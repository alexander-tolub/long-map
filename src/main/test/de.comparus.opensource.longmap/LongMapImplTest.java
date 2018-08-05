package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LongMapImplTest {

    @Test
    public void shouldAddValues() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertEquals(value, map.get(key));
    }

    @Test
    public void shouldAddValuesOnce() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);
        map.put(key, value);
        map.put(key, value);
        map.put(key, value);

        // Assert

        Assert.assertEquals(1, map.keys().length);
        Assert.assertEquals(1, map.values().size());
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void shouldRewriteValues() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value1 = new BigDecimal(12);
        BigDecimal value2 = new BigDecimal(13);
        map.put(key, value1);
        map.put(key, value2);

        // Assert
        Assert.assertEquals(value2, map.get(key));
    }

    @Test
    public void shouldReturnValues() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertEquals(value, map.values().get(0));
        Assert.assertEquals(1, map.values().size());
    }

    @Test
    public void shouldClear() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Act
        map.clear();

        // Assert
        Assert.assertEquals(0, map.values().size());
        Assert.assertEquals(0, map.keys().length);
        Assert.assertEquals(true, map.isEmpty());
        Assert.assertEquals(0, map.size());
        Assert.assertEquals(null, map.get(key));
    }

    @Test
    public void shouldReturnNull() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        long keyThatDoesNotExist = 2;
        Assert.assertEquals(null, map.get(keyThatDoesNotExist));
    }

    @Test
    public void shouldRemoveValues() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);
        map.remove(key);

        // Assert
        Assert.assertEquals(null, map.get(key));
        Assert.assertEquals(0, map.size());
        Assert.assertEquals(0, map.values().size());
    }

    @Test
    public void shouldBeEmpty() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Assert
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void shouldContainKey() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertTrue(map.containsKey(key));
    }

    @Test
    public void shouldContainValue() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertTrue(map.containsValue(value));
    }

    @Test
    public void shouldReturnKeys() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertTrue(map.keys().length == 1);
        Assert.assertTrue(map.keys()[0] == key);
    }

    @Test
    public void shouldRemoveCorrectValues() {
        // Arrange
        long key1 = 1;
        long key2 = 2;
        LongMap<BigDecimal> map = new LongMapImpl<>();
        BigDecimal value1 = new BigDecimal(12);
        BigDecimal value2 = new BigDecimal(12);
        map.put(key1, value1);
        map.put(key2, value2);

        // Act
        map.remove(1);

        // Assert
        Assert.assertEquals(null, map.get(key1));
        Assert.assertEquals(value2, map.get(key2));
        Assert.assertEquals(1, map.keys().length);
        Assert.assertEquals(1, map.values().size());
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void shouldAddSize() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);

        // Assert
        Assert.assertEquals(key, map.keys().length);
    }

    @Test
    public void shouldDecreaseSize() {
        // Arrange
        LongMap<BigDecimal> map = new LongMapImpl<>();

        // Act
        long key = 1;
        BigDecimal value = new BigDecimal(12);
        map.put(key, value);
        map.remove(key);

        // Assert
        Assert.assertEquals(0, map.keys().length);
    }

}
