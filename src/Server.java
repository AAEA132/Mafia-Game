
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
//            Thread thread = new Thread(this::printHowManyPlayersRemained);
//            thread.start();
            ServerSocket serverSocket = new ServerSocket(port);
            while (ready_player_counter<number_of_players){
                System.out.println("Waiting for a client to join");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected "+clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            }
            narrator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void printHowManyPlayersRemained() {
//        int numberOfPreviousReadyPlayers = -1;
//        int n = 0;
//        while (true){
//            if (numberOfPreviousReadyPlayers != (n = ready_player_counter)){
//                numberOfPreviousReadyPlayers = n;
//                try {
//                    for (ClientHandler clientHandler : clientHandlers){
//                        clientHandler.send("Number of players remained: " + numberOfRemainedPlayersToTypeREADY());
//                    }
////                    dataOutputStream.writeUTF("Number of players remained: " + server.numberOfRemainedPlayersToTypeREADY());
////                    dataOutputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}