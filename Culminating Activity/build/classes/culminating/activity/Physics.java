package culminating.activity;

import java.awt.*;
import java.util.ArrayList;
//physics of the game
public class Physics extends Keys{
    private static boolean inTheAir;
    public static ArrayList<Vector>queue = new ArrayList<Vector>();
    public static void physics() {
        Point p = Character.getCam();
        Rectangle character = new Rectangle(Character.charX(), Character.charY(), Constants.charWidth(), Constants.charHeight());
        int col = Draw.round(false, true, p.x) / Constants.BLOCK_WIDTH;
        int row = Draw.round(false, false, p.y) / Constants.BLOCK_HEIGHT;
        int cMax = Draw.round(true, true, p.x + Constants.WINDOW_WIDTH) / Constants.BLOCK_WIDTH;
        int rMax = Draw.round(true, false, p.y + Constants.WINDOW_HEIGHT) / Constants.BLOCK_HEIGHT;
        Vector v = Character.getVector();
        Point position = new Point(character.x, character.y);
        boolean [] temp = checkEdges(row,col,rMax,cMax,character);
        boolean below = temp[0];
        boolean left = temp[1];
        boolean right = temp[2];
        boolean above = temp[3];
        //directions
        if (below == true) {
            if (inTheAir == true) {
                inTheAir = false;
                double difference = Math.abs(v.yComponent()) - Constants.THRESHOLD;
                if(difference >Constants.FALL_DAMAGE_MODIFIER && Equipment.fallDamage()==true){
                    Character.hp -= Constants.CHAR_HP_RATIO * (int)(difference / Constants.FALL_DAMAGE_MODIFIER);
                    InfoWindow.addText("You have lost " + Constants.CHAR_HP_RATIO * (int)(difference / Constants.FALL_DAMAGE_MODIFIER) +" hp due to falling.");
                }                
            }
        }
        double vy = v.yComponent();
        double vx = v.xComponent();
        double angle = v.getAngle();
        //angle for the drawing
        if(below == true || above == true){   
            v.setMag(Math.max(-Constants.CHARACTER_SPEED + Equipment.getSpeedBonus(), Math.min(Constants.CHARACTER_SPEED + Equipment.getSpeedBonus(), Math.abs(vx))));
            if(angle >Math.PI/2 && angle <Math.toRadians(270)){
                v.setAngle(Math.PI);
            } else {
                v.setAngle(0);
            }
        } 
        if(left == true||right == true){
            v.setMag(Math.abs(vy));
            if(angle<Math.PI){
                v.setAngle(Math.PI/2);
            } else {
                v.setAngle(Math.toRadians(270));
            }                   
        }
        if(below == true && left == true || below == true && right == true || above == true && left == true || above == true && right == true){
            v.reset();
        }        
        
        if (keys[68] == true && right == false) { //right
            v.add(new Vector(add(v.xComponent(), Constants.HORI_ACCEL, Constants.CHARACTER_SPEED + Equipment.getSpeedBonus(),false), 0));
            Character.setDirection(1);
        }
        if (keys[87] == true && below == true && inTheAir == false && above == false) { //up
            v.add(new Vector(Constants.JUMP_POWER, Math.toRadians(90)));
            inTheAir = true;
        } else if(keys[87] == true && above == false && Equipment.jetpack()==true){
             v.add(new Vector(add(v.yComponent(),Constants.JUMP_POWER + Equipment.getJumpBonus(), Constants.MAX_JETPACK_SPEED,false), Math.toRadians(90)));
             inTheAir = true;
        }
        if (keys[65] == true && left == false) { //left            
            v.add(new Vector(add(v.xComponent(), -Constants.HORI_ACCEL, -(Constants.CHARACTER_SPEED + Equipment.getSpeedBonus()),true), Math.PI));
            Character.setDirection(0);
        }
        if (below == false) { //gravity
            v.add(new Vector(add(v.yComponent(), -Constants.ACCEL_GRAVITY, -Constants.TERMINAL_VELOCITY,true), Math.toRadians(270)));
            inTheAir = true;
        }
        if(inTheAir == false && below == true && keys[68] == false && keys[65] == false){
            v.reset();
        }
        for(int j = 0;j<queue.size();j++){
                v.add(queue.get(j));
                queue.remove(j);
        }
        character.translate((int) Math.round(v.xComponent()), -(int) Math.round(v.yComponent()));
        if (collision(row,col,rMax,cMax,character) == true) {
            character.setBounds(moveCollision(character,position,row,col,rMax,cMax));
            move(character);
        } else {
            move(character);
        }
    }

