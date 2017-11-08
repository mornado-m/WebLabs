import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Server {
    private int ClientCount = 0;
    private ArrayList<Server.Session> sessions;

    class Session extends Thread {
        Socket socket;
        int ClientID;
        String log;
        BufferedReader in;
        PrintWriter out;

        public Session(Socket s, int clientID) throws IOException {
            socket = s; ClientID = clientID;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),
                    Charset.forName("UTF-8")));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run(){
            try {
                log = in.readLine();

                if ("".equals(log)){
                    log = "Client" + ClientID;
                }

                for (Session s: sessions) {
                    s.out.println(log + " connected.");
                }

                while (true){
                    String str = in.readLine();

                    for (Session s: sessions) {
                        s.out.println(log + ": " + str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                for (Session s: sessions) {
                    s.out.println(log + " disconnected.");
                }
            }
        }
    }

    public Server(){
        sessions = new ArrayList<>();
        try{
            ServerSocket serverSocket = new ServerSocket(3345);
            System.out.println("Server started.");
            while(true){
                Socket clientSocket;
                try{
                    clientSocket = serverSocket.accept();
                    Session ses = new Session(clientSocket, ++ClientCount);
                    sessions.add(ses);
                    ses.start();
                } catch (IOException e) {System.out.println("Error of receiving...");}
            }
        } catch (IOException e) {
            System.out.println("Port 3345 is busy...");
            System.exit(-1);
        }
    }
    public static void main(String arg[])
    {
        new Server();
    }
}