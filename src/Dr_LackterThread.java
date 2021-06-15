import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Dr lackter thread.
 */
public class Dr_LackterThread extends Thread{
    private Narrator narrator;
    private ClientHandler clientHandler;
    private ArrayList<Player> players;

    /**
     * Instantiates a new Dr lackter thread.
     *
     * @param narrator      the narrator
     * @param clientHandler the client handler
     */
    public Dr_LackterThread(Narrator narrator,ClientHandler clientHandler) {
        this.narrator = narrator;
        this.clientHandler = clientHandler;
        players = narrator.getPlayers();
    }

    @Override
    public void run() {
        narrator.sendMafiaTeammateListToMafiaGroup();
        try {
            String clientVote;
            clientHandler.send("Please enter the username of the player you want to heal. Remember that you can only heal your self once");
            while (true) {
                clientVote = clientHandler.read();
                if (narrator.isHealValid(clientVote,clientHandler.getPlayer())){
                    narrator.heal(clientVote);
                    narrator.sendMsgToMafiaGroup("Dr_Lackter " + clientHandler.getPlayer().getName() + " healed: " + clientVote);
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
