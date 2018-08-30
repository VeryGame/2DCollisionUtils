package de.verygame.util;

/**
 * Created by Marco on 04.05.2017.
 *
 * @author Marco Deneke
 */

public final class PolygonUtils {

    private static final int RECTANGLE_VERTEX_COUNT = 4;
    private static float[][] rectanglePoly = new float[RECTANGLE_VERTEX_COUNT][2];

    private PolygonUtils() {
        //Default Constructor
    }

    /**
     * Tries to approximate the center of a polygon. Accurate for convex polygons.
     *
     * @param vertices the given polygon as vertex array
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

    /**
     * Tries to approximate the center of a polygon. Accurate for convex polygons.
     *
     * @param polygon the given polygon
     * @return index 0 for x and index 1 for y coordinate
     */
    public static float[] calculateCenter(float[][] polygon) {

        float[] center = new float[2];

        center[0] = 0;
        center[1] = 0;

        for (int i = 0; i < polygon.length; i ++) {
            center[0] += polygon[i][0];
            center[1] += polygon[i][1];
        }

        center[0] /= (polygon.length);
        center[1] /= (polygon.length);


        return center;
    }

    public static float[][] scaledCopy(float[][] polygon, float xScl, float yScl){
        float[][] cloneP = polygon.clone();

        for (float[] vector : cloneP){
            vector[0] *= xScl;
            vector[1] *= yScl;
        }

        return cloneP;
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
     * Constructs a rectangle polygon.
     *
     * @param width  width of the rectangle
     * @param height height of the rectangle
     * @return new array containing the polygon
     */
    public static float[][] constructRectanglePolygon(float width, float height) {
        int currentIndex = 0;
        // left lower corner
        rectanglePoly[currentIndex][0] = 0; //x
        rectanglePoly[currentIndex][1] = 0; //y
        currentIndex++;

        // right lower corner
        rectanglePoly[currentIndex][0] = width;
        rectanglePoly[currentIndex][1] = 0;
        currentIndex++;

        // right upper corner
        rectanglePoly[currentIndex][0] = width;
        rectanglePoly[currentIndex][1] = height;
        currentIndex++;

        // left upper corner
        rectanglePoly[currentIndex][0] = 0;
        rectanglePoly[currentIndex][1] = height;

        return rectanglePoly.clone();
    }

    public static String toString(float[][] polygon) {

        String res = "";

        StringBuilder resBuilder = new StringBuilder(res);
        resBuilder.append("[");

        for (int i = 0; i < polygon.length; i++) {

            resBuilder.append("[").append(polygon[i][0]).append(", ").append(polygon[i][1]).append("]");

            if (i < polygon.length - 1) {
                resBuilder.append(", ");
            }
        }

        resBuilder.append("]");

        return resBuilder.toString();
    }
}
