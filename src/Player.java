import Roles.Role;

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

    public Player(String name, Server server, ClientHandler clientHandler) {
        this.name = name;
        this.server = server;
        this.clientHandler = clientHandler;
        chatStatus = true;
    }

    public boolean getChatStatus() {
        return chatStatus;
    }
    public void setChatStatus(boolean chatStatus) {
        this.chatStatus = chatStatus;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public String getName() {
        return name;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return role;
    }

    public boolean getIsInServer() {
        return isInServer;
    }
    public void setInServer(boolean inServer) {
        isInServer = inServer;
    }

    public boolean getIsInGame() {
        return isInGame;
    }
    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public void addVote() {
        voteCounter++;
    }

    public int getVoteCounter() {
        return voteCounter;
    }

    public void setVoteCounter(int voteCounter) {
        this.voteCounter = voteCounter;
    }

    public boolean getIsShot() {
        return isShot;
    }

    public void setIsShot(boolean shot) {
        isShot = shot;
    }

    public boolean getIsHealed() {
        return isHealed;
    }

    public void setIsHealed(boolean healed) {
        isHealed = healed;
    }
}
