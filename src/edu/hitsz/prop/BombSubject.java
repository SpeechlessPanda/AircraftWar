package edu.hitsz.prop;

public class BombSubject extends PropSubject {

    public BombEffectContext trigger() {
        BombEffectContext context = new BombEffectContext();
        for (PropObserver observer : observers()) {
            observer.onBomb(context);
        }
        return context;
    }
}