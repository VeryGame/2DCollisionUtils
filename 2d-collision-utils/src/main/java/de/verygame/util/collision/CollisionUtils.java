package de.verygame.util.collision;

import com.badlogic.gdx.math.Vector2;

import de.verygame.util.ArrayUtils;
import de.verygame.util.PolygonUtils;

/**
 * @author Marco Deneke
 *         <p/>
 *         Created by Marco Deneke on 24.06.2015.
 */
public final class CollisionUtils {

    private static final int POLYGON_VERTEX_COUNT = 3;
    public static final int RECTANGLE_VERTEX_COUNT = 6;
    private static Vector2[] rectanglePoly = new Vector2[RECTANGLE_VERTEX_COUNT];
    private static Vector2 temp = new Vector2();

    private static Vector2 gAxis = new Vector2();
    private static float[] minMaxA = {0, 0};
    private static float[] minMaxB = {0, 0};

    static {
        for (Vector2 v : rectanglePoly) {
            v = new Vector2();
        }
    }

    private CollisionUtils() {
        //private constructor because the class is static
    }

    /**
     * Checks if a rectangle collides with a polygon
     *
     * @param x         global x of rectangle
     * @param y         global y of rectangle
     * @param width     width of rectangle
     * @param height    height of rectangle
     * @param x2        global x of polygon
     * @param y2        global y of polygon
     * @param xScale    x scaling of polygon
     * @param yScale    y scaling of polygon
     * @param otherPoly points of polygon
     * @return true when colliding else false
     */
    public static boolean checkRectanglePolygonCollision(final float x, final float y, final float width, final float height, final float x2, final float y2, final float xScale, final float yScale, final Vector2[] otherPoly) {
        return checkPolygonPolygonCollision(x, y, 1, 1, PolygonUtils.constructRectanglePolygon(width, height), x2, y2, xScale, yScale, otherPoly);
    }

    /**
     * Checks if a Polygon collides with a Circle
     *
     * @param x       x coordinate of the circle
     * @param y       y coordinate of the circle
     * @param r       radius of the circle
     * @param x2      x coordinate of the polygon
     * @param y2      y coordinate of the polygon
     * @param xScale  x scaling of the polygon
     * @param yScale  y scaling of the polygon
     * @param polygon vertices of the Polygon
     * @return true when they collide otherwise false
     */
    public static boolean checkCirclePolygonCollision(float x, float y, float r, float x2, float y2, final float xScale, final float yScale, final Vector2[] polygon) {

        Vector2 first;
        Vector2 second;
        Vector2 third;

        for (int i = 0; i < polygon.length - 2; i += POLYGON_VERTEX_COUNT) {

            first = polygon[i].cpy().scl(xScale, yScale).add(x2, y2);
            second = polygon[i + 1].cpy().scl(xScale, yScale).add(x2, y2);
            third = polygon[i + 2].cpy().scl(xScale, yScale).add(x2, y2);

            if (checkLineCircleCollision(first.x, first.y, second.x, second.y, x + r / 2, y + r / 2, r)) {
                return true;
            }
            if (checkLineCircleCollision(second.x, second.y, third.x, third.y, x + r / 2, y + r / 2, r)) {
                return true;
            }
            if (checkLineCircleCollision(first.x, first.y, third.x, third.y, x + r / 2, y + r / 2, r)) {
                return true;
            }
        }

        //check if one point on the circle is inside of the polygon
        if (contains(x2, y2, ArrayUtils.buildVertexArray(polygon), x + r, y)) {
            return true;
        }


        return false;
    }

