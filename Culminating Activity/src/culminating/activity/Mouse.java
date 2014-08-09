package culminating.activity;

import java.awt.Point;
import java.awt.event.*;
import culminating.activity.Monsters.*;
//mouse action linsters
public class Mouse {

    private static boolean leftClick = false;
    private static Point coords;

    public void click(MouseEvent i) {
        if (i.getButton() == 1) {
            leftClick = true;
            coords = i.getPoint();
            clicked();
        }
    }

    public void clicked() {
        Character.setRotation(0);
        int x = (Character.getCam().x + coords.x) / Constants.BLOCK_WIDTH;
        int y = (Character.getCam().y + coords.y) / Constants.BLOCK_HEIGHT;
        if (x >= 0 && y >= 0 && x < Constants.WORLD_WIDTH && y < Constants.WORLD_HEIGHT) {
            if (QuickSlot.getType(QuickSlot.getCurItem()) != 0 && Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).placable == 3) {
                Character.consume = true;            
            } else {
                if (QuickSlot.getType(QuickSlot.getCurItem()) != 0 && World.getBlockType(y, x) == 0 && Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).placable == 1) {
                    Character.place = true;
                } else {
                    Character.mine = true;
                    Character.miningCountdown = Constants.MINING_SPEED;                    
                }
            }
        }
        Character.lastx = x;
        Character.lasty = y;
    }

    public void release(MouseEvent i) {
        if (i.getButton() == 1) {
            leftClick = false;
            Character.setRotation(0);
            clear();
        }
    }

    public void move(MouseEvent i) {
        coords = i.getPoint();
    }

    public void dragged(MouseEvent i) {
        coords = i.getPoint();
    }

    public void wheel(MouseWheelEvent i) {
        int temp = i.getWheelRotation() * -1;
        temp = QuickSlot.getCurItem() - temp;
        if (temp < 0) {
            while (temp < 0) {
                temp += 9;
            }
        }
        if (temp > 8) {
            while (temp > 8) {
                temp -= 9;
            }
        }
        QuickSlot.setCurItem(temp);
        if (leftClick == true) {
            clear();
            clicked();
        }
    }

    public static boolean getClick() {
        return leftClick;
    }

    public static void clear() {
        Character.consume = false;
        Character.place = false;
        Character.mine = false;
    }

    public static Point getCoords() {
        return coords;
    }
}
