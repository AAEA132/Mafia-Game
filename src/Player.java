import Roles.Role;

/**
 * The type Player.
 */
public class Player {
    private String name;
    private Server server;
    private ClientHandler clientHandler;
    private Role role;
    private boolean isInServer;
    private boolean isInGame;
    private boolean chatStatus;
    private boolean isShot;
    private boolean isHealed;
    private int voteCounter = 0;

    /**
     * Instantiates a new Player.
     *
     * @param name          the name
     * @param server        the server
     * @param clientHandler the client handler
     */
    public Player(String name, Server server, ClientHandler clientHandler) {
        this.name = name;
        this.server = server;
        this.clientHandler = clientHandler;
        chatStatus = true;
    }

    /**
     * Gets chat status.
     *
     * @return the chat status
     */
    public boolean getChatStatus() {
        return chatStatus;
    }

    /**
     * Sets chat status.
     *
     * @param chatStatus the chat status
     */
    public void setChatStatus(boolean chatStatus) {
        this.chatStatus = chatStatus;
    }

    /**
     * Gets client handler.
     *
     * @return the client handler
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets is in server.
     *
     * @return the is in server
     */
    public boolean getIsInServer() {
        return isInServer;
    }

    /**
     * Sets in server.
     *
     * @param inServer the in server
     */
    public void setInServer(boolean inServer) {
        isInServer = inServer;
    }

    /**
     * Gets is in game.
     *
     * @return the is in game
     */
    public boolean getIsInGame() {
        return isInGame;
    }

    /**
     * Sets in game.
     *
     * @param inGame the in game
     */
    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    /**
     * Add vote.
     */
    public void addVote() {
        voteCounter++;
    }

    /**
     * Gets vote counter.
     *
     * @return the vote counter
     */
    public int getVoteCounter() {
        return voteCounter;
    }

    /**
     * Sets vote counter.
     *
     * @param voteCounter the vote counter
     */
    public void setVoteCounter(int voteCounter) {
        this.voteCounter = voteCounter;
    }

    /**
     * Gets is shot.
     *
     * @return the is shot
     */
    public boolean getIsShot() {
        return isShot;
    }

    /**
     * Sets is shot.
     *
     * @param shot the shot
     */
    public void setIsShot(boolean shot) {
        isShot = shot;
    }

    /**
     * Gets is healed.
     *
     * @return the is healed
     */
    public boolean getIsHealed() {
        return isHealed;
    }

    /**
     * Sets is healed.
     *
     * @param healed the healed
     */
    public void setIsHealed(boolean healed) {
        isHealed = healed;
    }
}
