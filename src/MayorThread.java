import java.io.IOException;

/**
 * The type Mayor thread.
 */
public class MayorThread extends Thread{
    private Player mostVotedPlayer;
    private Narrator narrator;
    private ClientHandler clientHAndler;

    /**
     * Instantiates a new Mayor thread.
     *
     * @param clientHandler   the client handler
     * @param narrator        the narrator
     * @param mostVotedPlayer the most voted player
     */
    public MayorThread(ClientHandler clientHandler, Narrator narrator, Player mostVotedPlayer) {
        this.clientHAndler = clientHandler;
        this.narrator = narrator;
        this.mostVotedPlayer = mostVotedPlayer;
    }

    @Override
    public void run() {
        try {
            String clientVote;
            clientHAndler.send("Do you want to cancel the voting result? if yes type yes and if no type no");
            while (true) {
                clientVote = clientHAndler.read();
                if (clientVote.equals("yes")){
                    narrator.sendMsgToAllPlayers("Voting Canceled by Mayor");
                    mostVotedPlayer.setInGame(false);
                    break;
                }
                else if (clientVote.equals("no")){
                    break;
                }
                else {
                    clientHAndler.send("Invalid input");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
