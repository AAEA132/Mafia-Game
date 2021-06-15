import java.io.IOException;
import java.util.ArrayList;
import Roles.Le_Professionnel;

/**
 * The type Le professionnel thread.
 */
public class Le_ProfessionnelThread extends Thread {
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Le professionnel thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public Le_ProfessionnelThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        if (((Le_Professionnel) clientHandler.getPlayer().getRole()).getShots() > 0) {
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
                clientHandler.send("Do you want to shoot tonight? Remember that you have " + ((Le_Professionnel) clientHandler.getPlayer().getRole()).getShots() + " shots left");
                clientHandler.send("Please enter yes or no");
                while (true) {
                    clientVote = clientHandler.read();
                    if (clientVote.equals("yes")) {
                        clientHandler.send("Please enter the username of the player you want to shoot.");
                        while (true) {
                            clientVote = clientHandler.read();
                            if (narrator.isShotValid(clientVote, clientHandler.getPlayer())) {
                                narrator.shootP(clientVote, clientHandler);
                                break;
                            } else {
                                clientHandler.send("invalid");
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
        else {
            try {
                clientHandler.send("No shots left");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
