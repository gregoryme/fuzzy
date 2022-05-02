package dev.kofe.ftlogic.container;

/*
 *  Temporal fuzzy logic API
 *  kofe.dev, 2022
 */

import dev.kofe.ftlogic.fuzzy.FuzzyBool;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * TemporalFuzzyBoolFactory is a "time machine" that can produce a FuzzyBool object
 * in state depends on time. The factory has a specific "time function" that defines rules to produce
 * FuzzyBool objects. Time function expects parameter of LocalDateTime which can be interpreted as definite time.
 */
public class TemporalFuzzyBoolFactory implements ContainerFuzzyBoolTime {

    @Getter
    @Setter
    private Function<LocalDateTime, FuzzyBool> timeFunction;

    /**
     * Constructor with the default time function
     */
    public TemporalFuzzyBoolFactory() {
        timeFunction = (time) -> new FuzzyBool();
    }

    public TemporalFuzzyBoolFactory(Function<LocalDateTime, FuzzyBool> timeFunction) {
        this.timeFunction = timeFunction;
    }

    /**
     * Method get(LocalDateTime time) produces FuzzyBool object according to the time function
     * @param time is definite timeMethod  produces FuzzyBool object according to the time function
     * @return FuzzyBool object in state according to the time function and definite time
     */ 
    @Override
    public FuzzyBool get(LocalDateTime time) {
        return timeFunction.apply(time);
    }
}
