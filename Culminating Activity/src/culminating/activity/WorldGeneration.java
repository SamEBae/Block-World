package culminating.activity;

import java.awt.*;
import java.awt.Graphics2D.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import culminating.activity.Monsters.*;

public class WorldGeneration {

    static final int highElev = Constants.WORLD_HEIGHT / 2 - 100;
    static final int lowElev = Constants.WORLD_HEIGHT / 2 + 100;

    public static void generateWorld() {        
        outline();
        FillBlock(new int[]{9, 10, 11, 12, 13}, new int[]{50, 100, 700, 100, 50}, 2, 95);// fill stone
        addBlock(25, 20, 25, Constants.WORLD_WIDTH * (Constants.WORLD_HEIGHT / 2) / 2500, 3); //iron
        addBlock(100, 15, 20, Constants.WORLD_WIDTH * (Constants.WORLD_HEIGHT / 2) / 4000, 8); //gold
        addBlock(250, 15, 20, Constants.WORLD_WIDTH * (Constants.WORLD_HEIGHT / 2) / 10000, 16); //diamond
        addBlock(20, 40, 150, 200, 0); //add caves
        addHeartStone(70);
        addDeserts();
        chooseSpawn();
        Character.respawn();
        spawnMonsters();        
        addTrees();        
        addIslands();
        addBedrock();        
        //worldMap();        
    }

    private static void outline() {
        int curRow = Constants.WORLD_HEIGHT / 2;
        int direction = 0; //up, flat, down
        int blocks = 0; //how many blocks the direction lasts
        int[] increments = new int[10];
        int[] chance = new int[10];

        for (int i = 0; i < increments.length; i++) {
            increments[i] = i + 1;
            switch (increments[i]) {
                case 1:
                    chance[i] = 770;
                    break;
                case 2:
                    chance[i] = 150;
                    break;
                case 3:
                    chance[i] = 50;
                    break;
                case 4:
                    chance[i] = 10;
                    break;
                case 5:
                    chance[i] = 5;
                    break;
                case 6:
                    chance[i] = 5;
                    break;
                case 7:
                    chance[i] = 4;
                    break;
                case 8:
                    chance[i] = 3;
                    break;
                case 9:
                    chance[i] = 2;
                    break;
                case 10:
                    chance[i] = 1;
                    break;
            }
        }

        for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
            if (blocks == 0) {
                direction = weightedRandom(new int[]{-1, 0, 1}, new int[]{175, 650, 175});
                if (direction == 0) {
                    blocks = random(5, 10);
                } else {
                    blocks = random(2, 5);
                }
            } else {
                blocks--;
            }
            if (direction == -1) {
                curRow += weightedRandom(increments, chance);
                if (curRow >= lowElev) {
                    curRow = lowElev;
                }
            } else if (direction == 1) {
                curRow -= weightedRandom(increments, chance);
                if (curRow <= highElev) {
                    curRow = highElev;
                }
            }
            World.setBlock(curRow, col, 1);
            World.resetHp(curRow, col);
        }

