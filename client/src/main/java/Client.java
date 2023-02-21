import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Consumer;

public class Client extends Thread{

    Socket socketClient;
    CFourInfo clientInfo;

    ObjectInputStream in;
    ObjectOutputStream out;
    String IP;
    String port;

    playerMoves moves;


    private Consumer<Serializable> callback;

    Client (Consumer<Serializable> call, String ip, String Port) {
        callback = call;
        IP = ip;
        port = Port;
        clientInfo = new CFourInfo();
        moves = new playerMoves();
    }


    public void run() {
        try {
            socketClient = new Socket(IP, Integer.parseInt(port));
//            socketClient = new Socket("127.0.0.1", 5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        } catch (Exception e) {
            System.exit(-1);
        }

        while (true) {
            try {
                clientInfo = (CFourInfo) in.readObject();

//
                if (clientInfo.wP2) {
                    callback.accept(clientInfo.pPlays);
                } else if (clientInfo.wP1) {
                    callback.accept(clientInfo.pPlays);
                } else if (clientInfo.p2Turn) {
                    if (!clientInfo.has2players) {
                        callback.accept("One player quit.\nWaiting for a second player");
                    } else {
                        callback.accept("It's Player 1's turn.\n" +
                                "Last move: Player 2 played " + clientInfo.pPlays);
                    }

                    if (!Objects.equals(clientInfo.pPlays, "Waiting for a second player to join...")) {
                        moves.p2Move.add(clientInfo.pPlays);
                        moves.allMoves.add(clientInfo.pPlays);
                    }

                    clientInfo.p2Turn = false;
                    clientInfo.p1Turn = true;

                } else if (clientInfo.p1Turn){
                    if (!clientInfo.has2players) {
                        callback.accept("One player quit.\nWaiting for a second player");
                    } else {

                        if (clientInfo.first) {
                            callback.accept("It's Player 2's turn.");
                            clientInfo.first = false;
                        } else {
                            callback.accept("It's Player 2's turn.\n" +
                                    "Last move: Player 1 played " + clientInfo.pPlays);


                            moves.p1Move.add(clientInfo.pPlays);
                            moves.allMoves.add(clientInfo.pPlays);

                        }
                    }
                    clientInfo.p1Turn = false;
                    clientInfo.p2Turn = true;
                } else {
                    callback.accept(clientInfo.pPlays);
                }

                if (moves.winCheck(moves.p2Move)) {
                    clientInfo.wP2 = true;
                } else if (moves.winCheck(moves.p1Move)) {
                    clientInfo.wP1 = true;
                }

            } catch (Exception e) {
            }
        }
    }

    public void send (CFourInfo data) {
        try {
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
