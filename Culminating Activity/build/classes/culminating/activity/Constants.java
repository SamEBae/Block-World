package culminating.activity;
import java.awt.*;
public class Constants {
    //WORLD BASICS-------------------------------------------------
    public static final int WINDOW_LOCATIONX = 0;
    public static final int WINDOW_LOCATIONY = 0;
    public static final int REFRESH_TIME = 25;
    public static int WINDOW_WIDTH = 1152;
    public static int WINDOW_HEIGHT = 768;
    public static int WORLD_WIDTH = 3000;
    public static int WORLD_HEIGHT = 1000;
    public static final int BLOCK_WIDTH = 30;
    public static final int BLOCK_HEIGHT = 30;    
    public static String WORLD_NAME="";
    public static boolean sfx = true;
    public static boolean music = true;

    public static int getTaskBar() {
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = scrnSize.height - winSize.height;
        return taskBarHeight;
    }
    
    public static int charWidth(){//character width
        return Character.getChar(1).getWidth(null);
    }
    public static int charHeight(){//character height
        return Character.getChar(1).getHeight(null);
    }
    //BLOCK AND ITEM CONSTANTS--------------------------------------------------
    public static final int MINING_SPEED = 10;//frames per do damage
    public static final int HAND_DAMAGE = 2500000; //damage per MINING_SPEED frames
    public static final int MAX_JETPACK_SPEED = 10;
    public static final double ROTATION = Math.PI/3/MINING_SPEED; //radians per refresh
    
    
    //PHYSICS CONSTANTS-------------------------------------------------
    public static final double CHARACTER_SPEED = 5; //max speed, pixels per refresh_time
    public static final double HORI_ACCEL = 2;
    public static final double JUMP_POWER = 8;
    public static final double ACCEL_GRAVITY = 0.51;//pixels per refresh time squared
    public static final double TERMINAL_VELOCITY = 30;
    public static final double THRESHOLD = 20.0; //all velocities downward above this when falling will deal damage
    public static final double FALL_DAMAGE_MODIFIER = 0.125;
    public static final int CHAR_HP_RATIO = 1; //deals CHAR_HP_RATIO for every FALL_DAMAGE_MODIFIER above THRESHOLD
    public static final int MONSTER_CHASE_RANGE = 450;
        
    //CHARACTER CONSTANTS
    public static int CHAR_MAXHP = 100;
    public static final int PLACE_RANGE  = 120;
    public static final int CRAFTING_RANGE = 200; //range from crafting table/furnace
    public static final int DAMAGE_SPEED = 8;
    public static final int ATTACK_RANGE = 150;
    public static final int DAMAGE_INTERVAL = 20; //how often the character can get damaged
    
}
