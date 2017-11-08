import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        try(Socket socket = new Socket("localhost", 3345)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());

            System.out.println("Client connected to socket.");
            System.out.println();

            while(!socket.isOutputShutdown()){
                if(br.ready()){
                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();

                    if(clientCommand == "Exit") {
                        Thread.sleep(200);
                        if(ois.read() > -1) {
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }
                        break;
                    }

                    Thread.sleep(200);

                    if(ois.read() > -1) {
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }

            br.close();
            ois.close();
            oos.close();

            System.out.println("Closing connections & channels on clientSide - DONE.");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}