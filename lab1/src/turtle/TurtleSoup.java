/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.*;
import java.lang.Math;

public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.forward(sideLength);
        turtle.turn(90.0);
        turtle.forward(sideLength);

    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180.0 - 360.0 / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return Integer.parseInt(new java.text.DecimalFormat("0").format(360.0 / (180.0 - angle)));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double degrees = 180.0 - calculateRegularPolygonAngle(sides);

        for (int i = 1; i <= sides; ++i) {
            turtle.forward(sideLength);
            turtle.turn(degrees);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing,
                                                 int currentX, int currentY,
                                                 int targetX, int targetY) {

        if (Math.abs(currentX - targetX) < 0.00001 && Math.abs(currentY - targetY) < 0.00001) {
            return 0.0;
        }

        double temp_degrees = 0.0;

        if (Math.abs(currentX - targetX) < 0.00001) {

            if (Math.abs(currentY - targetY) > 0.0001) {
                temp_degrees = 0.0;
            } else if (Math.abs(currentY - targetY) < -0.0001) {
                temp_degrees = 180.0;
            }

        } else if (Math.abs(currentY - targetY) < 0.00001) {
            if (Math.abs(currentX - targetX) > 0.00001) {
                temp_degrees = 90.0;
            } else if (Math.abs(currentX - targetX) < -0.00001) {
                temp_degrees = 270.0;
            }
        } else {
            temp_degrees = Math.atan(((double) Math.abs(currentX - targetX)) / ((double) Math.abs(currentY - targetY))) / Math.PI * 180.0;
        }

        if (targetX >= currentX && targetY >= currentY) {
            temp_degrees = 0 + temp_degrees;
        } else if (targetX >= currentX) {
            temp_degrees = 180.0 - temp_degrees;
        } else if (targetY <= currentY) {
            temp_degrees = 180.0 + temp_degrees;
        } else {
            temp_degrees = 360.0 - temp_degrees;
        }

        if (temp_degrees >= currentBearing) return temp_degrees - currentBearing;
        else return 360.0 + (temp_degrees - currentBearing);
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {

        List<Double> result = new ArrayList<Double>(xCoords.size());

        double temp_degrees = 0;
        double now_degrees = 0;


        for (int i = 1; i < xCoords.size(); ++i) {

            temp_degrees = calculateBearingToPoint(now_degrees, xCoords.get(i - 1), yCoords.get(i - 1),
                    xCoords.get(i), yCoords.get(i));

            now_degrees += temp_degrees;

            now_degrees = now_degrees - (int) (now_degrees / 360.0) * 360.0;

            result.add(temp_degrees);
        }

        return result;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */

    public static Set<Point> convexHull(Set<Point> points) {

        Set<Point> result = null;
        return result;
    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        turtle.turn(330);

        int sideLength = 60;

        for (int i = 1; i <= 6; ++i) {
            turtle.forward(sideLength);
            turtle.turn(120);

            turtle.forward(sideLength);
            turtle.turn(300);
        }

        turtle.turn(60);

        for (int i = 1; i <= 6; ++i) {
            turtle.forward(sideLength);
            turtle.turn(60);
        }

    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);
//        drawRegularPolygon(turtle, 6, 40);
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
