package edu.ahs.robotics.java;

public class Path {

    /**
     * @param rawPoints Array of X,Y points.  Consecutive duplicate points are discarded
     *                  A path must have at least 2 non-identical points
     * @throws IllegalArgumentException for paths with fewer than 2 non-duplicate points.
     */
    public Path(Point[] rawPoints) {

        /**
         * @return total distance of the path
         */
        public double totalDistance () {
            return 0.0;
        }

        /**
         * @return a point at the supplied distance along the path from the supplied current position
         * Note that the point will usually be interpolated between the points that originally defined the Path
         */
        public Path.WayPoint targetPoint (Point current,double distance){
            return new WayPoint(new Point(0, 0), 0.0, 0.0, 0.0);
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


        }

    }


}
