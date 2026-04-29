package edu.hitsz.prop;

public interface PropObserver {

    void onBomb(BombEffectContext context);

    void onFreeze(FreezeEffectContext context);
}