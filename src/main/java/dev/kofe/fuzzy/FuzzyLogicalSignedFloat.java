package dev.kofe.fuzzy;

/*
 * The fuzzy logic interface with float type of values that should be in [-1..1]
 * kofe.dev, 2022
 */
public interface FuzzyLogicalSignedFloat extends FuzzyLogical<Float> {

    /**
     * The default method to check validity of arguments.
     * Method supports the concept of the interface: a value should be in between -1 and +1, i.e. [-1, +1]
     * @param value is an argument
     * @return true when argument is in [-1, +1]
     */
    default boolean isValueValid (Float value) {
        boolean decision = false;
        if (value >= -1.0F && value <= +1.0F) {
            decision = true;
        }

        return decision;
    }

}
