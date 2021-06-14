
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    //    private  int serverNumber = 0;
    private  String serverName = null;
    private  int serverPort;
    private static Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    //    private ReadFromServerThreadForClient reader;
//    private WriteClientStuffForServerToBePrinted writer;
    Thread reader;
    Thread writer;
//    ExecutorService pool;
//    BufferedReader bufferedReader;
//    PrintWriter printWriter;

    //    public Client(int serverNumber, int serverPort) {
//        this.serverNumber = serverNumber;
//        this.serverPort = serverPort;
//    }
    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
//        pool = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) throws IOException {
        Client client;
//        int ipNumber = 0;
        int portNumber = 0;
        String serverName = "";
        boolean ok = true;
        Scanner scanner = new Scanner(System.in);
        while (true){
            while (true) {
                System.out.print("Please enter the servers IP address: ");
                String ipGiven = scanner.nextLine();
                scanner = new Scanner(System.in);
//                System.out.println();

                if (ipGiven.equals("localhost") || isValidIPAddress(ipGiven)){
                    serverName = ipGiven;
                    break;
                }

//                else if (isValidIPAddress(ipGiven)){
//                    serverName = ipGiven;
//                    try {
//                        ipNumber = Integer.parseInt(ipGiven);
//                        break;
//                    } catch (NumberFormatException e) {
//                        System.out.println("IP address must be a Integer or serverName. try again");
//                    }
//                }
                else {
                    System.out.println("IP address must be a valid IP address like 127.0.0.1 or serverName. try again");
                }
            }

            while (true) {
                System.out.print("Please enter the number of the port: ");
                String port = scanner.nextLine();
//                System.out.println();
                try {
                    portNumber = Integer.parseInt(port);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Port number must be an Integer. try again");
                }
            }
//            if (serverName.equals("serverName")){
//                client = new Client(serverName,portNumber);
//            }
//            else {
//                client = new Client(ipNumber,portNumber);
//            }
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
//        socket.close();
    }

    private void buildReaderAndWriterThreads() {
        writer = new WriteClientStuffForServerToBePrinted(dataOutputStream , this);
        reader = new ReadFromServerThreadForClient(dataInputStream , this, writer);
    }

    private boolean connect() {
        try {
//            if (serverName.equals("localhost")){
//                this.socket = new Socket(serverName,serverPort);
//            }
//            else {
//                this.socket = new Socket(String.valueOf(serverNumber),serverPort);
//            }
            socket = new Socket(serverName,serverPort);
            System.out.println("Client port is: "+ socket.getPort());
//            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            printWriter = new PrintWriter(socket.getOutputStream() , true);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
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

        // Compile the ReGex
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
    private void startMassageWriting() {
//        writer = new WriteClientStuffForServerToBePrinted(dataOutputStream , this);
        writer.start();
//        writer.start();
//        Thread thread = new Thread(this::writeMassageLoop);
//        pool.execute(thread);
//        thread.start();
    }
//    private void writeMassageLoop() {
//        Scanner scanner = new Scanner(System.in);
//        String line = "";
//        while ( !line.equals("EXIT")){
//            line = scanner.nextLine();
////            printWriter.println(line);
//            try {
//                dataOutputStream.writeUTF(line);
//                dataOutputStream.flush();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            dataOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void startMassageReading() {
//        reader = new ReadFromServerThreadForClient(dataInputStream , this, writer);
        reader.start();
//        reader.start();
//        Thread thread = new Thread(this::readMassageLoop);
//        pool.execute(thread);
//        thread.start();
    }
    //read from server
//    private void readMassageLoop() {
//        while (true){
//            try {
////                System.out.println(bufferedReader.readLine());
//                System.out.println(dataInputStream.readUTF());
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//        try {
//            dataInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void handleMassage(String massage) throws IOException {
        System.out.println(massage);
    }

//    public void stopClientMassaging(){
//        try {
//            writer.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    public void activateClient(){
//        writer.notify();
//    }
}
