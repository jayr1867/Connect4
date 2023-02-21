import java.io.Serializable;

public class CFourInfo implements Serializable {
    Boolean has2players;
    String pPlays;
    Boolean p1Turn;
    Boolean p2Turn;

    Boolean first;

    Boolean wP1;

    Boolean wP2;
//    Boolean run;

    CFourInfo() {
//        run = true;
        has2players = false;
        p1Turn = false;
        p2Turn = false;
        first = false;
        pPlays = "Waiting for a second player to join...";
        wP1 = false;
        wP2 = false;
    }
}
