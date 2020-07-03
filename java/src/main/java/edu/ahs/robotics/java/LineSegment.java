package edu.ahs.robotics.java;

public class LineSegment {
    private Point point1;
    private Point point2;

    public LineSegment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point[] subDivide(int subSegments){
        double k;
        Point[] divisions = new Point[subSegments - 1];

        for (int i = 1; i < subSegments; i++){
            double j = i;
            double l = subSegments;
            k = j / l;
            divisions[i - 1] = new Point(this.point1.getX() + (k * (this.point2.getX() - this.point1.getX())), this.point1.getY() + (k * (this.point2.getY() - this.point1.getY())));
            System.out.println(this.point1.getX());
            System.out.println(this.point2.getX());
            System.out.println(k);
            System.out.println(i);
            System.out.println(subSegments);
        }

        System.out.println(divisions);

        return divisions;
    }

}
