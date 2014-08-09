package culminating.activity.Files;

import culminating.activity.*;
import culminating.activity.Monsters.Monsters;
import java.io.*;

public class ReadWorld {
//filework to read the world
    public static void read(File file) {
        MainGame.initGame();
        try {
            BufferedReader read = new BufferedReader(new FileReader(file));
            String[] dimensions = read.readLine().split(" ");
            Constants.WORLD_WIDTH = Integer.parseInt(dimensions[0]);
            Constants.WORLD_HEIGHT = Integer.parseInt(dimensions[1]);
            for (int row = 0; row < Constants.WORLD_HEIGHT; row++) {
                String line = read.readLine();
                for (int col = 0; col < Constants.WORLD_WIDTH; col++) {
                    World.setBlock(row, col, toNum(line.charAt(col)));
                    World.resetHp(row, col);
                }
            }
            String[] inventory = read.readLine().split(" ");
            for (int i = 0; i < inventory.length; i++) {
                Inventory.setItem(i,Integer.parseInt(inventory[i].split(",")[0]), Integer.parseInt(inventory[i].split(",")[1]));
            }
            String[] qs = read.readLine().split(" ");
            for (int i = 0; i < qs.length; i++) {
                QuickSlot.setItem(i,Integer.parseInt(qs[i].split(",")[0]), Integer.parseInt(qs[i].split(",")[1]));
            }
            String[] equipment = read.readLine().split(" ");
            for (int i = 0; i < equipment.length; i++) {
                Equipment.addEquipment(Integer.parseInt(equipment[i].split(",")[1]));
            }
            for (int i = 1; i <= 5; i++) {
                String[] line = read.readLine().split(" ");
                for (int j = 1; j < Integer.parseInt(line[0]); j++) {
                    Monsters.addMonster(i, Integer.parseInt(line[j].split(",")[0]), Integer.parseInt(line[j].split(",")[1]));
                }
            }
            String[] time = read.readLine().split(" ");
            TimeOfDay.setTime(new int[]{Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2])});
            Constants.CHAR_MAXHP = Integer.parseInt(read.readLine());
            String[] spawn = read.readLine().split(" ");
            culminating.activity.Character.setSpawn(Integer.parseInt(spawn[0]), Integer.parseInt(spawn[1]));
        } catch (IOException e) {
            System.err.println(e);
        } 
        catch (RuntimeException ex) {
            InfoWindow.addText("Save file is corrupt.");
        }
    }

    public static int toNum(char c) {
        return java.lang.Character.getNumericValue(c);
    }
}
