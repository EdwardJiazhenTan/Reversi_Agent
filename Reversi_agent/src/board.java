import java.util.ArrayList;
import java.util.List;

public class board {

    // create board, 0 stands for blank, 1 stands for white and -1 stands for black
    public int[][] createBoard(int n){
        int[][] theBoard = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                theBoard[i][j] = 0;
            }
        }

        theBoard[n/2][n/2-1] = 1;
        theBoard[n/2-1][n/2] = 1;
        theBoard[n/2-1][n/2-1] = -1;
        theBoard[n/2][n/2] = -1;
        return theBoard;
    }


    // print board
    public void printBoard(int[][] board){
        String[] row = new String[8];
        row[0] = "a";
        row[1] = "b";
        row[2] = "c";
        row[3] = "d";
        row[4] = "e";
        row[5] = "f";
        row[6] = "g";
        row[7] = "h";
        System.out.print("  ");
        for(int i = 0; i < board.length; i++){
            System.out.print(row[i] + " ");
        }
        System.out.println();

        for(int i = 0; i < board.length; i++){
            System.out.print((i +1) + " ");
            for(int j = 0; j < board.length; j++){

                // swap 0, 1, 2 into pieces and blanks
                if(board[i][j] == 0) {
                    System.out.print("  ");
                }
                else if(board[i][j] == 1){
                    System.out.print("o" + " ");
                }
                else if(board[i][j] == -1){
                    System.out.print("x" + " ");
                }
                else{
                    System.out.println("error when generating board");
                }
            }
            System.out.print((i +1) + " ");
            System.out.println();
        }

        System.out.print("  ");
        for(int i = 0; i < board.length; i++){
            System.out.print(row[i] + " ");
        }
        System.out.println();
        System.out.println();
    }


    // check if a move is legal
    public boolean isLegalMove(int x, int y, int color, int[][] board) {

        int BOARD_SIZE = board.length;
        // if a move is out of bound
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
          return false;
        }

        // if a move is not target to a blank spot
        if (board[x][y] != 0) {
          return false;
        }

        // recursively check horizontally and vertically
        for (int dx = -1; dx <= 1; dx++) {
          for (int dy = -1; dy <= 1; dy++) {
            if (dx == 0 && dy == 0) {
              continue;
            }
            int x1 = x + dx;
            int y1 = y + dy;
            if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == -color) {
              x1 += dx;
              y1 += dy;
              while (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == -color) {
                x1 += dx;
                y1 += dy;
              }
              if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == color) {
                return true;
              }
            }
          }
        }
        return false;
      }



      // move method for player
      public int[][] moveBoard(int x, int y, int color, int[][] board){
          int BOARD_SIZE = board.length;
        // if its not legal move
        if(!isLegalMove(x, y, color, board)){
            System.out.println("Illegal move!" + x + " " + y);
        }

        // if the move is legal
        else{
            board[x][y] = color;
           // System.out.println("Your new piece is on (" + x + ", " + y + ")");

            // flip colors after the move
            ArrayList<Integer> Xs=  new ArrayList<>();
            ArrayList<Integer> Ys=  new ArrayList<>();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) {
                        continue;
                    }
                    int x1 = x + dx;
                    int y1 = y + dy;
                    if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == -color) {
                        Xs.add(x1);
                        Ys.add(y1);
                        x1 += dx;
                        y1 += dy;
                        while (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == -color) {
                            Xs.add(x1);
                            Ys.add(y1);
                            x1 += dx;
                            y1 += dy;

                        }
                        if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && board[x1][y1] == color) {
                            for(int i = 0; i < Xs.size(); i ++){
                                board[Xs.get(i)][Ys.get(i)] = color;
                            }
                        }

                    }
                    Xs.clear();
                    Ys.clear();
                }

            }

        }


        return board;
      }




    public int[][] tryMoveBoard(int x, int y, int color, int[][] board){

        int[][] clonedBoard = new int[board.length][board.length];

        for(int i = 0; i < board.length; i ++){
            for(int j = 0; j < board.length; j ++){
                clonedBoard[i][j] = board[i][j];
            }
        }

        int BOARD_SIZE = clonedBoard.length;
        // if its not legal move
        if(!isLegalMove(x, y, color, clonedBoard)){
            System.out.println("Illegal move!" + x + " " + y);
        }

        // if the move is legal
        else{
            clonedBoard[x][y] = color;
        //    System.out.println("Your new piece is on (" + x + ", " + y + ")");

            // flip colors after the move

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    ArrayList<Integer> Xs=  new ArrayList<>();
                    ArrayList<Integer> Ys=  new ArrayList<>();
                    if (dx == 0 && dy == 0) {
                        continue;
                    }
                    int x1 = x + dx;
                    int y1 = y + dy;
                    if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && clonedBoard[x1][y1] == -color) {
                        Xs.add(x1);
                        Ys.add(y1);
                        x1 += dx;
                        y1 += dy;
                        while (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && clonedBoard[x1][y1] == -color) {
                            Xs.add(x1);
                            Ys.add(y1);
                            x1 += dx;
                            y1 += dy;
                        }
                        if (x1 >= 0 && x1 < BOARD_SIZE && y1 >= 0 && y1 < BOARD_SIZE && clonedBoard[x1][y1] == color) {
                            for(int i = 0; i < Xs.size(); i ++){
                                clonedBoard[Xs.get(i)][Ys.get(i)] = color;

                            }
                        }


                    }
                    Xs.clear();
                    Ys.clear();

                }

            }

        }

        return clonedBoard;
    }


      // check the number of colors on the board
      public int[] checkColor (int[][] board){
        List<Integer> l = new ArrayList<Integer>();
        for(int i = 0; i < board.length; i ++){
            for(int j = 0; j < board[i].length; j ++){
                l.add(board[i][j]);
            }
        }

        int white = 0;
        int black = 0;
        int[] result = new int[2];

        for(int i = 0; i < l.size(); i ++){
            if(l.get(i) == 1){
                white += 1;
            }
            else if(l.get(i) == -1){
                black += 1;
            }
        }
          result[0] = white;
          result[1] = black;

        //  System.out.println("check color:  "+ result[0] + " " + result[1]);
        return result;
      }



      // determine who won the game
    // return 0 if it is a tie, return 1 if white wins, return -1 if black wins
      // return 2 if the game should continue
    public int winner(int[][] board){

        // if both players have no blank space to continue, check who won
        // whoever has more pieces on board wins the game
        if(findLegal(1,board) == null && findLegal(-1, board) == null){
            int[] result = checkColor(board);
            if (result[0]  == result[1]) {
                // tie
                return 0;
            }
            else if (result[0] < result[1]){
                // white wins
                return 1;
            }
            else {
                // black wins
                return -1;
            }
        }
        else {
            return 2;
        }
    }

    public int blankNumber(int[][] board){
        List<Integer> l = new ArrayList<Integer>();
        for(int i = 0; i < board.length; i ++){
            for(int j = 0; j < board[i].length; j ++){
                l.add(board[i][j]);
            }
        }

        int result = 0;
        for(int j = 0; j < l.size(); j ++) {
            if (l.get(j) == 0) {
                result ++;
            }
        }
        return result;
    }

    // find all legal moves
    public ArrayList<int[]> findLegal(int color, int[][] board){

        ArrayList<int[]> moves = new ArrayList<>(board.length);

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(isLegalMove(i,j,color,board)){
                   moves.add(new int[] {i,j});
                  // System.out.print("   " + i  + j);
                }
            }
        }

        return moves;
    }


    public int[][] userMove(String move, int color, int[][] board){
        char[] moveChar = move.toCharArray();
        int x = 0;
        int y = 0;
        if(moveChar[0] == 'q'){
            return board;
        }
        if(Character.isLetter(moveChar[0]) && Character.isDigit(moveChar[1])){
            y = Character.getNumericValue(moveChar[1]) - 1;
            if(moveChar[0] == 'a'){
                x = 0;
            }
            else if(moveChar[0] == 'b'){
                x = 1;
            }
            else if(moveChar[0] == 'c'){
                x = 2;
            }
            else if(moveChar[0] == 'd'){
                x = 3;
            }
            else if(moveChar[0] == 'e'){
                x = 4;
            }
            else if(moveChar[0] == 'f'){
                x = 5;
            }
            else if(moveChar[0] == 'g'){
                x = 6;
            }
            else if(moveChar[0] == 'h'){
                x = 7;
            }
            else{
                System.out.println("Illegal move!");
            }
        }else {
            System.out.println("Illegal move!");
        }
        if(isLegalMove(y,x,color,board)){
           // System.out.println("Your new piece is on (" + x + ", " + y + ")");
            moveBoard(y,x,color,board);
           // printBoard(board);
        }
        else{
            System.out.println("Illegal move!!!!");
        }
        return board;
    }



          //  System.out.println("Your new piece is on (" + x + ", " + y + ")");
           // printBoard(board);


    }


