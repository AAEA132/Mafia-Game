import java.io.IOException;
import java.util.ArrayList;
import Roles.DieHard;

/**
 * The type Die hard thread.
 */
public class DieHardThread extends Thread {
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Die hard thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public DieHardThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        if (((DieHard) clientHandler.getPlayer().getRole()).getAbility() > 0) {
            try {
                String clientVote;
                clientHandler.send("Do you want the state of game to be stated tomorrow? Remember that you can do it for 2 times only");
                clientHandler.send("Please enter yes or no");
                while (true) {
                    clientVote = clientHandler.read();
                    if (clientVote.equals("yes")) {
                        ((DieHard) clientHandler.getPlayer().getRole()).setAbility(((DieHard) clientHandler.getPlayer().getRole()).getAbility() - 1);
                        narrator.setStateGameState(true);
                        break;
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
                clientHandler.send("No ability left");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
