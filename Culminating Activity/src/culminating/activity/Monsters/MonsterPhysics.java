package culminating.activity.Monsters;

import culminating.activity.*;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MonsterPhysics extends Monsters { 
    //physics of the monsters
    public static final Map<Integer,Vector> queue = new HashMap<Integer,Vector>();
    public static void move() {
        for (int i = 0; i < Monsters.getTotalNum(); i++) {
            Point p = new Point(getLocation(i).x + MonsterStats.get(getCode(i)).width / 2 - 200, getLocation(i).y + MonsterStats.get(getCode(i)).height - 200);
            Point position = getLocation(i);
            Rectangle mob = new Rectangle(position.x, position.y, MonsterStats.get(getCode(i)).width, MonsterStats.get(getCode(i)).height);
            int col = Draw.round(false, true, p.x) / Constants.BLOCK_WIDTH;
            int row = Draw.round(false, false, p.y) / Constants.BLOCK_HEIGHT;
            int cMax = Draw.round(true, true, p.x + Constants.WINDOW_WIDTH) / Constants.BLOCK_WIDTH;
            int rMax = Draw.round(true, false, p.y + Constants.WINDOW_HEIGHT) / Constants.BLOCK_HEIGHT;
            Vector v = monsters.get(i).getVector();
            boolean[] temp = Physics.checkEdges(row, col, rMax, cMax, mob);
            boolean below = temp[0];
            boolean left = temp[1];
            boolean right = temp[2];
            boolean above = temp[3];
            double vy = v.yComponent();
            double vx = v.xComponent();
            double angle = v.getAngle();
            monsterAI(i, temp);
            int code = OnMob(new Rectangle(culminating.activity.Character.charX(),culminating.activity.Character.charY(), Constants.charWidth(), Constants.charHeight()));
            if(code >=0 &&culminating.activity.Character.damageInterval==0){   
                if(direction(code)==1){
                    Vector a = new Vector(MonsterStats.get(Monsters.getCode(code)).kb,Math.PI);
                    a.add(new Vector(4,Math.toRadians(135)));
                    Physics.queue.add(a);
                } else {
                    Vector a = new Vector(MonsterStats.get(Monsters.getCode(code)).kb,0);
                    a.add(new Vector(4,Math.toRadians(45)));  
                    Physics.queue.add(a);
                }
                InfoWindow.addText("A " + MonsterStats.get(Monsters.getCode(i)).name +" has damaged you " +  MonsterStats.get(Monsters.getCode(i)).dmg + " hp.");
                culminating.activity.Character.hp -= MonsterStats.get(Monsters.getCode(i)).dmg;
                culminating.activity.Character.damageInterval = Constants.DAMAGE_INTERVAL;
            }            
            
            if (below == true || above == true) {
                v.setMag(Math.max(-MonsterStats.get(Monsters.getCode(i)).speed, Math.min(MonsterStats.get(Monsters.getCode(i)).speed, Math.abs(vx))));
                if (angle > Math.PI / 2 && angle < Math.toRadians(270)) {
                    v.setAngle(Math.PI);
                } else {
                    v.setAngle(0);
                }
            }
            if (left == true || right == true) {
                v.setMag(Math.abs(vy));
                if (angle < Math.PI) {
                    v.setAngle(Math.PI / 2);
                } else {
                    v.setAngle(Math.toRadians(270));
                }
            }
            if (below == true && left == true || below == true && right == true || above == true && left == true || above == true && right == true) {
                v.reset();
            }
            if (monsters.get(i).getKeys()[2] == true && right == false) { //right
                v.add(new Vector(Physics.add(v.xComponent(), Constants.HORI_ACCEL, MonsterStats.get(Monsters.getCode(i)).speed, false), 0));
                monsters.get(i).setDirection(1);
            }
            if (monsters.get(i).getKeys()[0] == true && below == true && above == false) { //up
                v.add(new Vector(MonsterStats.get(Monsters.getCode(i)).jump, Math.toRadians(90)));
            }
            if (monsters.get(i).getKeys()[1] == true && left == false) { //left            
                v.add(new Vector(Physics.add(v.xComponent(), -Constants.HORI_ACCEL, -MonsterStats.get(Monsters.getCode(i)).speed, true), Math.PI));
                monsters.get(i).setDirection(0);
            }
            if (below == false) { //gravity
                v.add(new Vector(Physics.add(v.yComponent(), -Constants.ACCEL_GRAVITY, -Constants.TERMINAL_VELOCITY, true), Math.toRadians(270)));
            }
            if (below == true && monsters.get(i).getKeys()[1] == false && monsters.get(i).getKeys()[2] == false) {
                v.reset();
            }            
            if(queue.containsKey(i)){
                v.add(queue.get(i));
                queue.remove(i);
            }            
            mob.translate((int) Math.round(v.xComponent()), -(int) Math.round(v.yComponent()));
            if (Physics.collision(row, col, rMax, cMax, mob) == true) {
                mob.setBounds(Physics.moveCollision(mob, position, row, col, rMax, cMax));
                move(mob, i);
            } else {
                move(mob, i);
            }
        }
    }

    private static void move(Rectangle mob, int i) {
        monsters.get(i).setLocation(new Point(mob.x, mob.y));
    }

    private static void monsterAI(int i, boolean[] edges) {
        if (distanceCheck(i) < Constants.MONSTER_CHASE_RANGE) { // within proximity of player
            monsters.get(i).clearKeys();
            monsters.get(i).setKeys(direction(i), true);
        } else { //far from player, moving randomly
            if (monsters.get(i).getDuration() > 0) {
                monsters.get(i).setDuration(monsters.get(i).getDuration() - 1);
            } else {
                monsters.get(i).clearKeys();
                int j = new Random().nextInt(2);
                int duration = new Random().nextInt(Constants.REFRESH_TIME * 100 - Constants.REFRESH_TIME * 50 + 1) + Constants.REFRESH_TIME * 50;
                monsters.get(i).setDuration(duration);
                if (j == 0) {
                    monsters.get(i).setKeys(1, true);
                } else {
                    monsters.get(i).setKeys(2, true);
                }
            }
        }
        if (monsters.get(i).getKeys()[1] == true && edges[1] == true || monsters.get(i).getKeys()[2] == true && edges[2]) {
            monsters.get(i).setKeys(0, true);
        } else {
            monsters.get(i).setKeys(0, false);
        }
    }

    private static int distanceCheck(int i) {
        Point m = new Point(Monsters.getLocation(i).x + MonsterStats.get(Monsters.getCode(i)).width / 2,
                Monsters.getLocation(i).y + MonsterStats.get(Monsters.getCode(i)).height / 2);
        return (int) m.distance(new Point(culminating.activity.Character.charX() + Constants.charWidth() / 2,
                culminating.activity.Character.charY() + Constants.charWidth() / 2));
    }
    
    private static int direction(int i){
        if(Monsters.getLocation(i).x + MonsterStats.get(Monsters.getCode(i)).width / 2 > culminating.activity.Character.charX() + Constants.charWidth() / 2){
            return 1;
        } else {
            return 2;
        }
    }
    
    public static int onMob(Point mouse){
        for(int i = 0; i<Monsters.getTotalNum();i++){
            if(new Rectangle(Monsters.getLocation(i), new Dimension(MonsterStats.get(Monsters.getCode(i)).width,MonsterStats.get(Monsters.getCode(i)).height)).contains(mouse)){
                return i;
            }     
        }        
        return -1;
    }
    
    public static int OnMob(Rectangle r){
        for(int i = 0; i<Monsters.getTotalNum();i++){
            if(new Rectangle(Monsters.getLocation(i), new Dimension(MonsterStats.get(Monsters.getCode(i)).width,MonsterStats.get(Monsters.getCode(i)).height)).intersects(r)){
                return i;
            }     
        }        
        return -1;
    }
}
