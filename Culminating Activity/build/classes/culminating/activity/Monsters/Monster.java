package culminating.activity.Monsters;

import culminating.activity.*;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;

public class Monster {
    private int code=0;
    private int hp =0;
    private Point coords = new Point();
    private int facing = 0;//left
    private Vector velocity = new Vector(0,0);
    private boolean [] keys = new boolean[3];//0-up, 1-left, 2-right
    private int duration = 0;
    
    public Monster(int code, int x, int y){
        this.code = code;
        this.hp=MonsterStats.get(code).hp;        
        coords.x=x;
        coords.y=y;
    }
    public Point getLocation(){//coordinate of monster
        return coords;
    }    
    public void setLocation(Point p){//set location of monster
        coords = p;
    }
    
    public int getCode(){
        return code;
    }
    public void damage(int hp){
        this.hp-=hp;
            if (this.hp > 0) {
                InfoWindow.addText("This " + MonsterStats.get(code).name + " has " + this.hp + " hp remaining.");
            } else if (this.hp <= 0) {
                InfoWindow.addText("This " + MonsterStats.get(code).name + " has 0 hp remaining.");
                destroy();
            }
    }
    private void destroy(){//monster killed
        int [] drops = MonsterStats.get(code).drops;
        Random r = new Random();
        for(int i = 0; i<drops.length;i+=2){
            if(r.nextInt(100)<drops[i]){
                Inventory.add(drops[i+1], 1);
                InfoWindow.addText("You have gained a " + Stats.get(drops[i+1]).name + ".");
            }            
        }
        this.code = 0;
    }
    
    public int getHp(){//monster hp
        return hp;
    }
    
    public Image getImage(){
        if(facing == 0){
            return MonsterStats.get(code).getImg(code);            
        } else{
            return MonsterStats.get(code).getImg2(code);
        }        
    }
    public Vector getVector(){//monster vecctor
        return velocity;
    }
    public boolean [] getKeys(){//monster key/direcction
        return keys;
    } 
    public void setKeys(int key, boolean b){
        keys[key] = b;
    }
    public void setDirection(int i){//seting direction
        facing = i;
    }
    public void setDuration(int i){//setting duration
        duration = i;
    }
    public int getDuration(){//geting duration
        return duration;
    }
    public void clearKeys(){//clearing keys
        for(int i = 0; i<keys.length;i++){
            keys[i]=false;
        }
    }
}
