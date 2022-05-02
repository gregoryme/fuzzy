package dev.kofe.ftlogic.fuzzy;

/*
 *  Temporal fuzzy logic API
 *  kofe.dev, 2022
 */

import lombok.*;
import java.text.DecimalFormat;
import java.util.function.Function;

public class FuzzyBool implements FuzzyLogicalSignedFloat {

    @Getter
    private float truth = 0;

    public FuzzyBool () {

    }

    public FuzzyBool (float truth) {
        setTruth(truth);
    }

    /**
     * Default trigger function set the hard rule: if argument is 1 then trigger returns true
     */
    @Getter
    private Function<Float, Boolean> triggerFunction = (value) -> (value == +1.0F) ? true : false;

    /**
     * Setting a custom trigger function
     * @param triggerFunction
     */
    public void setTriggerFunction(Function<Float, Boolean> triggerFunction) {
        this.triggerFunction = triggerFunction;
    }

    /**
     * Set current truth value
     * @param newTruthValue
     * @throws IllegalArgumentException
     */
    public void setTruth(Float newTruthValue) throws IllegalArgumentException {
        if (isValueValid(newTruthValue)) {
            this.truth = newTruthValue;
        } else {
            throw new IllegalArgumentException("Value must be between -1.0F and +1.0F");
        }
    }

    /**
     * Fuzzy logical operation NOT
     * @return
     */
    public Float fuzzyNot() {
        Float result = 0.0F;

        if (truth != 0.0F) {
            result = (truth > 0.0F) ? (-truth) : Math.abs(truth);
        }

        return result;
    }

    /**
     * Fuzzy logical operation AND
     * @param secondOperandValue is a value of second (right) variable in operation,
     *                           while first operand is an instance of class
     * @return float value as a result of fuzzy operation "AND"
     * @throws IllegalArgumentException
     */
    public Float fuzzyAnd(Float secondOperandValue) throws IllegalArgumentException {
        Float result;

        if (isValueValid(secondOperandValue)) {
            Float preResult = Math.abs(truth * secondOperandValue);
            preResult = roundFloat(preResult);
            result = (truth < 0 || secondOperandValue < 0) ? -preResult : preResult;
        } else {
            throw new IllegalArgumentException("Argument must be between -1.0F and +1.0F");
        }

        return result;
    }

    /**
     * Fuzzy logical operation OR
     * @param secondOperandValue is a value of second (right) variable in operation,
     *                           while first operand is an instance of class
     * @return float value as a result of fuzzy operation "OR"
     */
    public Float fuzzyOr(Float secondOperandValue) throws IllegalArgumentException {
        Float result;

        if (isValueValid(secondOperandValue)) {

            if (truth != 0 && secondOperandValue != 0) {
                result = Math.max(truth, secondOperandValue);
            } else {
                result = truth + secondOperandValue;
            }

        } else {
            throw new IllegalArgumentException("Argument must be between -1.0F and +1.0F");
        }

        return result;
    }

    /**
     * Method to formatting values
     * @param value
     * @return formatted value
     */
    private Float roundFloat (Float value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Float.valueOf(decimalFormat.format(value));
    }

    /**
     * The trigger is a "bridge" between fuzzy and traditional 2-values boolean logic
     * Trigger uses the trigger function for determining a dependence returned result on fuzzy value (an argument)
     * @param fuzzyValue is a current value of truth
     * @return true if an argument has value that can be interpreted as "true" according to the trigger function
     */
    public boolean trigger(Float fuzzyValue) {
       boolean result = false;

       if (isValueValid(fuzzyValue)) {
           result = triggerFunction.apply(fuzzyValue);
       }

       return result;
    }

    public boolean trigger() {
        boolean result = false;

        if (isValueValid(this.truth)) {
            result = triggerFunction.apply(this.truth);
        }

        return result;
    }

    @Override
    public boolean equals(Object object2) {

        if (object2 == null) {
            return false;
        }

        if (object2.getClass() != this.getClass()) {
            return false;
        }

        final FuzzyBool obj = (FuzzyBool) object2;
        if (this.getTruth() == obj.getTruth() && this.getTriggerFunction().equals(obj.getTriggerFunction())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int q1 = 105;
        final int q2 = 93;
        int hash = q1 + q2;
        hash = q2 * hash * (int)this.truth + (int) Math.abs(this.truth);
        hash = hash + this.triggerFunction.toString().length();
        return hash;
    }

}
