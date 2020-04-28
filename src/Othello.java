/**
 * This is the othello game for two player games, human to human.
 * The whole game would run in the Othello class.
 *
 * @author Erfan. Azari
 * @version 1.2
 */

import javax.swing.*;
import java.util.*;
public class Othello{
    //The current player who is turn to play
    private int currentPlayer;
    //Show the score of the white player
    private int whiteScore;
    //Show the score of the black player
    private int blackScore;
    //The array of the game which save the positions and states of each place in the game
    private int[][] gameMatrix =new int[9][9];
    //The list which saves the possible moves for each player in each round
    private ArrayList<Point> possibleMoves;


    /**
     * Create a new game with the first default positions. The default options are:
     *  - The black play the first round
     *  - When game begins, both white and black have two points in the fixed positions.
     * @param gameMatrix When the game begins, we initialize all positions in the game with 0, means there is nor black neither white points in them.
     * @param currentPlayer The first round is for black and we show black player with number 1.
     *
     */
    public Othello(){
        for (int i = 1 ; i < 9 ; i++)
            for (int j = 1 ; j < 9 ; j++)
                gameMatrix[i][j] = 0;
        gameMatrix[4][4] = -1;
        gameMatrix[5][5] = -1;
        gameMatrix[4][5] = 1;
        gameMatrix[5][4] = 1;
        currentPlayer = 1;
        possibleMoves = new ArrayList<>();
    }

    /**
     * This method will start the game and keep the progress the game until it ends and there is no possible moves.
     */
    public void startGame(){
        Scanner input = new Scanner(System.in);
        while(!gameIsOver()) {
            Point playerInput = new Point();
            if ( findPossibleMoves(possibleMoves, currentPlayer, true) > 0){
                do {
                    playerInput.setxCoordinate(input.nextInt());
                    playerInput.setyCoordinate(input.next().charAt(0) - 64);
                } while (!checkMove(playerInput));
                gameMatrix[playerInput.getxCoordinate()][playerInput.getyCoordinate()] = currentPlayer;
                changeColor(playerInput.getxCoordinate(), playerInput.getyCoordinate());
            }else{
                System.out.println("Pass");
                gameDisplay(gameMatrix);
            }
            currentPlayer *= -1 ;
        }
        showGameResult();
    }

