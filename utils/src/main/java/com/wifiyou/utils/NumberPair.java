package com.wifiyou.utils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/22/17
 */

public class NumberPair<F extends Number, S extends Number> {
    public F first;
    public S second;

    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public NumberPair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public boolean isBetween(Number number) {
        if (number != null) {
            if (number.doubleValue() >= first.doubleValue() && number.doubleValue() <= second.doubleValue()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return "Pair{" + String.valueOf(first) + " " + String.valueOf(second) + "}";
    }
}
