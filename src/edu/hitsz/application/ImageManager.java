package edu.hitsz.application;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EliteProEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.BloodSupply;
import edu.hitsz.prop.BombSupply;
import edu.hitsz.prop.FirePlusSupply;
import edu.hitsz.prop.FireSupply;
import edu.hitsz.prop.FreezeSupply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 * 
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE_2;
    public static BufferedImage BACKGROUND_IMAGE_3;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITE_PLUS_ENEMY_IMAGE;
    public static BufferedImage ELITE_PRO_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage BLOOD_SUPPLY_IMAGE;
    public static BufferedImage FIRE_SUPPLY_IMAGE;
    public static BufferedImage FIRE_PLUS_SUPPLY_IMAGE;
    public static BufferedImage BOMB_SUPPLY_IMAGE;
    public static BufferedImage FREEZE_SUPPLY_IMAGE;

    static {
        try {

            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE_2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE_3 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITE_PLUS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            ELITE_PRO_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePro.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            BLOOD_SUPPLY_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            FIRE_SUPPLY_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            FIRE_PLUS_SUPPLY_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            BOMB_SUPPLY_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            FREEZE_SUPPLY_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_freeze.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(), ELITE_PLUS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteProEnemy.class.getName(), ELITE_PRO_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BloodSupply.class.getName(), BLOOD_SUPPLY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FireSupply.class.getName(), FIRE_SUPPLY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FirePlusSupply.class.getName(), FIRE_PLUS_SUPPLY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombSupply.class.getName(), BOMB_SUPPLY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FreezeSupply.class.getName(), FREEZE_SUPPLY_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className) {
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj) {
        if (obj == null) {
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static BufferedImage getBackgroundImageForDifficulty(String difficulty) {
        if (difficulty == null) {
            return BACKGROUND_IMAGE_2;
        }
        // 难度与背景图保持一一对应，便于界面层只传入难度字符串。
        switch (difficulty.trim().toLowerCase()) {
            case "easy":
                return BACKGROUND_IMAGE;
            case "hard":
                return BACKGROUND_IMAGE_3;
            case "normal":
            default:
                return BACKGROUND_IMAGE_2;
        }
    }

}