    /**
     * Calculates if two polygons collide.
     *
     * @param polygonX x coordinate of first polygon
     * @param polygonY y coordinate of first polygon
     * @param pxScale  x scaling of first polygon
     * @param pyScale  y scaling of first polygon
     * @param polygon  vertices of first polygon
     * @param otherX   x coordinate of second polygon
     * @param otherY   y coordinate of second polygon
     * @param oxScale  x scaling of second polygon
     * @param oyScale  y scaling of second polygon
     * @param other    vertices of second polygon
     * @return true when they collide otherwise false.
     */
    public static boolean checkPolygonPolygonCollision(final float polygonX, final float polygonY, final float pxScale, final float pyScale, final Vector2[] polygon,
                                                       final float otherX, final float otherY, final float oxScale, final float oyScale, final Vector2[] other) {

        if (polygon.length < POLYGON_VERTEX_COUNT || other.length < POLYGON_VERTEX_COUNT) {
            //Invalid Polygon
            return false;
        }

        for (int i = 0; i < polygon.length - 2; i += POLYGON_VERTEX_COUNT) {

            final float polygonVertexX = polygonX + (polygon[i].x * pxScale);
            final float polygonVertexY = polygonY + (polygon[i].y * pyScale);

            final float polygonVertexXOne = polygonX + (polygon[i + 1].x * pxScale);
            final float polygonVertexYOne = polygonY + (polygon[i + 1].y * pyScale);

            final float polygonVertexXTwo = polygonX + (polygon[i + 2].x * pxScale);
            final float polygonVertexYTwo = polygonY + (polygon[i + 2].y * pyScale);

            gAxis.set(polygonVertexYOne - polygonVertexY, -1 * (polygonVertexXOne - polygonVertexX));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }

            gAxis.set(polygonVertexYTwo - polygonVertexYOne, -1 * (polygonVertexXTwo - polygonVertexXOne));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }

