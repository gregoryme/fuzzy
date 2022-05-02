package dev.kofe.ftlogic.container;

/*
 *  Temporal fuzzy logic API
 *  kofe.dev, 2022
 */

/**
 * Interface for object container
 * @param <O> is a type of "wrapped" objects
 * @param <T> is a parameter (dependency)
 */
public interface Container <O, T> {

    /**
     * Method-factory
     * @param parameter
     * @return object of type O in the state determined by the parameter
     */
    O get(T parameter);

}
