import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Client.
 */
public class Client {
    private  String serverName = null;
    private  int serverPort;
    private static Socket socket;
    /**
     * The Data input stream.
     */
    DataInputStream dataInputStream;
    /**
     * The Data output stream.
     */
    DataOutputStream dataOutputStream;
    /**
     * The Reader that reads from server/client_handler and prints it to console.
     */
    Thread reader;
    /**
     * The Writer that writes the things client types for server/client_handler
     */
    Thread writer;

    /**
     * Instantiates a new Client.
     *
     * @param serverName the server name
     * @param serverPort the server port
     */
    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    /**
     * The entry point of application.
     * Fisrt it gets the name and port of the server, then it tries to connect by given info and will ask
     * client to try again if connection fails
     * After that it creates and starts the reader and writer threads to communicate with server
     */
    public static void main(String[] args) {
        Client client;
        int portNumber = 0;
        String serverName = "";
        boolean ok = true;
        Scanner scanner = new Scanner(System.in);
        while (true){
            while (true) {
                System.out.print("Please enter the servers IP address: ");
                String ipGiven = scanner.nextLine();
                scanner = new Scanner(System.in);
                if (ipGiven.equals("localhost") || isValidIPAddress(ipGiven)){
                    serverName = ipGiven;
                    break;
                }
                else {
                    System.out.println("IP address must be a valid IP address like 127.0.0.1 or serverName. try again");
                }
            }

            while (true) {
                System.out.print("Please enter the number of the port: ");
                String port = scanner.nextLine();
                try {
                    portNumber = Integer.parseInt(port);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Port number must be an Integer. try again");
                }
            }
            client = new Client(serverName,portNumber);
            if (!client.connect()){
                System.err.println("Connection Failed. Try again");
                ok = false;
            }
            else {
                System.out.println("Connect Successful");
                break;
            }
        }
        client.buildReaderAndWriterThreads();
        client.startMassageReading();
        client.startMassageWriting();

        System.out.println("Test");
    }

    /**
     * Creates the reader and writer threads
     */
    private void buildReaderAndWriterThreads() {
        writer = new WriteClientStuffForServerToBePrinted(dataOutputStream , this);
        reader = new ReadFromServerThreadForClient(dataInputStream , this, writer);
    }

    /**
     * Tries to connect; Returns true if it's successful, if not it returns false
     */
    private boolean connect() {
        try {
            socket = new Socket(serverName,serverPort);
            System.out.println("Client port is: "+ socket.getPort());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if the given IP address has the valid format
     *
     * @param ip the ip adheres
     * @return the boolean which will be true if the format is correct and if not, it'll be false
     */
    public static boolean isValidIPAddress(String ip) {
        // Regex for digit from 0 to 255.
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (ip == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        Matcher m = p.matcher(ip);

        // Return if the IP address
        // matched the ReGex
        return m.matches();
    }

    /**
     * Starts writer thread
     */
    private void startMassageWriting() {
        writer.start();
    }
    /**
     * Starts reader thread
     */
    private void startMassageReading() {
        reader.start();
    }
//    private void handleMassage(String massage) throws IOException {
//        System.out.println(massage);
//    }
}