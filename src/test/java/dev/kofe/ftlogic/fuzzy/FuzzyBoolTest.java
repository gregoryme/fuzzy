package dev.kofe.ftlogic.fuzzy;

/*
 *  Temporal fuzzy logic API
 *  kofe.dev, 2022
 */

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class FuzzyBoolTest {

    private FuzzyBool testingVar = new FuzzyBool();

    private float getTruthCurrentValue (FuzzyBool fb) {
        float currentTruthValue = 0.0F;
        try {

            Field fi = fb.getClass().getDeclaredField("truth");
            fi.setAccessible(true);
            currentTruthValue = fi.getFloat(fb);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return currentTruthValue;
    }

    @Test
    void truthValueShouldBeSet() {

        testingVar.setTruth(0.0F);
        float currentTruthValue = getTruthCurrentValue(testingVar);
        assertEquals(0.0F,  currentTruthValue);

        testingVar.setTruth(-1.0F);
        currentTruthValue = getTruthCurrentValue(testingVar);
        assertEquals(-1.0F,  currentTruthValue);

        testingVar.setTruth(-0.89F);
        currentTruthValue = getTruthCurrentValue(testingVar);
        assertEquals(-0.89F,  currentTruthValue);

    }

    @Test
    void shouldThrowIllegalArgumentException () {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
           testingVar.setTruth(-1.1F);
        });

        exception = assertThrows(IllegalArgumentException.class, () -> {
            testingVar.setTruth(+2.144F);
        });

        exception = assertThrows(IllegalArgumentException.class, () -> {
            testingVar.setTruth(+1.0001F);
        });

    }

    @Test
    void resultValueShouldBeSymmetric() {

        testingVar.setTruth(-1.0F);
        assertEquals(+1.0F, testingVar.fuzzyNot());

        testingVar.setTruth(+1.0F);
        assertEquals(-1.0F, testingVar.fuzzyNot());

        testingVar.setTruth(+0.8F);
        assertEquals(-0.8F, testingVar.fuzzyNot());

    }

    // NOT operation

    @Test
    void resultShouldBeZero() {

        testingVar.setTruth(+1.0F);
        assertEquals(0.0F, testingVar.fuzzyAnd(0.0F));

        testingVar.setTruth(0.0F);
        assertEquals(0.0F, testingVar.fuzzyAnd(0.5F));

    }

    @Test
    void resultShouldBeNegative1() {

        testingVar.setTruth(+1.0F);
        assertEquals(-1.0F, testingVar.fuzzyAnd(-1.0F));

        testingVar.setTruth(-1.0F);
        assertEquals(-1.0F, testingVar.fuzzyAnd(-1.0F));

        testingVar.setTruth(+1.0F);
        assertEquals(-1.0F, testingVar.fuzzyAnd(-1.0F));

        testingVar.setTruth(-1.0F);
        assertEquals(-1.0F, testingVar.fuzzyAnd(+1.0F));

    }

    @Test
    void resultShouldBeDefiniteNumber() {

        // AND operation

        testingVar.setTruth(+0.9F);
        assertEquals(+0.36F, testingVar.fuzzyAnd(+0.4F));

        testingVar.setTruth(-0.9F);
        assertEquals(-0.36F, testingVar.fuzzyAnd(+0.4F));

        testingVar.setTruth(+0.99F);
        assertEquals(+0.47F, testingVar.fuzzyAnd(+0.47F));

        testingVar.setTruth(-0.91F);
        assertEquals(-0.46F, testingVar.fuzzyAnd(-0.5F));

        // OR operation

        testingVar.setTruth(+0.4F);
        assertEquals(+0.4F, testingVar.fuzzyOr(-0.4F));

        testingVar.setTruth(+0.84F);
        assertEquals(+0.84F, testingVar.fuzzyOr(+0.76F));

        testingVar.setTruth(-1F);
        assertEquals(-1.0F, testingVar.fuzzyOr(0.0F));

    }

    // trigger

    @Test
    void defaultTriggerShouldReturnTrueIfValueIsPositiveOne() {

        assertTrue(() -> testingVar.trigger(1.0F));

    }

    // equal

    @Test
    void shouldBeFalseIfNoEquals () {

        FuzzyBool fuzzyBool1 = new FuzzyBool(0.2F);
        FuzzyBool fuzzyBool2 = new FuzzyBool();

        assertFalse(fuzzyBool1.equals(fuzzyBool2));

        fuzzyBool1.setTruth(-0.35F);
        fuzzyBool2.setTruth(-0.35F);
        fuzzyBool1.setTriggerFunction((fuzzyTruth) -> fuzzyTruth > 0);
        fuzzyBool2.setTriggerFunction((fuzzyTruth) -> fuzzyTruth <= 1);

        assertFalse(fuzzyBool1.equals(fuzzyBool2));

        fuzzyBool1.setTruth(-0.35F);
        fuzzyBool2.setTruth(+0.12F);
        fuzzyBool1.setTriggerFunction((fuzzyTruth) -> fuzzyTruth > 0);
        fuzzyBool2.setTriggerFunction((fuzzyTruth) -> fuzzyTruth > 0);

        assertFalse(fuzzyBool1.equals(fuzzyBool2));

    }

    @Test
    void shouldBeTrueIfEquals () {

        FuzzyBool fuzzyBool1 = new FuzzyBool(0.2F);
        FuzzyBool fuzzyBool2 = new FuzzyBool(0.2F);

        assertTrue(fuzzyBool1.equals(fuzzyBool2));

    }

}