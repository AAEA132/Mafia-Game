
import java.io.DataInputStream;
import java.io.IOException;

/**
 * The type Read from server thread for client.
 */
public class ReadFromServerThreadForClient extends Thread{
    private final DataInputStream dataInputStream;
    private final Client client;
    private final Thread writer;

    /**
     * Instantiates a new Read from server thread for client.
     *
     * @param dataInputStream the data input stream
     * @param client          the client
     * @param writer          the writer
     */
    public ReadFromServerThreadForClient(DataInputStream dataInputStream, Client client, Thread writer) {
        this.dataInputStream = dataInputStream;
        this.client = client;
        this.writer = writer;
    }

    @Override
    public void run() {
        String serverSays = "";
        while (true){
            try {
                serverSays = dataInputStream.readUTF();
                String[] tokens = serverSays.split("\\s+");
                String command = tokens[0];
                if (command.equals("DISABLE_CHAT")){
                    System.out.println("Waiting for other players to join, no massaging will happen");
                    synchronized (writer) {
                        writer.suspend();
                    }
                }
                else if (command.equals("ENABLE_CHAT")){
                    System.out.println("Enough players joined, Massaging enabled");
                    synchronized (writer) {
                        writer.resume();
                    }
                }
                else {
                    System.out.println(serverSays);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
