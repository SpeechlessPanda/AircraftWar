package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.BombEffectContext;
import edu.hitsz.prop.FreezeEffectContext;
import edu.hitsz.prop.PropObserver;
import edu.hitsz.strategy.NoShootStrategy;
import edu.hitsz.strategy.ShootStrategy;
import java.util.Collections;
import java.util.List;

/**
 * 所有种类飞机的抽象父类
 * 
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject implements PropObserver {

    protected static final int BOMB_SCORE_BONUS = 10;

    // 最大生命值
    protected int maxHp;
    protected int hp;
    protected int shootNum;
    protected int power;
    protected int direction;
    protected ShootStrategy shootStrategy;
    private boolean shootingDisabledByFreeze;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        this(locationX, locationY, speedX, speedY, hp, 0, 0, 0);
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power,
            int direction) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
        this.shootStrategy = new NoShootStrategy();
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public void increaseHp(int increase) {
        hp += increase;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    /**
     * 飞机射击方法
     * 
     * @return
     *         可射击对象需实现，返回子弹列表
     *         非可射击对象空实现，返回空列表
     */
    public List<BaseBullet> shoot() {
        if (shootingDisabledByFreeze) {
            return Collections.emptyList();
        }
        return shootStrategy.shoot(this);
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        if (shootStrategy == null) {
            throw new IllegalArgumentException("shootStrategy cannot be null");
        }
        this.shootStrategy = shootStrategy;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public int getDirection() {
        return direction;
    }

    public void setShootNum(int shootNum) {
        // 至少保留 1 发子弹，避免道具恢复或测试设置时出现非法值。
        this.shootNum = Math.max(1, shootNum);
    }

    public void multiplySpeed(double factor) {
        speedX = (int) Math.round(speedX * factor);
        speedY = Math.max(1, (int) Math.round(speedY * factor));
    }

    public void increaseMaxHp(double factor) {
        int newMaxHp = Math.max(maxHp, (int) Math.round(maxHp * factor));
        int increase = newMaxHp - maxHp;
        maxHp = newMaxHp;
        hp += increase;
    }

    protected void resetAircraftState(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum,
            int power, int direction, ShootStrategy shootStrategy) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;
        this.maxHp = hp;
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
        this.shootingDisabledByFreeze = false;
        this.isValid = true;
        setShootStrategy(shootStrategy);
    }

    @Override
    public void onBomb(BombEffectContext context) {
        if (!notValid()) {
            vanish();
            context.addScoreBonus(BOMB_SCORE_BONUS);
        }
    }

    @Override
    public void onFreeze(FreezeEffectContext context) {
        context.recordAffectedTarget();
        shootingDisabledByFreeze = true;
        setSpeed(0, 0);
    }

    protected void freezeMovementAndShootingTemporarily(FreezeEffectContext context, long durationMillis) {
        shootingDisabledByFreeze = true;
        context.freezeTemporarily(this, durationMillis, () -> shootingDisabledByFreeze = false);
    }

}
