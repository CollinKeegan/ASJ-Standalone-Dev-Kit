package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void testDuplicatesRemoved() {
        // Make some points
        Point[] points = new Point[] {new Point(0,0), new Point(3,4), new Point(3,4), new Point(5,5)};
        Path path = new Path(points);

        // Check to make sure that you got rid of the duplicates
        // Your code here!
    }

    @Test
    public void testRejectZeroLengthPath() {
        // Make an empty point array and try to create a path
        Point[] points = new Point[0];
        try {
            Path path = new Path(points);  // should throw IllegalArgumentException
            fail("Expected Path() to throw IllegalArgumentException for empty point array");
        } catch (IllegalArgumentException e) {
            // What we expected; do nothing and let the test pass.
        }
    }

    @Test
    public void testTotalDistance() {
        Point[] points = new Point[] {new Point(0,0), new Point(3,4)};
        Path path = new Path(points);
        assertEquals(5, path.totalDistance(), 0.00001);
    }




}