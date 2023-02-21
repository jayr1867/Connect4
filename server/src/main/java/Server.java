import javafx.scene.Parent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public class Server {

    CFourInfo clientInfo =  new CFourInfo();

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<>();

    TheServer server;
    String port;
    private Consumer<Serializable> callback;

    Server (Consumer<Serializable> call, String pNum) {
        callback = call;
        port = pNum;
        server = new TheServer();
        server.start();
    }

    public void end() {
        server.endThread();
    }


    public class TheServer extends Thread {
        public void run() {
            try (ServerSocket mySocket = new ServerSocket(Integer.parseInt(port));) {
//            try (ServerSocket mySocket = new ServerSocket(5555);) {
                System.out.println("Server is waiting for a client...");
                callback.accept("SERVER RUNNING...");

                while (true) {
                    ClientThread c = new ClientThread(mySocket.accept());
                    count = clients.size() + 1;
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();
                }


            } catch (Exception e) {
                callback.accept("Server did not launch, try again.");
                System.out.println("Server stopped.");
            }
        }

        public void endThread() {
            this.interrupt();
        }
    }

    class ClientThread extends Thread {

        Socket conn;


        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread (Socket s) {
            this.conn = s;
        }

        public void updateClients (CFourInfo message) {
            for (int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                } catch (Exception e) {}
            }
        }


        public void run() {

            try {
                in = new ObjectInputStream(conn.getInputStream());
                out = new ObjectOutputStream(conn.getOutputStream());
                conn.setTcpNoDelay(true);
            } catch (Exception e) {
                System.out.println("Streams not open.");
            }

            if (clients.size() > 1) {
                clientInfo.has2players = true;
                clientInfo.p2Turn = true;
                clientInfo.first = true;
            } else {
                clientInfo.has2players = false;
                clientInfo.p2Turn = false;
                clientInfo.p1Turn = false;
                clientInfo.first = false;
                clientInfo.pPlays = "Waiting for a second player to join...";
                clientInfo.wP2 = false;
                clientInfo.wP1 = false;
            }


            updateClients(clientInfo);

            while (true) {
                try {
                    clientInfo = (CFourInfo) in.readObject();
                    callback.accept("client #" + count + " plays: " + clientInfo.pPlays);
                    if (clientInfo.wP1) {
                        clientInfo.pPlays = "PLAYER 1 HAS WON!!!";
                        callback.accept(clientInfo.pPlays);
                    } else if (clientInfo.wP2) {
                        clientInfo.pPlays = "PLAYER 2 HAS WON!!!";
                        callback.accept(clientInfo.pPlays);
                    }
                    updateClients(clientInfo);
                } catch (Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    updateClients(clientInfo);
                    clients.remove(this);
                    break;
                }

            }

        } //end of run.

    } //end of client thread.

}