    public static boolean[] checkEdges(int row, int col, int rMax, int cMax,Rectangle character) { //block that is directly touching you
        boolean [] edges = new boolean [4]; //0 is below, 1 is left, 2 is right, 3 is above
        loop:
        for (int r = row; r < rMax; r++) {
            for (int c = col; c < cMax; c++) {
                if (r >= 0 && c >= 0 && r < Constants.WORLD_HEIGHT && c < Constants.WORLD_WIDTH && World.getBlockType(r, c) != 0 && Stats.get(World.getBlockType(r,c)).collidable== true) {
                    Rectangle R = new Rectangle(c * Constants.BLOCK_HEIGHT, r * Constants.BLOCK_WIDTH, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                    R.translate(0, -1);
                    if (character.intersects(R) == true) {
                        edges[0] = true;
                    }
                    R.translate(1,1);
                    if (character.intersects(R) == true) {
                        edges[1] = true;
                    }
                    R.translate(-2,0);
                    if (character.intersects(R) == true) {
                        edges[2] = true;
                    }
                    R.translate(1,1);
                    if (character.intersects(R) == true) {
                        edges[3] = true;
                    }
                }
            }
        }
        return edges;
    }
    //collision dtect with blocks
    public static boolean collision(int row, int col, int rMax, int cMax,Rectangle character) {
        boolean collide = false;
        loop:
        for (int r = row; r < rMax; r++) {
            for (int c = col; c < cMax; c++) {
                if (r >= 0 && c >= 0 && r < Constants.WORLD_HEIGHT && c < Constants.WORLD_WIDTH && World.getBlockType(r, c) != 0 && Stats.get(World.getBlockType(r,c)).collidable== true) {
                    Rectangle R = new Rectangle(c * Constants.BLOCK_HEIGHT, r * Constants.BLOCK_WIDTH, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                    if (character.intersects(R) == true) {
                        collide = true;
                        break loop;
                    }
                }
            }
        }
        return collide;
    }

    public static double add(double original, double increment, double max, boolean greater) {
        if (greater == false) {
            if (original + increment <= max) {
                return increment;
            } else if (original >= max){
                return 0;
            } else{
                return max-original;
            }
        } else {
            if (original + increment >= max) {
                return Math.abs(increment);
            } else if(original <= max){
                return 0;
            } else {
                return Math.abs(max-original);
            }
        }
    }
    //setting camera as you move
    private static void move(Rectangle character) {
        Character.setCam(new Point(character.x + Constants.charWidth() / 2 - Constants.WINDOW_WIDTH / 2, character.y + Constants.charHeight() / 2 - Constants.WINDOW_HEIGHT / 2 + 50));
        //System.out.println(Character.getCam());
    }
    public static Rectangle moveCollision(Rectangle character, Point position,int row, int col, int rMax, int cMax) {
        double coefficient = 1;
        double ratioX = (double)(character.x-position.x)/50.00; //50 is modifier for making smallest unit
        double ratioY = (double)(character.y-position.y)/50.00;
        ratioX*=-1;
        ratioY*=-1;
        Rectangle temp = new Rectangle(character.x, character.y, character.width, character.height);
        loop:
        for (;;) {
            temp.translate((int) Math.round(ratioX * coefficient), (int) Math.round(ratioY * coefficient)); //moves in opposite direction
            if (collision(row,col,rMax,cMax,temp) == false) {
                return temp;
            } else {
                coefficient++;
                temp = new Rectangle(character.x, character.y, character.width, character.height);
            }
        }
    }
}