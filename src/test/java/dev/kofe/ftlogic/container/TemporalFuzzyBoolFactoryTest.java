package dev.kofe.ftlogic.container;

/*
 *  Temporal fuzzy logic API
 *  kofe.dev, 2022
 */

import dev.kofe.ftlogic.fuzzy.FuzzyBool;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TemporalFuzzyBoolFactoryTest {

    private TemporalFuzzyBoolFactory temporalFuzzyBoolFactory;
    private final LocalDateTime SPECIFIC_TIME = LocalDateTime.now();

    @Test
    void shouldBeEquals() {
        temporalFuzzyBoolFactory = new TemporalFuzzyBoolFactory();
        FuzzyBool fuzzyBool1 = temporalFuzzyBoolFactory.get(SPECIFIC_TIME);
        FuzzyBool fuzzyBool2 = temporalFuzzyBoolFactory.get(SPECIFIC_TIME);
        assertEquals(fuzzyBool1, fuzzyBool2);
    }

    @Test
    void shouldBeNotEquals () {

        temporalFuzzyBoolFactory = new TemporalFuzzyBoolFactory( (time) -> {
            if (time.isAfter(SPECIFIC_TIME)) {
                return new FuzzyBool(0.3F);
            } else {
                return new FuzzyBool(-0.18F);
            }
        });

        FuzzyBool fuzzyBool1 = temporalFuzzyBoolFactory.get(SPECIFIC_TIME);
        FuzzyBool fuzzyBool2 = temporalFuzzyBoolFactory.get(SPECIFIC_TIME.plusHours(2));
        assertNotEquals(fuzzyBool1, fuzzyBool2);

    }

    @Test
    void iAmGoingToMeetTom () {

        // object's name is a "predicate"
        TemporalFuzzyBoolFactory sunHasSet = new TemporalFuzzyBoolFactory( (time -> {

            float truth = 0;
            int hour = time.getHour();

            if (isBetweenOpen(0,  hour, 5))  truth = +1.0F; // the truth that sun has set is +1 i.e. exactly true
            if (isBetweenOpen(5,  hour, 6))  truth = +0.5F; // the truth that sun has set is +0.5
            if (isBetweenOpen(6,  hour, 19)) truth = -1.0F; // the truth that sun has set is -1 i.e. exactly false
            if (isBetweenOpen(19, hour, 20)) truth = -0.5F; // the truth that sun has set is -0.5
            if (isBetweenOpen(20, hour, 21)) truth = +0.4F; // the truth that sun has set is +0.4
            if (isBetweenOpen(21, hour, 22)) truth = +0.6F; // the truth that sun has set is +0.6
            if (isBetweenOpen(22, hour, 23)) truth = +0.9F; // the truth that sun has set is +0.9
            if (isBetweenOpen(23, hour, 0))  truth = +1.0F; // the truth that sun has set is +1 i.e. exactly true

            FuzzyBool object = new FuzzyBool(truth);
            object.setTriggerFunction( (value) -> value >= 0.6F );

            return object;
        }));

        // object's name is a "predicate"
        TemporalFuzzyBoolFactory tomIsGoingHome = new TemporalFuzzyBoolFactory( (time) -> {

            float truth = 0;
            int hour = time.getHour();

            if (isBetweenOpen(0,  hour, 18)) truth = -1.0F; // the truth that Tom is going home is -1 i.e. exactly false
            if (isBetweenOpen(18, hour, 19)) truth = +0.5F; // the truth that Tom is going home is +0.5
            if (isBetweenOpen(19, hour, 20)) truth = +0.6F; // the truth that Tom is going home is +0.6
            if (isBetweenOpen(20, hour, 21)) truth = +0.7F; // the truth that Tom is going home is +0.7
            if (isBetweenOpen(21, hour, 22)) truth = +0.9F; // the truth that Tom is going home is +0.9
            if (isBetweenOpen(22, hour, 23)) truth = +0.8F; // the truth that Tom is going home is +0.8
            if (isBetweenOpen(23, hour, 0))  truth = +0.4F; // the truth that Tom is going home is +0.4

            FuzzyBool object = new FuzzyBool(truth);
            object.setTriggerFunction( (value) -> value >= 0.7F );

            return object;

        } );

        LocalDateTime testTime;

        testTime = LocalDateTime.of(2022,1,1,21,30);
        assertTrue( sunHasSet.get(testTime).trigger() && tomIsGoingHome.get(testTime).trigger() );
        assertTrue( sunHasSet.get(testTime).fuzzyAnd(tomIsGoingHome.get(testTime).getTruth()) >= 0.5F );

        testTime = LocalDateTime.of(2022,1,1,15,00);
        assertFalse( sunHasSet.get(testTime).trigger() && tomIsGoingHome.get(testTime).trigger() );
        assertFalse( sunHasSet.get(testTime).fuzzyAnd(tomIsGoingHome.get(testTime).getTruth()) >= 0.5F );

    }

    /**
     * is a ∈ [a, b]
     */
    private boolean isBetween(int a, int x, int b) {
        return a <= x && x <= b;
    }

    /**
     * is a ∈ [a, b)
     */
    private boolean isBetweenOpen(int a, int x, int b) {
        return a <= x && x < b;
    }

}