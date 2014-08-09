package culminating.activity.Files;

import culminating.activity.*;
import culminating.activity.Monsters.*;
import java.io.*;

public class WriteWorld {

    public static void write(String name) {
        File file = new File("saves/" + name + ".dat");
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
            BufferedWriter write = new BufferedWriter(new FileWriter(file));
            write.write(Constants.WORLD_WIDTH + " " + Constants.WORLD_HEIGHT); //writes width and height
            write.newLine();
            for (int row = 0; row < Constants.WORLD_HEIGHT; row++) { //writes world
                for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
                    if (World.getBlockType(row, col) >= 0 && World.getBlockType(row, col) <= 9) {
                        write.write(String.valueOf(World.getBlockType(row, col)));
                    } else {
                        write.write((char) World.getBlockType(row, col) + 55);
                    }
                }
                write.newLine();
            }

            for (int i = 0; i < 27; i++) { //inventory
                write.write(Inventory.getType(i) + "," + Inventory.getQuantity(i));
                if (i != 26) {
                    write.write(" ");
                }
            }
            write.newLine();
            for (int i = 0; i < 9; i++) { //qs
                write.write(QuickSlot.getType(i) + "," + QuickSlot.getQuantity(i));
                if (i != 8) {
                    write.write(" ");
                }
            }
            write.newLine();
            for (int i = 0; i < 4; i++) { //equipment
                write.write(Equipment.getCode(i) + "," + Equipment.getCode(i));
                if (i != 3) {
                    write.write(" ");
                }
            }
            write.newLine();
            for(int i = 1, totalnum=0; i<=5;i++){ //monsters
                write.write(Monsters.getNumMob(i) +" ");
                for(int j = 0; j<Monsters.getNumMob(i);j++){                    
                    write.write(Monsters.getLocation(totalnum).x +"," + Monsters.getLocation(totalnum).y +" ");
                    totalnum++;                    
                }                
                write.newLine();
            }
            int [] time = TimeOfDay.getTime();//time of day
            write.write(time[0]+" " + time[1] +" " + time[2]);
            write.newLine();
            write.write(String.valueOf(Constants.CHAR_MAXHP)); //hp
            write.newLine();
            write.write(culminating.activity.Character.getSpawn().x+" "+culminating.activity.Character.getSpawn().y);
            write.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

/* world, two ints (width and length) followed by one line for each row in the world
 * inventory - code,quantity ....
 * quickslots
 * equipment
 * monster x,y - number of monsters followed by coords, each type is its own line
 * time of day - 3 ints
 * max hp - 1 int
 * spawn - 2 ints
 */