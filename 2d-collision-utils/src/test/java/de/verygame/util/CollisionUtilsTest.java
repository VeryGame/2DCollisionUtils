package de.verygame.util;

import com.badlogic.gdx.math.Vector2;

import de.verygame.util.CollisionUtils;
import org.junit.Test;

import de.verygame.util.PolygonUtils;

import static org.junit.Assert.*;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 12.02.2016.
 */
public class CollisionUtilsTest {

    //triangle with height 5 and width 5
    Vector2[] firstPoly = {new Vector2(), new Vector2(0, 5), new Vector2(5, 5)};

    //square with height 5 and width 5
    Vector2[] secondPoly = {new Vector2(), new Vector2(5, 0), new Vector2(0, 5), new Vector2(5, 0), new Vector2(5, 5), new Vector2(0, 5)};

    @Test
    public void testCheckPolygonRectangleCollision() {

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(15, 10, 10, 10, 20, 6, secondPoly));

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(15, 10, 20, 10, 20, 6, scalePolygon(secondPoly, 2, 1)));

        assertFalse(CollisionUtils.checkRectanglePolygonCollision(5, 0, 6, 8, 12, 5, secondPoly));

        assertTrue(CollisionUtils.checkRectanglePolygonCollision(10, 10, 20, 20, 0, 0, scalePolygon(firstPoly, 2, 2)));
    }

    // Temporary until vector 2 is removed completely
    private Vector2[] scalePolygon(final Vector2[] polygon, final float xScale, final float yScale){
        Vector2[] copy = polygon.clone();

        for (Vector2 v : copy){
            v.scl(xScale, yScale);
        }

        return copy;
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

        Vector2[] incPoly = {new Vector2(42, 42), new Vector2(13, 37)};

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