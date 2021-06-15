import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The type Client handler.
 */
public class ClientHandler extends Thread{
    private final Socket clientSocket;
    private final Server server;
    private Narrator narrator;
    private String username = "Unknown";
    private Player player;

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    /**
     * Instantiates a new Client handler.
     *
     * @param clientSocket the client socket
     * @param server       the server
     * @param narrator     the narrator
     */
    public ClientHandler(Socket clientSocket, Server server, Narrator narrator) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.narrator = narrator;
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            try {
                dataOutputStream.close();
                dataInputStream.close();
                clientSocket.close();
                System.err.println("Socket Closed!");
                synchronized (server) {
                    server.remove1FromReadyCounter();
                }
                System.out.println("1 ready client reduced");
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void handleClient() throws IOException {
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        String clientLine = "";
        boolean ok = true;
        while (true){
            dataOutputStream.writeUTF("Please enter a username: ");
            dataOutputStream.flush();

            String clientResponse = dataInputStream.readUTF();

            synchronized (server) {
                if (!server.containsUserName(clientResponse)) {
                    dataOutputStream.writeUTF("Username accepted!");
                    dataOutputStream.flush();

                    this.username = clientResponse;
                    player = new Player(username, server, this);
                    server.addPlayer(player);
                    break;
                } else {
                    dataOutputStream.writeUTF("Username taken, try again!");
                    dataOutputStream.flush();
                }
            }
        }
        while (true){
            dataOutputStream.writeUTF("In order to join, you have to type READY");
            dataOutputStream.flush();

            String clientResponse = dataInputStream.readUTF();

            if (clientResponse.equals("READY")){
                dataOutputStream.writeUTF("Waiting for other players...");
                synchronized (player) {
                    player.setInGame(true);
                }
                synchronized (server) {
                    server.add1FromReadyCounter();
                    System.out.println("1 client is ready and added");
                }
                break;
            }
            else {
                dataOutputStream.writeUTF("You have to write READY!!!");
                dataOutputStream.flush();

            }
        }

        dataOutputStream.writeUTF("DISABLE_CHAT");
        dataOutputStream.flush();

        while (true){
            synchronized (server) {
                if (server.numberOfRemainedPlayersToTypeREADY() == 0) {
                    dataOutputStream.writeUTF("ENABLE_CHAT");
                    dataOutputStream.flush();
                    break;
                }
            }
        }

        while (true){
            clientLine = dataInputStream.readUTF();
            if (clientLine.equals("EXIT")) {
                handleExit();
                break;
            } else {
                synchronized (server) {
                    ArrayList<ClientHandler> clientHandlers = server.getClientHandlers();

                    //send other online users current user's msg
                    for (ClientHandler clientHandler : clientHandlers) {
                        if (!username.equals(clientHandler.username)) {
                            String msg = username + " : " + clientLine;
                            clientHandler.send(msg);
                        }
                    }
                }
            }
        }

    }
    private synchronized void handleExit() throws IOException {
        server.removeClientHandler(this);
        server.removePlayer(this.player);

        ArrayList<ClientHandler> clientHandlers = server.getClientHandlers();

        for (ClientHandler clientHandler : clientHandlers){
            if (!username.equals(clientHandler.username)) {
                String msg = "Went offline : " + username + "\n";
                clientHandler.send(msg);
            }
        }
        server.remove1FromReadyCounter();
        dataInputStream.close();
        dataOutputStream.close();
        clientSocket.close();
    }

    /**
     * Send the massage to client's reader thread to print it.
     *
     * @param msg the msg
     * @throws IOException the io exception
     */
    public void send(String msg) throws IOException {
        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush();
    }

    /**
     * Reads the string from client
     *
     * @return the string
     * @throws IOException the io exception
     */
    public String read() throws IOException {
        String s = dataInputStream.readUTF();
        return s;
    }
}
