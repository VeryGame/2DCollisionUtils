package de.verygame.util;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Marco Deneke
 */
public class ArrayUtils {

    private ArrayUtils() {
        //utility class
    }

    public static float[] createAndFill(int size, float value) {
        float[] res = new float[size];
        for (int i = 0; i < size; ++i) {
            res[i] = value;
        }
        return res;
    }

    public static float[] buildVertexArray(Vector2[] vertices) {

        float[] res = new float[vertices.length * 2];

        for (int i = 0, j = 0; j < vertices.length; i += 2, j++) {
            res[i] = vertices[j].x;
            res[i + 1] = vertices[j].y;
        }

        return res;
    }

    public static String toString(float[] vertices) {

        String res = "";

        StringBuilder resBuilder = new StringBuilder(res);
        resBuilder.append("[");

        for (int i = 0; i < vertices.length; i++) {
            if (i < vertices.length - 1) {
                resBuilder.append(vertices[i]).append(", ");
            } else {
                resBuilder.append(Float.toString(vertices[i]));
            }
        }

        res = resBuilder.toString();
        res += "}";

        return res;
    }
}