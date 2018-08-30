package de.verygame.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 12.02.2016.
 */
public class CollisionUtilsTest {

    private float[][] firstPoly =  {{0,0}, {5, 5}, {0, 5}};

    private float[][] secondPoly = {{0, 0}, {5, 0}, {5, 5}, {0, 5}};

    @Test
    public void testCheckPolygonRectangleCollision() {

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(15, 10, 10, 10, 20, 6, secondPoly));

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(15, 10, 20, 10, 20, 6, PolygonUtils.scaledCopy(secondPoly, 2, 1)));

        assertFalse(CollisionUtils.checkRectanglePolygonCollision(5, 0, 6, 8, 12, 5, secondPoly));

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(10, 10, 20, 20, 0, 0, PolygonUtils.scaledCopy(firstPoly, 2, 2)));
    }

    @Test
    public void testCheckPolygonCircleCollision(){

        assertTrue(CollisionUtils.checkCirclePolygonCollision(0, 0, 3, 3, 3, secondPoly));

        assertFalse(CollisionUtils.checkCirclePolygonCollision(15, 25, 6, 15, 15, firstPoly));

        assertTrue(CollisionUtils.checkCirclePolygonCollision(15, 15, 5, 19, 13, firstPoly));
    }

    @Test
    public void testCheckPolygonPolygonCollision(){

        assertTrue(CollisionUtils.checkPolygonPolygonCollision(20, 10, firstPoly, 23, 10, firstPoly));

        assertTrue(CollisionUtils.checkPolygonPolygonCollision(20, 10, firstPoly, 22, 8, secondPoly));

        assertTrue(CollisionUtils.checkPolygonPolygonCollision(20, 10, secondPoly, 15, 10, secondPoly));

        assertFalse(CollisionUtils.checkPolygonPolygonCollision(20, 10, firstPoly, 26, 10, firstPoly));

        assertFalse(CollisionUtils.checkPolygonPolygonCollision(20, 10, firstPoly, 26, 8, secondPoly));

        assertFalse(CollisionUtils.checkPolygonPolygonCollision(20, 10, secondPoly, 14, 10, secondPoly));

        float[][] incPoly = new float[][]{new float[]{42, 42}, new float[]{13,37}};

        assertFalse(CollisionUtils.checkPolygonPolygonCollision(20, 10, firstPoly, 20, 10, incPoly));
    }

    @Test
    public void testCheckCircleCircleCollision(){

        assertTrue(CollisionUtils.checkCircleCircleCollision(50, 60, 40, 100, 60, 50));

        assertFalse(CollisionUtils.checkCircleCircleCollision(50, 60, 10, 100, 60, 20));
    }

    @Test
    public void testCheckRectangleCircleCollision(){

        assertTrue(CollisionUtils.checkRectangleCircleCollision(15, 15, 100, 20, 20, 20, 10));

        assertFalse(CollisionUtils.checkRectangleCircleCollision(50, 30, 15, 15, 100, 10, 6));
    }

    @Test
    public void testCheckLineCircleCollision(){

        assertTrue(CollisionUtils.checkLineCircleCollision(5, 5, 20, 20, 6, 6, 10));

        assertTrue(CollisionUtils.checkLineCircleCollision(10, 15, 20, 15, 10, 0, 15));

        assertFalse(CollisionUtils.checkLineCircleCollision(50, 50, 100, 100, 2, 5, 15));
    }

    @Test
    public void testContains(){

        assertTrue(CollisionUtils.contains(5, 5, 10, 10, 7, 7));

        assertFalse(CollisionUtils.contains(15, 15, 5, 5, 50, 25));
    }

    @Test
    public void testScaleToSize(){
        float[] polygon = {0,0,0,3,3,3,0,3,3,6,3,3};

        polygon = PolygonUtils.scaleToSize(polygon, 100, 100);

        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        float i = 1;

        float width = 0;
        float height = 0;

        for(float f : polygon){
            if(i == 1){
                if(width <= f){
                    width = f;
                }

                i *=-1;
            } else {
                if(height <= f){
                    height = f;
                }
                i*=-1;
            }

            if(f <= min){
                min = f;
            } else if(f >= max){
                max = f;
            }
        }

        assertTrue(min == 0);
        assertTrue(max == 100);
        assertTrue(width == 100);
        assertTrue(height == 100);

    }
}