        for (int row = 1; row < Constants.WORLD_HEIGHT; row++) {
            for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
                if (World.getBlockType(row - 1, col) != 0) {
                    World.setBlock(row, col, 1);
                    World.resetHp(row, col);
                }
            }
        }
    }
    //fills all blocks with /type/ below a random point between /lowBound/ and /highBound/ with a % /chance/

    private static void fillBlock(int lowBound, int highBound, int type, int chance) {
        for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
            innerLoop:
            for (int row = 0; row < Constants.WORLD_HEIGHT; row++) {
                if (World.getBlockType(row, col) != 0) {
                    row += random(lowBound, highBound);
                    for (int r = row; r < Constants.WORLD_HEIGHT; r++) {
                        if (new Random().nextInt(100) < chance) {
                            World.setBlock(r, col, type);
                            World.resetHp(r, col);
                        }
                    }
                    break innerLoop;
                }
            }
        }
    }
    
    //same as above but random num between bounds are generated weighted random
    private static void FillBlock(int[] increments, int[] probs, int type, int chance) {
        for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
            innerLoop:
            for (int row = 0; row < Constants.WORLD_HEIGHT; row++) {
                if (World.getBlockType(row, col) != 0) {
                    row += weightedRandom(increments, probs);
                    for (int r = row; r < Constants.WORLD_HEIGHT; r++) {
                        if (new Random().nextInt(100) < chance) {
                            World.setBlock(r, col, type);
                            World.resetHp(r, col);
                        }
                    }
                    break innerLoop;
                }
            }
        }
    }

    private static void addBlock(int minDepth, int lowA, int highA, int num, int type) {
        for (int i = 0; i < num; i++) {
            int c = random(0, Constants.WORLD_WIDTH - 1);
            int r = random(elevationCheck(c) + minDepth, Constants.WORLD_HEIGHT);
            int area = random(lowA, highA);
            int[] distribution;
            if (type != 0) {
                distribution = new int[random(2, area / 2)];
            } else {
                distribution = new int[random(4, area / 4)];
            }
            for (int j = 0; j < distribution.length - 1; j++) {
                distribution[j] = random((int) Math.floor(area / distribution.length), (int) Math.ceil(area / distribution.length));
                area -= distribution[j];
            }
            distribution[distribution.length - 1] = area;

            int counter = 0;
            loop:
            for (int row = 0; row < distribution.length; row++) {
                for (int col = 0; col < distribution[row]; col++) {
                    if (row + r >= 0 && col + c >= 0 && row + r < Constants.WORLD_HEIGHT && col + c < Constants.WORLD_WIDTH) {
                        if (type != 0) {
                            World.setBlock(row + r, col + c, type);
                        } else {
                            int temp = World.getBlockType(row + r, col + c);
                            World.setBlock(row + r, col + c, 0);
                            if (temp != 1 && temp != 2) {
                                counter++;
                            }
                            if (counter == 2) { //stops placing the cave if it erases too many valuable minerals
                                break loop;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private static void addHeartStone(int num){
        for(int i = 0; i<num;){
            int x = random(1, Constants.WORLD_WIDTH-2);
            int y = random(1, Constants.WORLD_HEIGHT-2);
            if(World.getBlockType(y, x)!= 0){
                i++;
                World.setBlock(y, x, 18);
                World.resetHp(y, x);
            }            
        }        
    }

    private static void addDeserts() {
        int minElev = 0;
        int maxElev = Integer.MAX_VALUE;
        for (int col = 0; col < Constants.WORLD_WIDTH; col++, minElev = 0, maxElev = Integer.MAX_VALUE) {
            int r = elevationCheck(col);
            loop:
            for (int i = col; i < col + 60 && i < Constants.WORLD_WIDTH; i++) {
                int temp = elevationCheck(i);
                if (temp >= r + 3 || temp <= r - 3) {
                    break loop;
                } else {
                    if (temp > minElev) {
                        minElev = temp;
                    }
                    if (temp < maxElev) {
                        maxElev = temp;
                    }
                }
                if (i == col + 60 - 1) {
                    for (int j = maxElev; j < minElev + 13; j++) {
                        for (int k = col; k < col + 60; k++) {
                            if (World.getBlockType(j, k) != 0) {
                                World.setBlock(j, k, 14);
                                World.resetHp(j, k);
                            }
                        }
                    }
                    col = i;
                }
            }
        }
    }

    private static void addTrees() {
        for (int i = 0; i < 500;) {
            int x = random(0, Constants.WORLD_WIDTH - 1);
            int y = elevationCheck(x);
            if (World.getBlockType(y, x) == 1) {
                i++;
                int h = random(7,30);
                if (areaCheck(h, x, y-1, getTreeWidth(h)+1)) {
                    for (int row = y - h; row < y; row++) {//adds the tree
                        World.setBlock(row,x,10);
                        World.resetHp(row,x);
                    }
                    for(int row = y-5;row >=y-h-1;row--){ //leaves
                        for(int col = x-getTreeWidth(row-(y-h)+5);col<=x+getTreeWidth(row-(y-h)+5);col++){
                            if(col>=0 && col < Constants.WORLD_WIDTH&&World.getBlockType(row, col) == 0){
                                World.setBlock(row,col,5);
                                World.resetHp(row,col);
                            }
                        }
                    }                    
                }

            } else if (World.getBlockType(y, x) == 14) { //desert, place cactus
                i++;
                int h = random(5, 10);
                if (areaCheck(h, x, y, 2)) {
                    for (int row = y - h; row < y; row++) {
                        World.setBlock(row,x,23);
                        World.resetHp(row,x);
                    }
                }
            }

        }
    }
    
    private static int getTreeWidth(int h){ //amount of cols of leaf maximum on each side
        if(h>=25){
            return 5;
        } else if (h>=20&& h<=24){
            return 4;
        } else if (h>=15 && h<=19){
            return 3;
        } else if (h>=10 && h<=14){
            return 2;            
        } else{
            return 1;
        }
    }     
    private static void addIslands(){//floating islands in the sky
        int num = random(10,15);
        for(int i = 0; i<num;){
            int width = random(30,40);
            int height = 20 + (int)Math.ceil(width/4);
            int x = random(0, Constants.WORLD_WIDTH-width);
            int y = random(100, 310);
            if(areaCheck(height,x+width/2,y+height,width/2)){
                i++;
                loop:
                for(int row = y+20, temp = 0;row <= y+height && temp <width/2;row++, temp +=2){
                    for(int col = x+temp;col< x+width-temp;col++){
                        if(random(1,100)<80){
                            World.setBlock(row, col, 1);
                        } else if (random(1,20)<15){
                            World.setBlock(row,col,8);
                        } else{
                            World.setBlock(row,col,16);
                        }
                        World.resetHp(row, col);
                    }
                }
                for(int row = y; row < y+20;row++){
                    World.setBlock(row, x+width-1, 17);  
                    World.resetHp(row,x+width-1);
                    World.setBlock(row,x+width-25,17);
                    World.resetHp(row,x+width-25);
                }
//                for(int col = x+width-1;col>=x+width-25;col--){
//                    World.setBlock(y, col, 17);  
//                    World.resetHp(y,col);
//                }
                Monsters.addMonster(5, (x+width-1)*Constants.BLOCK_WIDTH - MonsterStats.get(5).width, (y+1)*Constants.BLOCK_HEIGHT + 20); //place dragon
            }
        }
    }
    
    private static void addBedrock() {//border in which player cannot get out of
        for(int col = 0; col<Constants.WORLD_WIDTH;col++){
            World.setBlock(0, col, 24);
            World.resetHp(0,col);
            World.setBlock(Constants.WORLD_HEIGHT-1, col, 24);
            World.resetHp(Constants.WORLD_HEIGHT-1, col);
        }
        for(int row = 1; row <Constants.WINDOW_HEIGHT;row++){
            World.setBlock(row, 0, 24);
            World.resetHp(row,0);
            World.setBlock(row, Constants.WORLD_WIDTH-1, 24);
            World.resetHp(row, Constants.WORLD_WIDTH-1);
        }
    }

    private static boolean areaCheck(int h, int x, int y, int d) {//areacheck
        for (int row = y - h; row < y; row++) {
            for (int col = x - d; col <= x + d; col++) {
                if (col>=0 && col < Constants.WORLD_WIDTH &&World.getBlockType(row, col) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void chooseSpawn() {//respawn point
        int x = random(Constants.WORLD_WIDTH / 2 - 50, Constants.WORLD_WIDTH / 2 + 50);
        int y = 0;
        loop:
        for (; x < Constants.WORLD_WIDTH; x++) {
            for (; y < Constants.WORLD_HEIGHT; y++) {
                if (World.getBlockType(y, x) != 0) {
                    Character.setSpawn(x * Constants.BLOCK_WIDTH - Constants.WINDOW_WIDTH / 2 + Constants.charWidth() / 2, y * Constants.BLOCK_HEIGHT - 70 - Constants.WINDOW_HEIGHT / 2 + Constants.charHeight() / 2);
                    break loop;
                }
            }
        }
    }
    
    public static void spawnMonsters(){//monster spawning
        for(int i = 1; i<=4;i++){
            if(Monsters.getNumMob(i)<MonsterStats.get(i).amount){
                for(int j=Monsters.getNumMob(i);j<MonsterStats.get(i).amount;){
                    int x = random(2,Constants.WORLD_WIDTH-30);
                    int y = elevationCheck(x)*Constants.BLOCK_HEIGHT - MonsterStats.get(i).height-310;
                    x = x*Constants.BLOCK_HEIGHT - MonsterStats.get(i).width/2;
                    Rectangle m = new Rectangle(x,y,MonsterStats.get(i).width, MonsterStats.get(i).height);
                    Rectangle c = new Rectangle(Character.getCam());
                    c.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT); //monsters don't spawn near mosnters to begin with
                    c.grow(50, 50);
                    if(c.intersects(m) == false){
                        j++;
                        Monsters.addMonster(i,x,y);
                    }
                }
            }
        }        
    }

    private static int random(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low + 1) + low;
    }

    private static int weightedRandom(int[] nums, int[] prob) {
        int num = new Random().nextInt(1000);
        for (int i = 0, p = 0; i < nums.length; i++) {
            p += prob[i];
            if (num < p) {
                return nums[i];
            }
        }
        return 0;
    }

    private static int elevationCheck(int col) {
        int r = 0;
        for (int j = 0; j < Constants.WORLD_HEIGHT; j++) {
            if (World.getBlockType(j, col) != 0) {
                r = j;
                break;
            }
        }
        return r;
    }
    
    private static void worldMap(){//making a jpg of the world
        BufferedImage image = new BufferedImage(Constants.WORLD_WIDTH*5, Constants.WORLD_HEIGHT*5, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(95, 172, 250));
        g.fillRect(0,0,image.getWidth(), image.getHeight());
        for(int row = 0, y=0; row <Constants.WORLD_HEIGHT;row++,y+=5){
            for(int col = 0, x=0; col<Constants.WORLD_WIDTH;col++,x+=5){
                if(World.getBlockType(row, col)!=0){
                    g.drawImage(Stats.getImg(World.getBlockType(row,col)).getScaledInstance(5, 5, Image.SCALE_FAST), x,y,null);
                }
            }            
        }
        File f = new File("src/world.jpg");
        try{
            ImageIO.write(image, "jpg", f);
        }catch(IOException e){
            System.err.println(e);
        }        
    }
}