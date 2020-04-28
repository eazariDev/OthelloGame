/**
 * This class is responsible for generating points which held vertical and horizontal coordinates for each point.
 */
public class Point{
    //The vertical coordinate
    private int xCoordinate;
    //The horizontal coordinate
    private int yCoordinate;

    /**
     * It will make a point base on the inputs, user enter.
     * @param x The vertical coordinate of the new point.
     * @param y The horizontal coordinate of the new point.
     */
    public Point(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
    }
    public Point(){
        this(0 , 0);
    }

    /**
     * This method would set the vertical coordinate base on the input.
     * @param x The new vertical coordinate, user enters.
     */
    public void setxCoordinate(int x){xCoordinate = x;}

    /**
     * This method would set the horizontal coordinate base on the input.
     * @param y The new horizontal coordinate, user enters.
     */
    public void setyCoordinate(int y){yCoordinate = y;}

    /**
     * This method would return the vertical coordinate.
     * @return  xCoordinate field.
     */
    public int getxCoordinate(){return xCoordinate;}
    /**
     * This method would return the horizontal coordinate.
     * @return  yCoordinate field.
     */
    public int getyCoordinate(){return yCoordinate;}
}