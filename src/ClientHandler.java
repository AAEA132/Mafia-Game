//import Main.ServerWorker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
//import java.util.Scanner;

public class ClientHandler extends Thread{
    private final Socket clientSocket;
    private final Server server;
    private String username = "Unknown";
    private Player player;

    public String getUsername() {
        return username;
    }

    //    private PrintWriter printWriter;
//    private BufferedReader bufferedReader;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            try {
                clientSocket.close();
                System.err.println("Socket Closed!");
                server.remove1FromReadyCounter();
                e.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
//            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException {
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
//        printWriter = new PrintWriter(clientSocket.getOutputStream() , true);
//        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String clientLine = "";

        boolean ok = true;
        while (true){
//            printWriter.print("Please enter a username: ");
            dataOutputStream.writeUTF("Please enter a username: ");
            dataOutputStream.flush();
//            dataOutputStream.writeUTF("\n");
//            dataOutputStream.flush();

            String clientResponse = dataInputStream.readUTF();
//            String clientResponse = bufferedReader.readLine();

//            ArrayList<ClientHandler> clientHandlers = server.getClientHandlers();

//            for (ClientHandler clientHandler : clientHandlers){
//                if (clientHandler.username.equals(clientResponse)){
////                    printWriter.println("Username taken, try again!");
//                    dataOutputStream.writeUTF("Username taken, try again!");
//                    dataOutputStream.flush();
//
//                    ok = false;
//                    break;
//                }
//            }
            if (!server.containsUserName(clientResponse)){
//                printWriter.println("Username accepted!");
                dataOutputStream.writeUTF("Username accepted!");
                dataOutputStream.flush();

                this.username = clientResponse;
                player = new Player(username, server, this);
                server.addPlayer(player);
                break;
            }
            else {
                dataOutputStream.writeUTF("Username taken, try again!");
                dataOutputStream.flush();
            }
        }

        Thread thread = new Thread(this::printHowManyPlayersRemained);
        thread.start();

        while (true){
            dataOutputStream.writeUTF("In order to join, you have to type READY");
            dataOutputStream.flush();

            String clientResponse = dataInputStream.readUTF();

            if (clientResponse.equals("READY")){
                dataOutputStream.writeUTF("Waiting for other players...");
                server.add1FromReadyCounter();
                break;
            }
            else {
                dataOutputStream.writeUTF("You have to write READY!!!");
                dataOutputStream.flush();

            }
        }

        dataOutputStream.writeUTF("READY_PHASE");
        dataOutputStream.flush();

//        while (true){
//            if (server.numberOfRemainedPlayersToTypeREADY()!=0){
//                dataInputStream.readUTF();
//            }
//            else if (server.numberOfRemainedPlayersToTypeREADY()==0){
//                dataOutputStream.writeUTF("AFTER_READY_PHASE");
//                dataOutputStream.flush();
//                break;
//            }
//        }
        while (true){
            if (server.numberOfRemainedPlayersToTypeREADY()==0){
                dataOutputStream.writeUTF("AFTER_READY_PHASE");
                dataOutputStream.flush();
                break;
            }
        }

        while (true){
            clientLine = dataInputStream.readUTF();
//            clientLine = bufferedReader.readLine();
            if(clientLine.equals("EXIT")){
                handleExit();
                break;
            }
            else {
                ArrayList<ClientHandler> clientHandlers = server.getClientHandlers();

                //send other online users current user's msg
                for (ClientHandler clientHandler : clientHandlers){
                    if (!username.equals(clientHandler.username)) {
                        String msg = username + " : " + clientLine;
                        clientHandler.send(msg);
                    }
                }
            }
        }

    }

    private void printHowManyPlayersRemained() {
        int numberOfPreviusRadyPlayers = 0;
        while (true){
            if (numberOfPreviusRadyPlayers != server.getReady_player_counter()){
                numberOfPreviusRadyPlayers = server.getReady_player_counter();
                try {
                    dataOutputStream.writeUTF("Number of players remained: " + server.numberOfRemainedPlayersToTypeREADY());
                    dataOutputStream.flush();
                } catch (IOException e) {
                    try {
                        clientSocket.close();
                        System.err.println("Socket Closed!");
                        server.remove1FromReadyCounter();
                        e.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }


    private void handleExit() throws IOException {
        server.removeClientHandler(this);
        server.removePlayer(this.player);

        ArrayList<ClientHandler> clientHandlers = server.getClientHandlers();

        //send other online users current user's leaving
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

    private void send(String msg) throws IOException {
        dataOutputStream.writeUTF(msg);
        dataOutputStream.flush();
//        printWriter.println(msg);
    }
}
