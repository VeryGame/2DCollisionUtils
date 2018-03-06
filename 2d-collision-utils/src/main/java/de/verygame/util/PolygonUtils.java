package de.verygame.util;

import com.badlogic.gdx.math.Vector2;

import static de.verygame.util.CollisionUtils.RECTANGLE_VERTEX_COUNT;

/**
 * Created by Marco on 04.05.2017.
 *
 * @author Marco Deneke
 */

public final class PolygonUtils {

    private static Vector2[] rectanglePoly = new Vector2[RECTANGLE_VERTEX_COUNT];
    private static Vector2 temp = new Vector2();


    private PolygonUtils() {
        //Default Constructor
    }

    /**
     * Tries to approximate the center of a polygon
     *
     * @param vertices the given polygon
     * @return index 0 for x and index 1 for y coordinate
     */
    public static float[] calculateCenter(float[] vertices) {

        float[] center = new float[2];

        float sumX = 0;
        float sumY = 0;

        for (int i = 0; i < vertices.length; i += 2) {
            sumX += vertices[i];
            sumY += vertices[i + 1];
        }

        center[0] = sumX / (vertices.length / 2);
        center[1] = sumY / (vertices.length / 2);


        return center;
    }

    public static short[] getDefaultTriangles(float[] polygon) {
        short[] triangles = new short[polygon.length / 2];
        for (short i = 0; i < triangles.length; i++) {
            triangles[i] = i;
        }

        return triangles;
    }

    /**
     * Scales the given local polygon to the given width and height
     *
     * @param vertices polygon to scale
     * @param width    target width
     * @param height   target height
     * @return a new float array containing the scaled vertices
     */
    public static float[] scaleToSize(float[] vertices, float width, float height) {

        float maxX = Float.MIN_VALUE;
        float maxY = Float.MIN_VALUE;

        for (int i = 0; i < vertices.length; i += 2) {
            if (vertices[i] > maxX) {
                maxX = vertices[i];
            }
            if (vertices[i + 1] > maxY) {
                maxY = vertices[i + 1];
            }
        }

        if (Float.compare(maxX, width) == 0 && Float.compare(maxY, height) == 0) {
            return vertices.clone();
        } else {

            float[] nVertices = new float[vertices.length];

            float widthScale = width / maxX;
            float heightScale = height / maxY;

            for (int i = 0; i < vertices.length; i += 2) {
                nVertices[i] = vertices[i] * widthScale;
                nVertices[i + 1] = vertices[i + 1] * heightScale;
            }

            return nVertices;
        }
    }


    /**
     * Constructs a rectangle polygon with two triangles.
     *
     * @param width  width of the rectangle
     * @param height height of the rectangle
     * @return new array containing the polygon
     */
    public static Vector2[] constructRectanglePolygon(float width, float height) {
        int currentIndex = 0;
        // first triangle: left lower corner
        temp.set(0, 0);
        rectanglePoly[currentIndex] = temp.cpy();
        currentIndex++;

        // first triangle: right upper corner
        temp.set(width, height);
        rectanglePoly[currentIndex] = temp.cpy();
        currentIndex++;

        // first triangle: left upper corner
        temp.set(0, height);
        rectanglePoly[currentIndex] = temp.cpy();
        currentIndex++;

        // second triangle: left lower corner
        temp.set(0, 0);
        rectanglePoly[currentIndex] = temp.cpy();
        currentIndex++;

        // second triangle: right lower corner
        temp.set(width, 0);
        rectanglePoly[currentIndex] = temp.cpy();
        currentIndex++;

        // second triangle: right upper corner
        temp.set(width, height);
        rectanglePoly[currentIndex] = temp.cpy();

        return rectanglePoly.clone();
    }
}
