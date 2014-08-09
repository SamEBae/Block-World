package culminating.activity;

import java.awt.Image;
import java.io.*;

public class World {
    private static Block [] [] blocks = new Block[Constants.WORLD_HEIGHT][Constants.WORLD_WIDTH];    
    //this class returns and sets properties of the world
    public static void initBlocks(){
        for(int row = 0; row<blocks.length;row++){
            for(int col = 0; col <blocks[0].length;col++){
                blocks [row][col] = new Block(0);
            }
        }
//        Random r = new Random();
//        for(int row = blocks.length/2; row <blocks.length;row++){
//            for(int col = 0; col < blocks[0].length; col++){
//                if(col!=140&&col!=141&&col!=142&&col!=143){                    
//                    blocks[row][col].setType(r.nextInt(5));
//                    blocks[row][col].resetHp();
//                }
//            }
//        }
    }
    public static int getBlockType(int row, int col){
        return blocks[row][col].getType();
    }
    public static void setBlock(int row, int col, int i){
        blocks[row][col].setType(i);
    }
    public static int getHp(int row, int col){
        return blocks[row][col].getHp();
    }
    public static void setHp(int row, int col, int hp){
        blocks[row][col].setHp(hp);        
    }
    public static void resetHp(int row, int col){
        blocks[row][col].resetHp();
    }
    public static Image getImg(int row, int col){
        return Stats.getImg(blocks[row][col].getType());
    }
    public static Image getSelect(int row, int col){
        return Stats.getSelect(blocks[row][col].getType());
    }
    public static Image getIcon(int row, int col){
        return Stats.getIcon(blocks[row][col].getType());
    }   
}
