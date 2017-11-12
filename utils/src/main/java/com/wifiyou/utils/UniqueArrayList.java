package com.wifiyou.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/26/17
 */

public class UniqueArrayList<E> extends ArrayList<E> {
    @Override
    public boolean add(E o) {
        if (contains(o)) {
            return false;
        } else {
            return super.add(o);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //TODO Need Unique
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        //TODO Need Unique
        return super.addAll(index, c);
    }

    @Override
    public E set(int index, E element) {
        //TODO Need Unique
        return super.set(index, element);
    }
}
