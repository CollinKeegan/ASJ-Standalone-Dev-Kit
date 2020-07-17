package edu.ahs.robotics.java;

import java.util.ArrayList;

public class Path {

    private ArrayList<WayPoint> wayPoints;
    private int wayPointAlong;

    public void resetWayPointAlong() {
        this.wayPointAlong = 1;
    }

    /**
     * @param rawPoints Array of X,Y points.  Consecutive duplicate points are discarded
     *                  A path must have at least 2 non-identical points
     * @throws IllegalArgumentException for paths with fewer than 2 non-duplicate points.
     */
    public Path(Point[] rawPoints) {
        wayPoints = new ArrayList<>();

        try{

            Point prevPoint = rawPoints[0];
            wayPoints.add(new WayPoint(rawPoints[0], 0, 0, 0));

            if(rawPoints.length > 1){
                for(int i = 1; i < rawPoints.length; i++) {

                    if(rawPoints[i].getX() != prevPoint.getX() || rawPoints[i].getY() != prevPoint.getY()){
                        wayPoints.add(new WayPoint(rawPoints[i], rawPoints[i].getX() - rawPoints[i-1].getX(), rawPoints[i].getY() - rawPoints[i-1].getY(), Math.sqrt((rawPoints[i].getX() - rawPoints[i-1].getX())*(rawPoints[i].getX() - rawPoints[i-1].getX())+(rawPoints[i].getY() - rawPoints[i-1].getY())*(rawPoints[i].getY() - rawPoints[i-1].getY()))));
                        prevPoint = rawPoints[i];
                    } else {
                        throw new IllegalArgumentException("A Path must be defined by at least two non-duplicate points.");
                    }

                }
                
                wayPointAlong = 1;
            } else {
                throw new IllegalArgumentException("A Path must be defined by at least two non-duplicate points.");
            }

        } catch(Exception e){

            throw new IllegalArgumentException("A Path must be defined by at least two non-duplicate points.");

        }


    }



    /**
     * @return total distance of the path
     */
    public double totalDistance() {

        double distance = 0;

        for(int i = 0; i < this.wayPoints.size() - 1; i++){
            distance += wayPoints.get(i).distanceFromPreviousWaypoint();
        }

        return distance;

    }

    /**
     * @return a point at the supplied look-ahead distance along the path from the supplied current position
     * Note that the point will usually be interpolated between the points that originally defined the Path
     */
    public Path.WayPoint targetPoint(Point current, double targetDistance) {


        WayPoint returnWaypoint = null;


        //sets nextWaypoint to the next waypoint along the path with a positive componentAlongPath value and sets wayPointAlong to the index on arraylists waypoints

        WayPoint nextWaypoint = null;

        for(; wayPointAlong < wayPoints.size() - 1; wayPointAlong++){

            if(wayPoints.get(wayPointAlong).componentAlongPath(current) > 0){

                nextWaypoint = wayPoints.get(wayPointAlong);

            }

        }


        //returns correct waypoint

        if(nextWaypoint.componentAlongPath(current) > targetDistance){

            double z = nextWaypoint.componentAlongPath(current) - targetDistance;
            double c = nextWaypoint.distanceFromPrevious;

            double x = (z / c) * nextWaypoint.deltaXFromPrevious;
            double y = (z / c) * nextWaypoint.deltaYFromPrevious;

            double deltaX = nextWaypoint.point.getX() - wayPoints.get(wayPointAlong - 1).point.getX();
            double deltaY = nextWaypoint.point.getY() - wayPoints.get(wayPointAlong - 1).point.getY();

            return new WayPoint(new Point(nextWaypoint.point.getX() - x, nextWaypoint.point.getY() - y), deltaX, deltaY, Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        }else if(nextWaypoint.componentAlongPath(current) == targetDistance){

            return nextWaypoint;

        }else {

            boolean segmentFound = false;
            int wayPointAfterTarget = wayPointAlong + 1;
            double searchDistance = targetDistance - nextWaypoint.componentAlongPath(current);
            WayPoint a;

            while(segmentFound = false){

                if (wayPoints.get(wayPointAfterTarget).distanceFromPrevious < targetDistance){

                    a = wayPoints.get(wayPointAfterTarget);
                    double z = a.distanceFromPrevious - searchDistance;
                    double c = a.distanceFromPrevious;
                    segmentFound = true;

                    double x = (z / c) * wayPoints.get(wayPointAfterTarget).deltaXFromPrevious;
                    double y = (z / c) * wayPoints.get(wayPointAfterTarget).deltaYFromPrevious;

                    double deltaX = wayPoints.get(wayPointAfterTarget).point.getX() - wayPoints.get(wayPointAfterTarget - 1).point.getX();
                    double deltaY = wayPoints.get(wayPointAfterTarget).point.getY() - wayPoints.get(wayPointAfterTarget - 1).point.getY();

                    return new WayPoint(new Point(nextWaypoint.point.getX() - x, nextWaypoint.point.getY() - y), deltaX, deltaY, Math.sqrt(deltaX * deltaX + deltaY * deltaY));


                }else if (wayPoints.get(wayPointAfterTarget).distanceFromPrevious == targetDistance){

                    return wayPoints.get(wayPointAfterTarget);
                    segmentFound = true;

                }else {

                    searchDistance = searchDistance - wayPoints.get(wayPointAfterTarget).distanceFromPrevious;
                    wayPointAfterTarget ++;

                }

            }

        }

    }

    public static double pathComponent(){
        WayPoint a = new WayPoint(new Point(4, 4), 4, 4, 5.65685424949);
        System.out.println(a.componentAlongPath(new Point(2, 2)));
        return(a.componentAlongPath(new Point(2, 2)));
    }

    public static class WayPoint {
        public Point point;
        private double deltaXFromPrevious;
        private double deltaYFromPrevious;
        private double distanceFromPrevious;

        private WayPoint(Point point, double deltaXFromPrevious, double deltaYFromPrevious, double distanceFromPrevious) {
            this.point = point;
            this.deltaXFromPrevious = deltaXFromPrevious;
            this.deltaYFromPrevious = deltaYFromPrevious;
            this.distanceFromPrevious = distanceFromPrevious;
        }

        public Point getPoint() {
            return point;
        }

        public double distanceFromPreviousWaypoint(){
            return this.distanceFromPrevious;
        }

        /**
         * Calculates the projection of the vector Vcurrent leading from the supplied current
         * point to this WayPoint onto the vector Vpath leading from the previous point on the path
         * to this WayPoint.  If the return value is positive, it means that the WayPoint is
         * farther along the path from the current point.  If the return value is negative, it means
         * that the WayPoint is before the current point (earlier on the path).
         *The magnitude of the value tells the
         * distance along the path.  The value is computed as the dot product between Vcurrent and
         * Vpath, normalized by the length of vPath
         * @param current The source point to compare to the WayPoint
         */
        private double componentAlongPath(Point current) {
            double deltaXFromCurrent = point.getX() - current.getX();
            double deltaYFromCurrent = point.getY() - current.getY();

            double dp = deltaXFromCurrent * deltaXFromPrevious + deltaYFromCurrent * deltaYFromPrevious;
            return dp / distanceFromPrevious;
        }

    }

}