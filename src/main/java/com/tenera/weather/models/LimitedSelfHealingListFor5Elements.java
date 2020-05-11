package com.tenera.weather.models;

import java.util.ArrayList;

public class LimitedSelfHealingListFor5Elements<T> extends ArrayList<T> {

    @Override
    public boolean add(T t) {
        boolean isAdded = super.add(t);
        if (super.size() > 5) {
            super.remove(0);
        }
        return isAdded;
    }
}
