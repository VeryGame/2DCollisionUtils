package de.verygame.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @author Rico Schrage, Marco Deneke
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
        res += "[";
        for (int i = 0; i < vertices.length; i++) {
            if (i < vertices.length - 1) {
                res += (vertices[i] + ", ");
            }
            else {
                res += (Float.toString(vertices[i]));
            }
        }
        res += "}";
        return res;
    }

    public static Vector2[] removeDuplicates(Vector2[] vertices) {
        Deque<Vector2> stack = new ArrayDeque<>();

        for (Vector2 v : vertices) {
            if (!stack.contains(v)) {
                stack.push(v);
            }
        }

        Vector2[] reducedVertices = new Vector2[stack.size()];
        return stack.toArray(reducedVertices);
    }

    /**
     * Removes duplicate vertices from a sorted FloatArray
     */
    public static void removeDuplicates(FloatArray vertices) {
        for (int i = 0; i < vertices.size - 2; i += 2) {
            if (MathUtils.isEqual(vertices.get(i), vertices.get(i + 2)) && MathUtils.isEqual(vertices.get(i + 1), vertices.get(i + 3))) {
                vertices.removeRange(i + 2, i + 3);
            }
        }
    }

    public static int wrapIndex(int i, int arrayLength) {
        return i >= 0 ? (i % arrayLength) : ((i + arrayLength) % arrayLength);
    }

}