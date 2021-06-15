import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Dr thread.
 */
public class DrThread extends Thread{
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Dr thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public DrThread(Narrator narrator,ClientHandler clientHandler) {
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
            clientHandler.send("Please enter the username of the player you want to heal. Remember that you can only heal your self once");
            while (true) {
                clientVote = clientHandler.read();
                if (narrator.isHealValid(clientVote,clientHandler.getPlayer())){
                    narrator.heal(clientVote);
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
