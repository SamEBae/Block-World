package culminating.activity;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
public class Character {
    private static Point camera = new Point(); 
    private static Point spawn = new Point();
    private static int direction = 1;
    private static Vector velocity = new Vector(0,0);
    static int lastx,lasty;
    static boolean place,mine,consume;
    static int miningCountdown = Constants.MINING_SPEED;
    static int damageCountdown = 0;
    public static int damageInterval = 0;
    public static int hp = Constants.CHAR_MAXHP + Equipment.hpBonus();
    private static double rotation=0;
    private static Image character1; 
    private static Image character2;  
    private static Image quickSlot;  
    private static Image death;
    private static Image pauseI;
    private static Image pauseC;
    private static Image pauseE;
    
    public static void setCam(Point i){//camera point
        camera = i;   
    }
    public static Point getCam(){//getting camera point
        return camera;    
    }
    public static void setDirection(int i){//setting direction
        direction = i;
    }
    public static int getDirection(){//getting direction
        return direction;
    }
    public static Vector getVector(){//getting vector
        return velocity;
    }
    public static int charX(){//character X cordinate
        return camera.x+(Constants.WINDOW_WIDTH / 2) - Constants.charWidth() / 2;
    }
    public static int charY(){//character Y cordinate
        return camera.y + Constants.WINDOW_HEIGHT/2 - Constants.charHeight()/2 - 50;
    }    
    public static void loadChar(){//loading character
        try{
        character1 = ImageIO.read(Block.class.getResource("Resources/Character Left.png"));         
        character2 = ImageIO.read(Block.class.getResource("Resources/Character Right.png")); 
        quickSlot = ImageIO.read(Block.class.getResource("Resources/Slot.png")); 
        death = ImageIO.read(Block.class.getResource("Resources/Death.png")).getScaledInstance(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT, Image.SCALE_FAST);
        pauseI = ImageIO.read(Block.class.getResource("Resources/PauseInventory.png")).getScaledInstance(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT, Image.SCALE_FAST);
        pauseC = ImageIO.read(Block.class.getResource("Resources/PauseCrafting.png")).getScaledInstance(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT, Image.SCALE_FAST);
        pauseE = ImageIO.read(Block.class.getResource("Resources/PauseEquipment.png")).getScaledInstance(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT, Image.SCALE_FAST);
        } catch (IOException e){
            System.err.println(e);
        }
        
    }
    public static void setSpawn(int x, int y){
        spawn.x=x;
        spawn.y=y;
    }
    
    public static Point getSpawn(){
        return spawn;
    }
    
    public static Image getChar(int i){ //getting character state
         if(i==0){
             return character1;
         } else if (i==1){
             return character2;
         } else if (i==2){
             return quickSlot;
         } else if (i==3){
             return death;
         } else if (i==4){
             return pauseI;
         } else if (i==5){
             return pauseC;
         } else if (i==6){
             return pauseE;
         }
         else{
             return null;
         }
    }
    public static void respawn(){ //reviving
        hp = Constants.CHAR_MAXHP + Equipment.hpBonus();
        camera.x = spawn.x;
        camera.y = spawn.y;
    }
    
    public static void setRotation(double i){//rotating
        rotation = i;
    }
    public static double getRotation(){
        return rotation;
    }
}
