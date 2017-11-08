import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        try (ServerSocket server= new ServerSocket(3345)){

            Socket client = server.accept();
            System.out.println("Connection accepted.");
            System.out.println();

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());

            out.writeUTF("Hello, client! Write \"Exit\" to close connection.");
            out.flush();

            while(!client.isClosed()){
                String entry = in.readUTF();
                System.out.println("READ from client message - " + entry);

                if(entry == "Exit"){
                    System.out.println("Client initialize connections quit.");
                    out.writeUTF("Ok. Bye!");
                    out.flush();
                    break;
                }

                out.writeUTF("Message received!");
                out.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            client.close();
            server.close();
            System.out.println("Closing connections & channels - DONE.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}