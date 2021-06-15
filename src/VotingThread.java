import java.io.IOException;

/**
 * The type Voting thread.
 */
public class VotingThread extends Thread{
    private Narrator narrator;
    private ClientHandler clientHandler;

    /**
     * Instantiates a new Voting thread.
     *
     * @param clientHandler the client handler
     * @param narrator      the narrator
     */
    public VotingThread(ClientHandler clientHandler, Narrator narrator) {
        synchronized (clientHandler) {
            this.clientHandler = clientHandler;
        }
        synchronized (narrator) {
            this.narrator = narrator;
        }
    }

    @Override
    public void run() {
        try {
            String clientVote;
            synchronized (clientHandler) {
                clientHandler.send("Please enter the username of the player you want to vote out. If you dont vote, you simply hadn't voted!");
            }
            while (true) {
                synchronized (clientHandler) {
                    clientVote = clientHandler.read();
                }
                synchronized (narrator) {
                    if (narrator.isVoteValid(clientVote)) {
                        narrator.addVote(clientVote);
                        synchronized (clientHandler) {
                            narrator.sendMsgToAllPlayers(clientHandler.getUsername() + " >Voted : " + clientVote);
                        }
                        break;
                    } else {
                        synchronized (clientHandler) {
                            clientHandler.send("Vote is invalid");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
