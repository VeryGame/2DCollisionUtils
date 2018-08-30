package de.verygame.util;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Marco Deneke
 *         Created by Marco Deneke on 23.03.2016.
 */
public class ArrayUtilsTest {

    @Test
    public void testCreateAndFill() throws Exception {

        float[] array = ArrayUtils.createAndFill(5, 1);

        float[] other = {1,1,1,1,1};

        assertArrayEquals(other, array, 0.001f);
    }

    @Test
    public void testConstructor() throws Exception{
        Whitebox.invokeConstructor(ArrayUtils.class);
    }
}