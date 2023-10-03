import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        board b = new board();
        miniMax max = new miniMax();
        int p1 = 1;
        Random rand = new Random();

        Scanner sc = new Scanner(System.in);
        System.out.println("Choose your game:");
        System.out.println("1. 4*4 Reversi");
        System.out.println("2. 8*8 Reversi");
        System.out.println("Your choice?");
        int chooseBoard = sc.nextInt();
        int size = 4;
        if(chooseBoard == 1){
            size = 4;
        }else if (chooseBoard == 2){
            size = 8;
        }
        else{
            System.out.println("Wrong choice.");
        }

        int[][] newBoard = b.createBoard(size);
        b.printBoard(newBoard);

        System.out.println("Do you want to play dark(x) or light(o)?");
        String choosePiece = sc.next();
        if(choosePiece.equals("x") || choosePiece.equals("X") || choosePiece.equals("dark")){
            p1 = -1;
        }
        else if (choosePiece.equals("o") || choosePiece.equals("O") || choosePiece.equals("light")){
            p1 = 1;
           b.moveBoard(newBoard.length/2-2,newBoard.length/2,-1,newBoard);
           b.printBoard(newBoard);
        }
        else{
            System.out.println("error");
        }

        int agent = 0;
        System.out.println("Choose your opponent:");
        System.out.println("1. An agent that uses MINIMAX (WARNING: this agent takes very long time to calculate route)");
        System.out.println("2. An agent that uses MINIMAX with alpha-beta pruning");
        System.out.println("3. An agent that uses H-MINIMAX with a fixed depth cutoff and a-b pruning");
        System.out.println("Your choice? (enter 1, 2, 3 without colum)");

        int chooseAgent = sc.nextInt();
        if(chooseAgent == 1){
            //minimax
            System.out.println("minimax");
            agent = 1;
        }
        else if (chooseAgent == 2){
            //minimax with alpha-beta pruning
            System.out.println("minimax with alpha-beta pruning");
            agent = 2;
        }
        else if (chooseAgent == 3){
            //H-MINIMAX with a fixed depth cutoff and a-b pruning
            System.out.println("H-MINIMAX with a fixed depth cutoff and a-b pruning");
            agent = 3;
        }
        else{
            System.out.println("error");
        }

        System.out.println("Your move (? for help):");
        String move = sc.next();


        while(sc.hasNextLine()) {

            if(move.equals("quit")){
                System.out.println("you have no moves left");
                System.out.println("According to our agent's calculate, when you lose all moves implies you're lost");
                System.out.println("Score:");
                System.out.println("White:" + b.checkColor(newBoard)[0] + " Black: " + b.checkColor(newBoard)[1]);
                System.out.println("*****************************************************************************");
                if(b.checkColor(newBoard)[0] > b.checkColor(newBoard)[1]){
                    System.out.println("Player with White(0) piece won!!!");
                }
                if(b.checkColor(newBoard)[0] == b.checkColor(newBoard)[1]){
                    System.out.println("we have a Tie!");
                }
                else{
                    if(b.checkColor(newBoard)[0] > b.checkColor(newBoard)[1]){
                        System.out.println("Player with black(x) piece won!!!");
                    }
                }
                break;
            }

            if (move.equals("?")) {
                System.out.println("Enter moves as COLUMN then LETTER.");
                System.out.println("For example, \"a1\" is the top left corner");
                System.out.println("Enter \"pass\" (without the quotes) if you have no legal moves.");
                System.out.println("if you have no moves, please enter 'quit',our agent will calculate your score after.");
                move = sc.next();
            }
            else {

                if (agent == 1) {

                    b.userMove(move, p1, newBoard);
                    System.out.println("agent1: regular miniMax");
                   // max.getBestMoveMinimax(-p1, -p1, newBoard, 5);

                    int[] p = new int[] {0,0};
                    max.miniMax_noABP(p, newBoard.length-1, p1,newBoard,-p1);

                    int[] x = max.getBestMoveMinimax();
                    if (x != null) {

                        System.out.println("best move: " + x[0] + x[1]);
                        b.moveBoard(x[0], x[1], -p1, newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                    } else {
                        System.out.println("best move calculating....");
                        ArrayList<int[]> array = b.findLegal(-p1,newBoard);
                        int rand_int1 = rand.nextInt(array.size());
                        int dx = array.get(rand_int1)[0];
                        int dy = array.get(rand_int1)[1];
                        b.moveBoard(dx,dy,-p1,newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                        //System.out.println(b.winner(newBoard));
                    }

                }
                else if (agent == 2) {
                    b.userMove(move, p1, newBoard);
                    System.out.println("agent2: alpha-beta pruning");
                    int[] p = new int[] {newBoard.length/2,newBoard.length/2};
                    max.minimax(p, newBoard.length/4, Integer.MIN_VALUE,Integer.MAX_VALUE,-p1,newBoard,p1);
                    int[] x = max.getBestMove();
                    if (x != null) {
                        System.out.println("best move: " + x[0] + x[1]);
                        b.moveBoard(x[0], x[1], -p1, newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                    } else {
                        System.out.println("best move calculating....");
                        ArrayList<int[]> array = b.findLegal(-p1,newBoard);
                        int rand_int1 = rand.nextInt(array.size());
                        int dx = array.get(rand_int1)[0];
                        int dy = array.get(rand_int1)[1];
                        b.moveBoard(dx,dy,-p1,newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                    }
                }
                else if (agent == 3) {
                    b.userMove(move, p1, newBoard);
                    System.out.println("agent3: fixed depth");
                    int[] p = new int[] {newBoard.length/2,newBoard.length/2};
                    max.minimax(p, 2,Integer.MIN_VALUE,Integer.MAX_VALUE,-p1,newBoard,p1);
                    int[] x = max.getBestMove();
                    if (x != null) {
                        System.out.println("best move: " + x[0] + x[1]);
                        b.moveBoard(x[0], x[1], -p1, newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                    } else {
                        System.out.println("best move calculating....");
                        ArrayList<int[]> array = b.findLegal(-p1,newBoard);
                        int rand_int1 = rand.nextInt(array.size());
                        int dx = array.get(rand_int1)[0];
                        int dy = array.get(rand_int1)[1];
                        b.moveBoard(dx,dy,-p1,newBoard);
                        b.printBoard(newBoard);
                        move = sc.next();
                        continue;
                    }
                } else {
                    System.out.println("error");
                }

            }
        }
    }
}



