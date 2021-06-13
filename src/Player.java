import Roles.Role;

public class Player {
    private String name;
    private Server server;
    private ClientHandler clientHandler;
    private Role role;

    public Player(String name, Server server, ClientHandler clientHandler) {
        this.name = name;
        this.server = server;
        this.clientHandler = clientHandler;
    }

    public String getName() {
        return name;
    }
}
