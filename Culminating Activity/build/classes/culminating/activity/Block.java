package culminating.activity;

import java.util.Random;

public class Block {

    private int code = 0;
    private int hp = 0;

    public Block(int i) {//code of the block
        code = i;
    }

    public int getType() {//type of the block
        return code;
    }

    public void setType(int i) {//setting type
        code = i;
        if (code == 0) {
            hp = 0;
        }
    }

    public int getHp() {//hp of the block
        return hp;
    }

    public void setHp(int i) {//set hp
        if (Stats.get(code).destructable == false) {
            InfoWindow.addText("This block is indestructable!");
        } else {
            hp = i;
            if (i > 0) {
                InfoWindow.addText("This " + Stats.get(code).name + " has " + hp + " hp remaining.");
            } else if (i <= 0) {
                InfoWindow.addText("This " + Stats.get(code).name + " has 0 hp remaining.");
                destroy();
            }
        }
    }

    public void resetHp() {//reseting hp
        if (code == 0) {
            hp = 0;
        } else {
            hp = Stats.get(code).health;
        }
    }

    public void destroy() {//destroying via inventory
        int chance = Stats.get(code).dropP;
        Random r = new Random();
        if (r.nextInt(100) < chance) {
            QuickSlot.addDrop(Stats.get(code).dropItem);
            InfoWindow.addText("You have gained a " + Stats.get(Stats.get(code).dropItem).name + ".");
        }
        code = 0;
        hp = 0;
    }
}
