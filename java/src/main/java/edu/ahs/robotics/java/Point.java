package edu.ahs.robotics.java;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point getCoords(){
        Point x = new Point(this.x, this.y);
        return x;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public double distanceFromOrigin(){

        double distance = Math.sqrt(x*x+y*y);

        return distance;

    }

    public String getQuadrant(){
        if (x > 0 && y > 0) {
            return "Quadrant 1";
        } else if (x<0 && y>0){
            return "Quadrant 2";
        } else if (x<0 && y<0){
            return "Quadrant 3";
        } else if (x>0 && y<0){
            return "Quadrant 4";
        } else if (x==0 || y==0){
            return "Axis";
        }
        else {
            return null;
        }
    }

}
