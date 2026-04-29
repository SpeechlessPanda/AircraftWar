package edu.hitsz.prop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PropSubject {

    private final List<PropObserver> observers = new ArrayList<>();

    public void addObserver(PropObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void removeObserver(PropObserver observer) {
        observers.remove(observer);
    }

    protected List<PropObserver> observers() {
        return Collections.unmodifiableList(observers);
    }
}