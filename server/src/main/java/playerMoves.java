import java.util.ArrayList;
import java.util.Collections;

public class playerMoves {

    ArrayList<String> p1Move;

    ArrayList<String> p2Move;

    ArrayList<String> allMoves;


    playerMoves() {
        p1Move = new ArrayList<>();
        p2Move = new ArrayList<>();
        allMoves = new ArrayList<>();
    }


    public Boolean winCheck (ArrayList<String> pMove) {
        String lMove = pMove.get(pMove.size() - 1);
        String deli = "[(, )]+";
        String[] move = lMove.split(deli);
        Integer mRow = Integer.valueOf(move[1]);
        Integer mCol = Integer.valueOf(move[2]);
        return (HorzwinCheck(pMove, mRow, deli) ||
                VertwinCheck(pMove, mCol, deli) ||
                DiagwinCheck(pMove, mRow, mCol));
    }


    public Boolean HorzwinCheck(ArrayList<String> pMove, Integer mRow, String deli) {
//        String lMove = pMove.get(pMove.size() - 1);
//        String deli = "[(, )]+";
//        String[] move = lMove.split(deli);
//        Integer mRow = Integer.valueOf(move[1]);
////        Integer mCol = Integer.valueOf(move[2]);
        ArrayList<Integer> RoCo = new ArrayList<>();
        boolean horz = true;

        for (int i = 0; i < pMove.size(); i++) {
            String[] coor = pMove.get(i).split(deli);
//            System.out.println("$$$$$$");
            Integer cRow = Integer.valueOf(coor[1]);
            Integer cCol = Integer.valueOf(coor[2]);
            if (mRow.equals(cRow)) {
                RoCo.add(cCol);
                Collections.sort(RoCo);
            }
        }
        if (RoCo.size() < 4) {
            horz = false;
        } else {
            int count = 0;
            for (int i = 0; i < RoCo.size() - 1; i++) {
//                System.out.println(RoCo.get(i));
                if ((RoCo.get(i + 1) - RoCo.get(i)) == 1) {
                    count++;
//                    System.out.println("CHECKING COUNT-" + count);
                } else {
                    count = 0;
                }
            }
            if (count != 3) {
                horz = false;
            }
        }

        //            System.out.println("$$$WE HAVE A WINNER!!!$$$");
        return horz;
    }


    public Boolean VertwinCheck(ArrayList<String> pMove, Integer mCol, String deli) {
//        String lMove = pMove.get(pMove.size() - 1);
//        String deli = "[(, )]+";
//        String[] move = lMove.split(deli);
////        Integer mRow = Integer.valueOf(move[1]);
//        Integer mCol = Integer.valueOf(move[2]);
        ArrayList<Integer> RoCo = new ArrayList<>();
        boolean vert = true;

        for (int i = 0; i < pMove.size(); i++) {
            String[] coor = pMove.get(i).split(deli);
//            System.out.println("$$$$$$");
            Integer cRow = Integer.valueOf(coor[1]);
            Integer cCol = Integer.valueOf(coor[2]);
            if (mCol.equals(cCol)) {
                RoCo.add(cRow);
                Collections.sort(RoCo);
            }
        }

        if (RoCo.size() < 4) {
            vert = false;
        } else {
            int count = 0;
            for (int i = 0; i < RoCo.size() - 1; i++) {
                System.out.println(RoCo.get(i));
                if ((RoCo.get(i + 1) - RoCo.get(i)) == 1) {
                    count++;
//                    System.out.println("CHECKING COUNT-" + count);
                } else {
                    count = 0;
                }
            }
            if (count != 3) {
                vert = false;
            }
        }
//        if (vert) {
//            System.out.println("$$$WE HAVE A WINNER!!!$$$");
//        }

        return vert;
    }


    public Boolean DiagwinCheck(ArrayList<String> pMove, Integer mRow, Integer mCol) {
//        String lMove = pMove.get(pMove.size() - 1);
//        String deli = "[(, )]+";
//        String[] move = lMove.split(deli);
//        int mRow = Integer.parseInt(move[1]);
//        int mCol = Integer.parseInt(move[2]);

        boolean diag = false;

        if (mCol == 1) {
            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol + 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow + 3) + ", " + (mCol + 3) + ")"))) ){
                diag = true;
            }
        } else if (mCol == 2) {
            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol + 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow + 3) + ", " + (mCol + 3) + ")"))) ) {
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")"))) ) {
                diag = true;
            }
        } else if (mCol == 6) {
            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol - 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol - 3) + ")"))) ) {
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")"))) ) {
                diag = true;
            }
        } else if (mCol == 7) {
            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol - 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol - 3) + ")"))) ){
                diag = true;
            }
        } else {
            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol - 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol - 3) + ")"))) ){
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol + 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol + 3) + ")"))) ){
                diag = true;
            }



            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol + 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol + 3) + ")"))) ) {
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol + 2) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 2) + ", " + (mCol + 2) + ")"))) ) {
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")")) &&
                    (pMove.contains("(" + (mRow - 3) + ", " + (mCol - 3) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")")) &&
                            (pMove.contains("(" + (mRow + 3) + ", " + (mCol - 3) + ")"))) ) {
                diag = true;
            }

            if (((pMove.contains("(" + (mRow - 1) + ", " + (mCol + 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 1) + ", " + (mCol - 1) + ")")) &&
                    (pMove.contains("(" + (mRow + 2) + ", " + (mCol - 2) + ")"))) ||
                    ((pMove.contains("(" + (mRow + 1) + ", " + (mCol + 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 1) + ", " + (mCol - 1) + ")")) &&
                            (pMove.contains("(" + (mRow - 2) + ", " + (mCol - 2) + ")"))) ) {
                diag = true;
            }
        }


        return diag;
    }

}
