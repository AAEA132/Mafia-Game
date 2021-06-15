import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Psychologist thread.
 */
public class PsychologistThread extends Thread {
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Psychologist thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public PsychologistThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        int n = 1;
        for (Player player : players) {
            if (!(clientHandler.getPlayer().getName().equals(player.getName()))) {
                try {
                    player.getClientHandler().send(n + ". " + player.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                n++;
            }
        }
        try {
            String clientVote;
            clientHandler.send("Do you want to silence someone for tomorrow?");
            clientHandler.send("Please enter yes or no");
            while (true) {
                clientVote = clientHandler.read();
                if (clientVote.equals("yes")) {
                    clientHandler.send("Please enter the username of the player you want to silence.");
                    while (true) {
                        clientVote = clientHandler.read();
                        if (narrator.isVoteValid(clientVote)) {
                            narrator.silence(clientVote, clientHandler);
                            break;
                        } else {
                            clientHandler.send("Vote is invalid");
                        }
                    }
                } else if (clientVote.equals("no")) {
                    break;
                } else {
                    clientHandler.send("Invalid input");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
