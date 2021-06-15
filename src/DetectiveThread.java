import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Detective thread.
 */
public class DetectiveThread extends Thread {
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Detective thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public DetectiveThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        int n = 1;
        for (Player player : players){
            if(!(clientHandler.getPlayer().getName().equals(player.getName()))){
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
            clientHandler.send("Please enter the username of the player you want to detect.");
            while (true) {
                clientVote = clientHandler.read();
                if (narrator.isDetectValid(clientVote,clientHandler.getPlayer())){
                    narrator.detect(clientVote, clientHandler);
                    break;
                }
                else {
                    clientHandler.send("invalid");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
