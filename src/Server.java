
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    private  int ready_player_counter = 0;
    private final int number_of_players;
    private final Narrator narrator;
    private final int port;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
    public void removeClientHandler(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    public void addPlayer(Player player){
        players.add(player);
    }

    public void remove1FromReadyCounter(){
        ready_player_counter--;
    }
    public void add1FromReadyCounter(){
        ready_player_counter++;
    }
    public int numberOfRemainedPlayersToTypeREADY(){
        return number_of_players-ready_player_counter;
    }

    public int getReady_player_counter() {
        return ready_player_counter;
    }
    public int getNumber_of_players() {
        return number_of_players;
    }

    public Server(int port, int number_of_players) {
        this.number_of_players = number_of_players;
        narrator = new Narrator(this);
        this.port = port;
    }

    public boolean containsUserName(String userName){
        for (ClientHandler clientHandler : clientHandlers){
            if (clientHandler.getUsername().equals(userName))
                return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Server server = this;
            Thread thread = new Thread(){
                @Override
                public void run() {
                    while (ready_player_counter<number_of_players){
                        if (ready_player_counter>=number_of_players)
                            break;
                        System.out.println("Waiting for a client to join");
                        if (ready_player_counter>=number_of_players)
                            break;
                        Socket clientSocket = null;
                        try {
                            clientSocket = serverSocket.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (ready_player_counter>=number_of_players)
                            break;
                        System.out.println("New client connected "+clientSocket);
                        if (ready_player_counter>=number_of_players)
                            break;
                        ClientHandler clientHandler = new ClientHandler(clientSocket, server , narrator);
                        if (ready_player_counter>=number_of_players)
                            break;
                        clientHandlers.add(clientHandler);
                        if (ready_player_counter>=number_of_players)
                            break;
                        clientHandler.start();
                        if (ready_player_counter>=number_of_players)
                            break;
                    }
                }
            };
            thread.start();
            System.out.println("accept thread");
            while (true){
                synchronized ((Integer)number_of_players) {
                    synchronized ((Integer) ready_player_counter) {
                        if ((number_of_players - ready_player_counter) == 0) {
                            thread.stop();
                            System.out.println("thread stopped");
                            break;
                        }
                    }
                }
            }
            System.out.println("ravi started");
            narrator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