            gAxis.set(polygonVertexY - polygonVertexYTwo, -1 * (polygonVertexX - polygonVertexXTwo));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }
        }

        for (int i = 0; i < other.length - 2; i += POLYGON_VERTEX_COUNT) {

            final float polygonVertexX = otherX + (other[i].x * oxScale);
            final float polygonVertexY = otherY + (other[i].y * oyScale);

            final float polygonVertexXOne = otherX + (other[i + 1].x * oxScale);
            final float polygonVertexYOne = otherY + (other[i + 1].y * oyScale);

            final float polygonVertexXTwo = otherX + (other[i + 2].x * oxScale);
            final float polygonVertexYTwo = otherY + (other[i + 2].y * oyScale);

            gAxis.set(polygonVertexYOne - polygonVertexY, -1 * (polygonVertexXOne - polygonVertexX));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }

            gAxis.set(polygonVertexYTwo - polygonVertexYOne, -(polygonVertexXTwo - polygonVertexXOne));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }

            gAxis.set(polygonVertexY - polygonVertexYTwo, -1 * (polygonVertexX - polygonVertexXTwo));

            if (axisSeparatePolygons(gAxis, polygonX, polygonY, pxScale, pyScale, polygon, otherX, otherY, oxScale, oyScale, other)) {
                return false;
            }
        }

        return true;
    }


    /**
     * Checks if a given axis seperates 2 polygons.
     *
     * @param axis     An given axis
     * @param polygonX x coordinates of the first polygon
     * @param polygonY y coordinates of the first polygon
     * @param pxScale  x scaling of the first polygon
     * @param pyScale  y scaling of the first polygon
     * @param polygon  first polygon
     * @param otherX   x coordinates of the second polygon
     * @param otherY   y coordinates of the second polygon
     * @param oxScale  x scaling of the second polygon
     * @param oyScale  y scaling of the second polygon
     * @param other    second polygon
     * @return true when the axis fits between them without touching.
     */
    private static boolean axisSeparatePolygons(final Vector2 axis, final float polygonX, final float polygonY, final float pxScale, final float pyScale, final Vector2[] polygon,
                                                final float otherX, final float otherY, final float oxScale, final float oyScale, final Vector2[] other) {

        calculateInterval(axis, polygonX, polygonY, pxScale, pyScale, polygon, false);
        calculateInterval(axis, otherX, otherY, oxScale, oyScale, other, true);

        if (minMaxA[0] > minMaxB[1] || minMaxB[0] > minMaxA[1]) {
            return true;
        }

        //Needed for determining the distance one object is overlapping the other

        /*float d0 = minMaxA[1] - minMaxB[0]
        float d1 = minMaxB[1] - minMaxA[0]
        float depth = (d0 < d1)? d0 : d1

        float axisLengthSquared = axis.dot(axis)

        //generate push vector from separation axis
        axis = axis.mul(depth/axisLengthSquared)
        */

        return false;
    }

    private static void calculateInterval(final Vector2 axis, final float polygonX, final float polygonY, final float xScale, final float yScale, final Vector2[] polygon, final boolean ab) {

        float d = dot(polygonX, polygonY, polygon[0].x * xScale, polygon[0].y * yScale, axis.x, axis.y);
        if (!ab) {
            minMaxA[0] = d;
            minMaxA[1] = d;

            for (Vector2 vector : polygon) {
                d = dot(polygonX, polygonY, vector.x * xScale, vector.y * yScale, axis.x, axis.y);
                if (d < minMaxA[0]) {
                    minMaxA[0] = d;
                }
                else if (d > minMaxA[1]) {
                    minMaxA[1] = d;
                }
            }
        }
        else {
            minMaxB[0] = d;
            minMaxB[1] = d;

            for (Vector2 vector : polygon) {
                d = dot(polygonX, polygonY, vector.x * xScale, vector.y * yScale, axis.x, axis.y);
                if (d < minMaxB[0]) {
                    minMaxB[0] = d;
                }
                else if (d > minMaxB[1]) {
                    minMaxB[1] = d;
                }
            }
        }

    }

    /**
     * The dot product of 2 vectors
     *
     * @param xOffset x offset on first vector
     * @param yOffset y offset on first vector
     * @param x1      x of the first vector
     * @param y1      y of the first vector
     * @param x2      x of the second vector
     * @param y2      y of the second vector
     * @return the dot product of both vectors with an offset on the first vector.
     */
    private static float dot(final float xOffset, final float yOffset, final float x1, final float y1, final float x2, final float y2) {
        return (xOffset + x1) * x2 + (yOffset + y1) * y2;
    }

    /**
     * Checks if two circles collide
     * Coordinate origin is bottom left.
     *
     * @param x1 x coordinate of the first shape
     * @param y1 y coordinate of the first shape
     * @param r1 radius of the first circle
     * @param x2 x coordinate of the second shape
     * @param y2 y coordinate of the second shape
     * @param r2 radius of the second circle
     * @return <code>true</code> when they collide, else <code>false</code>.
     */
    public static boolean checkCircleCircleCollision(final float x1, final float y1, final float r1, final float x2, final float y2, final float r2) {

        final float xDifference = x2 - x1;
        final float yDifference = y2 - y1;

        final float euclideanDistance = xDifference * xDifference + yDifference * yDifference;

        return euclideanDistance <= (r1 + r2) * (r1 + r2);
    }

    /**
     * Checks if a BoxCollider collides with a CircleCollider.
     * Coordinate system origin is bottom left.
     *
     * @param x1           x coordinate of the rectangle
     * @param y1           y coordinate of the rectangle
     * @param rectWidth    width of the rectangle
     * @param rectHeight   height of the rectangle
     * @param x2           x coordinate of the circle
     * @param y2           y coordinate of the circle
     * @param circleRadius radius of the circle
     * @return true when they collide else false.
     */
    public static boolean checkRectangleCircleCollision(final float x1, final float y1, final float rectWidth, final float rectHeight, final float x2, final float y2, final float circleRadius) {

        final float topY = y1 + rectHeight;

        final float rightX = x1 + rectWidth;

        final float circleCenterX = x2 + circleRadius;
        final float circleCenterY = y2 + circleRadius;

        final boolean contains = contains(x1, y1, rectWidth, rectHeight, x2, y2) && contains(x1, y1, rectWidth, rectHeight, circleCenterX + circleRadius, y2)
                && contains(x1, y1, rectWidth, rectHeight, x2, circleCenterY + circleRadius) && contains(x1, y1, rectWidth, rectHeight, circleCenterX + circleRadius, circleCenterY + circleRadius);

        return checkLineCircleCollision(x1, y1, x1, topY, circleCenterX, circleCenterY, circleRadius)
                || checkLineCircleCollision(x1, y1, rightX, y1, circleCenterX, circleCenterY, circleRadius)
                || checkLineCircleCollision(rightX, y1, rightX, topY, circleCenterX, circleCenterY, circleRadius)
                || checkLineCircleCollision(x1, topY, rightX, topY, circleCenterX, circleCenterY, circleRadius)
                || contains;

    }

    /**
     * Checks collision of a Circle with a line between two points p1 and p2.
     * Circle coordinates start with (0,0) in the middle
     *
     * @param p1X x coordinate of the first point of the line
     * @param p1Y y coordinate of the first point of the line
     * @param p2X x coordinate of the second point of the line
     * @param p2Y y coordinate of the second point of the line
     * @param x   x coordinate of the circle
     * @param y   y coordinate of the circle
     * @param r   radius of the circle
     * @return true when they collide else false.
     */
    public static boolean checkLineCircleCollision(final float p1X, final float p1Y, final float p2X, final float p2Y, final float x, final float y, final float r) {

        // Line segment is (x0, y0) to (x1, y1).
        // Translate everything so that line segment start point to (0, 0)
        final float a = p2X - p1X; // Line segment end point horizontal coordinate
        final float b = p2Y - p1Y; // Line segment end point vertical coordinate
        final float c = x - p1X; // Circle center horizontal coordinate
        final float d = y - p1Y; // Circle center vertical coordinate

        // Collision computation

        if ((d * a - c * b) * (d * a - c * b) <= r * r * (a * a + b * b)) {
            // Collision is possible
            if (c * c + d * d <= r * r) {
                // Line segment start point is inside the circle
                return true;
            }
            if ((a - c) * (a - c) + (b - d) * (b - d) <= r * r) {
                // Line segment end point is inside the circle
                return true;
            }
            if (c * a + d * b >= 0 && c * a + d * b <= a * a + b * b) {
                // Middle section only is inside the circle
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if two rectangle collide, coordinates start at the bottom left
     *
     * @param x1      x coordinates of first rect
     * @param y1      y coordinates of first rect
     * @param width   width of first rect
     * @param height  height of first rect
     * @param x2      x coordinates of second rect
     * @param y2      y coordinates of second rect
     * @param width2  width of second rect
     * @param height2 height of second rect
     * @return true when colliding else false
     */
    public static boolean checkRectangleRectangleCollision(final float x1, final float y1, final float width, final float height, final float x2, final float y2, final float width2, final float height2) {

        final float right1 = x1 + width;
        final float right2 = x2 + width2;
        final float top1 = y1 + height;
        final float top2 = y2 + height2;

        return x1 <= right2 &&
                x2 <= right1 &&
                top1 >= y2 &&
                top2 >= y1;
    }

    /**
     * Checks if a rectangle contains a point
     *
     * @param x      x coordinate of the rectangle
     * @param y      y coordinate of the rectangle
     * @param width  width of the rectangle
     * @param height height of the rectangle
     * @param xPoint x coordinate of the point
     * @param yPoint y coordinate of the point
     * @return true when point is inside of the rectangle.
     */
    public static boolean contains(final float x, final float y, final float width, final float height, final float xPoint, final float yPoint) {
        return x - width / 2 <= xPoint &&
                x + width / 2 >= xPoint &&
                y - height / 2 <= yPoint &&
                y + height / 2 >= yPoint;
    }

    /**
     * Checks if a polygon contains a point with a ray cast
     *
     * @param x        x coordinate of the polygon
     * @param y        y coordinate of the polygon
     * @param vertices copy of local vertices of the polygon
     * @param xPoint   x coordinate of the point
     * @param yPoint   y coordinate of the point
     * @return true when point is inside of the polygon.
     */
    public static boolean contains(final float x, final float y, final float[] vertices, final float xPoint, final float yPoint) {

        float xMax = Float.NEGATIVE_INFINITY;
        float xMin = Float.POSITIVE_INFINITY;

        int i = 0;

        while (i < vertices.length) {

            if (vertices[i] > xMax) {
                xMax = vertices[i];
            }
            if (vertices[i] < xMin) {
                xMin = vertices[i];
            }

            i++;
        }

        float accuracy = (xMax - xMin) / 100;

        int intersectionCount = 0;

        boolean contains = false;

        i = 0;

        while (i < vertices.length && !contains) {

            intersectionCount = 0;

            for (int j = 0; j < POLYGON_VERTEX_COUNT; j++) {
                float v2x1 = vertices[i + j] + x;
                float v2y1 = vertices[i + j + 1] + y;

                float v2x2 = vertices[i + ((j + 1) & POLYGON_VERTEX_COUNT)] + x;
                float v2y2 = vertices[i + ((j + 1) & POLYGON_VERTEX_COUNT) + 1] + y;

                if (lineIntersection(xPoint, yPoint, xMax + accuracy, yPoint, v2x1, v2y1, v2x2, v2y2)) {
                    intersectionCount++;
                }

            }

            contains = intersectionCount % 2 != 0;

            i += 2 * POLYGON_VERTEX_COUNT;
        }

        return contains;
    }

    /**
     * Tests if two lines intersect
     * Source: http://stackoverflow.com/questions/217578/how-can-i-determine-whether-a-2d-point-is-within-a-polygon
     *
     * @param v1x1 x of first line start point
     * @param v1y1 y of first line start point
     * @param v1x2 x of first line end point
     * @param v1y2 y of first line end point
     * @param v2x1 x of second line start point
     * @param v2y1 y of second line start point
     * @param v2x2 x of second line end point
     * @param v2y2 y of second line end point
     * @return true, when the lines intersect
     */
    private static boolean lineIntersection(final float v1x1, final float v1y1, final float v1x2, final float v1y2, final float v2x1, final float v2y1, final float v2x2, final float v2y2) {

        float d1, d2;
        float a, b, c;

        // v1 to line of infinite length
        // We want the line in linear equation standard form: A*x + B*y + C = 0
        a = v1y2 - v1y1;
        b = v1x2 - v1x1;
        c = (v1x2 * v1y1) - (v1x1 * v1y2);

        // Every point (x,y), that solves the equation above, is on the line,
        // The equation will have a positive result if it is on one side of the line and a negative one
        // if it is on the other side. Insert (x1,y1) and (x2,y2) of v2
        // into the equation above.
        d1 = (a * v2x1) + (b * v2y1) + c;
        d2 = (a * v2x2) + (b * v2y2) + c;

        // If d1 and d2 both have the same sign, they are both on the same side
        // of our line 1 and in that case no intersection is possible. Careful,
        // 0 is a special case, that's why we don't test ">=" and "<=",
        // but "<" and ">".
        if (d1 > 0 && d2 > 0) {
            return false;
        }
        if (d1 < 0 && d2 < 0) {
            return false;
        }

        // same as above, but with v2 as infinite line
        a = v2y2 - v2y1;
        b = v2x1 - v2x2;
        c = (v2x2 * v2y1) - (v2x1 * v2y2);

        // Calculate d1 and d2 again, this time using points of v1.
        d1 = (a * v1x1) + (b * v1y1) + c;
        d2 = (a * v1x2) + (b * v1y2) + c;

        // Again, if both have the same sign,
        // no intersection is possible.
        if (d1 > 0 && d2 > 0) {
            return false;
        }
        if (d1 < 0 && d2 < 0) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the moving square faces towards the other square.
     * Precision used to avoid edge cases, where two obstacles are to close to each other.
     *
     * @param bottomLeftX      bottom left x of the moving square
     * @param bottomLeftY      bottom left y of the moving square
     * @param topRightX        top right x of the moving square
     * @param topRightY        top right y of the moving square
     * @param vx               x velocity of the moving square
     * @param vy               y velocity of the moving square
     * @param otherBottomLeftX bottom left x of the other square
     * @param otherBottomLeftY bottom left y of the other square
     * @param otherTopRightX   top right x of the other square
     * @param otherTopRightY   top right y of the other square
     * @return true when it's face is moving towards the other square
     */
    public static boolean facesTowards(final float bottomLeftX, final float bottomLeftY, final float topRightX, final float topRightY, final float vx, final float vy, final float otherBottomLeftX, final float otherBottomLeftY, final float otherTopRightX, final float otherTopRightY) {

        final float precision = 0.000000005f;

        if (vx * vx > vy * vy) {

            return otherBottomLeftY + precision < topRightY && bottomLeftY + precision < otherTopRightY;

        }
        else if (vx * vx < vy * vy) {

            return otherBottomLeftX + precision < topRightX && bottomLeftX + precision < otherTopRightX;
        }


        return false;
    }

}
