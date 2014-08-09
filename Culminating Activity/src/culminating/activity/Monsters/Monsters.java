package culminating.activity.Monsters;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import culminating.activity.Vector;

public class Monsters {
    protected static ArrayList <Monster> monsters = new ArrayList<Monster>();
    
    public static Point getLocation(int i){//location of monster
        return monsters.get(i).getLocation();        
    }    
    
    public static int getCode(int i){//return code of the monster
        return monsters.get(i).getCode();
    }
    
    public static void damage(int i, int hp){//damage to monster
        monsters.get(i).damage(hp);
        if(monsters.get(i).getHp()<=0){
            monsters.remove(i);
        }        
    }
    
    public static void addMonster(int i, int x, int y){//add monster to game
        monsters.add(new Monster(i,x,y));
    }
    
    public static int getTotalNum(){
        return monsters.size();
    }
    
    public static int getNumMob(int i){
        int temp=0;
        for(int j = 0; j<getTotalNum();j++){
            if(monsters.get(j).getCode()==i){
                temp++;
            }
        }
        return temp;        
    }
    
    public static Image getImg(int i){
        return monsters.get(i).getImage();        
    }
}
