package edu.hitsz.prop;

public class FreezeSubject extends PropSubject {

    private final FreezeEffectContext context;

    public FreezeSubject(FreezeEffectContext context) {
        this.context = context;
    }

    public FreezeEffectContext trigger() {
        for (PropObserver observer : observers()) {
            observer.onFreeze(context);
        }
        return context;
    }
}