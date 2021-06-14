import Roles.Role;


public class Player {
    private String name;
    private Server server;
    private ClientHandler clientHandler;
    private Role role;
    private boolean isInServer;
    private boolean chatStatus;


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
}

