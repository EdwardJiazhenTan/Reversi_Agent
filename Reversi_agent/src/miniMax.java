import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class miniMax extends board{

    board board = new board();
    int depth;

    //alpha is the minimum boundary for maximizing
    //beta  is the maximum boundary for minimizing
    public int minimax(int[] position,int depth, int alpha, int beta, int currentColor, int[][] theBoard, int playerColor) {
        int[] bestMove = new int[2];
        if (depth == 0 || findLegal(1,theBoard) == null && findLegal(-1, theBoard) == null) {
            int[] result = board.checkColor(theBoard);
            if (playerColor == 1) {//white = 1, black = -1
                return result[0];//if player choose white, we need the utility score of black
            } else {
                return result[1];//vice versa
            }
        }
        else {

            if(findLegal(currentColor,theBoard) != null) {
                if (currentColor == playerColor) {//maximizing
                    int maxEval = Integer.MIN_VALUE;
                    int newDepth = depth - 1;

                    ArrayList<int[]> children  = board.findLegal(currentColor, theBoard);
                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = minimax(child,newDepth, alpha, beta, -currentColor, newBoard, playerColor);
                        maxEval = max(maxEval, eval);
                        alpha = max(alpha, eval);
                        if (beta <= alpha) {//when the maximum boundary for minimizing from the upper level is smaller than the minimum boundary for maximizing at this level
                            break;
                        }
                    }
                    return maxEval;
                }
                else{//minimizing
                    int minEval = Integer.MAX_VALUE;
                    int newDepth = depth - 1;

                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);

                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = minimax(child,newDepth, alpha, beta, -currentColor, newBoard, playerColor);
                        if(eval < minEval) {
                            bestMove = child;
                            minEval = eval;
                            setBestMove(bestMove);
                        }
                        beta = min(beta, eval);

                        if (beta <= alpha) {//when the minimum boundary for maximizing from the upper level is larger than the maximum boundary for minimizing at this level
                            break;
                        }
                    }
                    return minEval;
                }
            }
            else {
                currentColor*=-1;
                if (currentColor == playerColor) {//maximizing
                    int maxEval = Integer.MIN_VALUE;
                    int newDepth = depth - 1;

                    ArrayList<int[]> children  = board.findLegal(currentColor, theBoard);
                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = minimax(child,newDepth, alpha, beta, -currentColor, newBoard, playerColor);

                        maxEval = max(maxEval, eval);
                        alpha = max(alpha, eval);
                        if (beta <= alpha) {//when the maximum boundary for minimizing from the upper level is smaller than the minimum boundary for maximizing at this level
                            break;
                        }
                    }
                    return maxEval;
                }
                else{//minimizing
                    int minEval = Integer.MAX_VALUE;
                    int newDepth = depth - 1;

                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);

                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = minimax(child,newDepth, alpha, beta, -currentColor, newBoard, playerColor);
                        if(eval < minEval) {
                            bestMove = child;
                            minEval = eval;
                            setBestMove(bestMove);
                        }
                        beta = min(beta, eval);
                        if (beta <= alpha) {//when the minimum boundary for maximizing from the upper level is larger than the maximum boundary for minimizing at this level
                            break;
                        }
                    }
                    return minEval;
                }

            }

        }
    }


    public void getBestMove(int currentColor, int playerColor, int[][] theBoard) {

        int[] bestMove = new int[2];
        depth = 10;
                //board.blankNumber(theBoard) - 1;
        int maxUtility = Integer.MIN_VALUE;

        ArrayList<int[]> allPossible =  board.findLegal(currentColor, theBoard);

        for(int[] possible : allPossible) {
            int utility = minimax(possible, depth, Integer.MAX_VALUE, Integer.MIN_VALUE, currentColor, theBoard, playerColor);
         //   System.out.println("utility for this move is  " + utility);
            if(utility > maxUtility) {
                bestMove = possible;
                maxUtility = utility;
            }
        }
        // System.out.println(depth + "   " + maxUtility);
      //  return bestMove;
    }



    private int[] bestMove;
    public int[] getBestMove(){
        return bestMove;
    }
    public void setBestMove(int[] newMove){
        this.bestMove = newMove;
    }


    public void getBestMoveMinimax(int currentColor, int playerColor, int[][] theBoard, int depth1) {

        int[] bestMove = new int[2];
        //board.blankNumber(theBoard) - 1;
        int maxUtility = Integer.MIN_VALUE;

        ArrayList<int[]> allPossible =  board.findLegal(currentColor, theBoard);

        for(int[] possible : allPossible) {
            int utility = miniMax_noABP(possible, depth1, currentColor, theBoard, playerColor);
            //   System.out.println("utility for this move is  " + utility);
            if(utility > maxUtility) {
                bestMove = possible;
                maxUtility = utility;
            }
        }
        //return bestMove;
    }

    private int[] bestMoveMinimax;
    public int[] getBestMoveMinimax(){
        return bestMoveMinimax;
    }
    public void setBestMoveMinimax(int[] newMove){
        this.bestMoveMinimax = newMove;
    }


    public int miniMax_noABP(int[] position, int depth, int currentColor, int[][] theBoard, int playerColor) {

        int[] bestMove = new int[2];

        if (depth == 0 || findLegal(1, theBoard) == null && findLegal(-1, theBoard) == null) {
            int[] result = board.checkColor(theBoard);
            if (playerColor == 1) {//white = 1, black = -1
                return result[0];//if player choose white, we need the utility score of black
            } else {
                return result[1];//vice versa
            }
        } else {
            if (findLegal(currentColor, theBoard) != null) {
                if (currentColor == playerColor) {//maximizing
                    int maxEval = Integer.MIN_VALUE;
                    int newDepth = depth - 1;

                    currentColor = -1 * currentColor;
                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);

                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = miniMax_noABP(child, newDepth, currentColor, newBoard, playerColor);

                        maxEval = max(maxEval, eval);
                    }
                    return maxEval;
                } else {//minimizing
                    int minEval = Integer.MAX_VALUE;
                    int newDepth = depth - 1;
                    currentColor = -1 * currentColor;
                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);

                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = miniMax_noABP(child, newDepth, currentColor, newBoard, playerColor);
                        if(eval < minEval) {
                            bestMove = child;
                            minEval = eval;
                            setBestMoveMinimax(child);
                        }

                    }
                    return minEval;
                }
            } else {
                currentColor *= -1;
                if (currentColor == playerColor) {//maximizing
                    int maxEval = Integer.MIN_VALUE;
                    int newDepth = depth - 1;
                    currentColor = -1 * currentColor;
                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);

                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = miniMax_noABP(child, newDepth, currentColor, newBoard, playerColor);
                        maxEval = max(maxEval, eval);
                    }
                    return maxEval;
                } else {//minimizing
                    int minEval = Integer.MAX_VALUE;
                    int newDepth = depth - 1;


                    currentColor = -1 * currentColor;
                    ArrayList<int[]> children = board.findLegal(currentColor, theBoard);
                    for (int[] child : children) {
                        int[][] newBoard = board.tryMoveBoard(child[0], child[1], currentColor, theBoard);
                        int eval = miniMax_noABP(child, newDepth, currentColor, newBoard, playerColor);
                        if(eval < minEval) {
                            bestMove = child;
                            minEval = eval;
                            setBestMoveMinimax(child);
                        }
                    }
                    return minEval;
                }
            }
        }
    }


}