    /**
     * This method would find the possible moves which white or black player can make them and place them in the list
     * @param possibleMoves It is the list which holds all the possible moves which each player can make.
     * @param player    It shows which player is playing now
     * @param printMap  If it's true, then the method would print the game map with possible moves marked in it.
     * @return True, if there are possible moves for the player and return false if there is no possible moves left.
     */
    private int findPossibleMoves(ArrayList<Point> possibleMoves, int player, boolean printMap) {
        int[][] temp = new int[9][9];
        for (int m = 0; m < 9; m++)
            for (int n = 0; n < 9; n++)
                temp[m][n] = gameMatrix[m][n];
        possibleMoves.removeAll(possibleMoves);
        int otherPlayer = -1 * player;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (gameMatrix[i][j] == currentPlayer) {
                    colorToChange(possibleMoves, -1, -1, i, j, 0);
                    colorToChange(possibleMoves, -1, 0, i, j, 0);
                    colorToChange(possibleMoves, -1, 1 ,i ,j, 0);
                    colorToChange(possibleMoves, 0, 1 ,i ,j, 0);
                    colorToChange(possibleMoves, 1, 1 ,i ,j, 0);
                    colorToChange(possibleMoves, 1, 0 ,i ,j, 0);
                    colorToChange(possibleMoves, 1, -1 ,i ,j, 0);
                    colorToChange(possibleMoves, 0, -1 ,i ,j, 0);
                }
            }
        }
        //If there is none possible moves, it would pass the turn to other player.
        if (possibleMoves.size() == 0) {
            System.out.println("No possible moves!\n       Pass!\n\n");
            currentPlayer *= -1;
            return possibleMoves.size();
        }
        //If there are possible moves, it would print the new map game and the possible moves showed in the map.
        if (printMap == true){
        for (int k = 0; k < possibleMoves.size(); k++)
            temp[possibleMoves.get(k).getxCoordinate()][possibleMoves.get(k).getyCoordinate()] = 10;
        gameDisplay(temp);
        if (currentPlayer == 1)
            System.out.println("    Blackplayer, please place your move: ");
        else
            System.out.println("    Whiteplayer, please place your move: ");
    }
        return possibleMoves.size();
    }

    /**
     * This method would check if the choosen move from player is valid or not
     * @param point1 It is the move which player choose to do
     * @return  If it is a valid move, it would return true and if not, it would return false.
     */
    private boolean checkMove(Point point1){
        for (Point point: possibleMoves){
            if (point.getxCoordinate() == point1.getxCoordinate() && point.getyCoordinate() == point1.getyCoordinate())
                return true;
        }
        System.out.println("No a valid Move! please try again: ");
        return false;
    }

    /**
     * This method would call the colorToChange method first, to determine the positions which needs to change color and in the end, it would change the positions in the game array.
     * @param x Represents the vertical place of the chosen point of the player
     * @param y Represents the horizontal place of the chosen point of the player.
     */
    private void changeColor(int x, int y){
        ArrayList<Point> toChange = new ArrayList<>();
        colorToChange(toChange, -1, -1, x, y, currentPlayer);
        colorToChange(toChange, -1, 0, x, y, currentPlayer);
        colorToChange(toChange, -1, 1 ,x ,y, currentPlayer);
        colorToChange(toChange, 0, 1 ,x ,y, currentPlayer);
        colorToChange(toChange, 1, 1 ,x ,y, currentPlayer);
        colorToChange(toChange, 1, 0 ,x ,y, currentPlayer);
        colorToChange(toChange, 1, -1 ,x ,y, currentPlayer);
        colorToChange(toChange, 0, -1 ,x ,y, currentPlayer);
        for ( int k = 0 ; k < toChange.size() ; k++)
            gameMatrix[toChange.get(k).getxCoordinate()][toChange.get(k).getyCoordinate()] = currentPlayer;
        toChange.removeAll(toChange);
    }

    /**
     * This method will check the possible positions which needs to change color due to player move.
     * @param toChange  It is the list which keeps the positions coordinates, need to be changed.
     * @param i It determined the direction of checking the positions
     * @param j It determined the direction of checking the positions
     * @param xSelected It is vertical place of the chosen position
     * @param ySelected It is horizontal place of the chosen position
     * @param state It determined, whether look for possible moves or positions for changing color.
     */
    private void colorToChange(ArrayList<Point> toChange, int i, int j, int xSelected, int ySelected, int state){
        int otherPlayer = -1 * currentPlayer;
        int tempX = xSelected, tempY = ySelected;
        if ((xSelected + (2 * i) <= 8) && (xSelected + (2 * i) >= 1) && (ySelected + (2 * j) <= 8) && (ySelected + (2 * j) >= 1)) {
            do {
                tempX += i;
                tempY += j;
                if (tempX + i >= 1 && tempY + j >= 1 && tempX + i <= 8 && tempY + j <= 8)
                    if (gameMatrix[tempX][tempY] == otherPlayer && gameMatrix[tempX + i][tempY + j] == state) {
                        if (state == 0)
                         toChange.add(new Point(tempX + i, tempY + j));
                        else
                            while (!(xSelected == tempX && ySelected == tempY)) {
                                xSelected += i;
                                ySelected += j;
                                toChange.add(new Point(xSelected, ySelected));
                            }
                    }
            } while (tempX < 9 && tempX > 0 && tempY < 9 && tempY > 0 && gameMatrix[tempX][tempY] == otherPlayer);
        }
    }

    /**
     * This method would print the game play and show all the positions and their status in the game.
     */
    private void gameDisplay(int[][] matrix){
        blackScore = 0;
        whiteScore = 0;
        System.out.println("             A  B  C  D  E  F  G  H ");
        for ( int i = 1 ; i < 9 ; i++){
            for ( int j = 0 ; j < 9 ; j++){
                if ( j == 0)
                    System.out.print("          " + i + "  ");
                else {
                    if (matrix[i][j] == 1) {
                        System.out.print("\u25CB" + "  ");
                        blackScore++;
                    }
                    else if (matrix[i][j] == -1) {
                        System.out.print("\u25CF" + "  ");
                        whiteScore++;
                    }
                    else if (matrix[i][j] == 10)
                        System.out.print("?" + "  ");
                    else
                        System.out.print("-" + "  ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("                  Score Board         ");
        System.out.println("         White: " + whiteScore + "        "+ "    Black: " + blackScore);
    }

    /**
     * This method would check if the game ends or not base on two conditions:
     *  - First there is no more positions to move
     *  - Second, there is no more positions to move for both player
     * @return  It would return true, if either conditions are true and return false if, neither of them are true.
     */
    private boolean gameIsOver(){
        if ( whiteScore + blackScore == 64  || (findPossibleMoves(possibleMoves, 1, false) == 0  && findPossibleMoves(possibleMoves, -1, false) == 0))
            return true;
        return false;
    }

    /**
     * When the game ends, this method would print the result of the game to the screen.
     */
    private void showGameResult(){
        System.out.println("Your game is over!");
        if (whiteScore > blackScore)
            System.out.println("Whiteplayer won the game!");
        else if (blackScore > whiteScore)
            System.out.println("Blackplayer won the game!");
        else
            System.out.println("No one won the game! The game is draw!");
    }
}
