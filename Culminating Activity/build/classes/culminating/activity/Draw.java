package culminating.activity;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import culminating.activity.Monsters.*;

public class Draw {//drawings

    BufferedImage image = new BufferedImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    Point mouse;
    Point cam;
    int direction;

    public Draw(Point c, Point m, int k) {
        mouse = m;
        if (m == null) {
            mouse = new Point(-1, -1);
        }
        cam = c;
        direction = k;
        paint();
    }

    public Draw() {
    }

    private void paint() {
        paintBlocks();
        paintMonsters();
        paintChar();
        paintHP();
        paintQS();
        paintItem();
        MainGame.world.setIcon(new ImageIcon(image));
        g.dispose();
        image.flush();
    }

    private void paintBlocks() {//drawing blocks
        Point screen = new Point(0, 0);
        int c = round(false, true, cam.x) / Constants.BLOCK_WIDTH;
        int r = round(false, false, cam.y) / Constants.BLOCK_HEIGHT;
        int cMax = round(true, true, cam.x + Constants.WINDOW_WIDTH) / Constants.BLOCK_WIDTH;
        int rMax = round(true, false, cam.y + Constants.WINDOW_HEIGHT) / Constants.BLOCK_HEIGHT;
        int dx1 = cam.x - round(false, true, cam.x);
        int dx2 = round(true, true, (cam.x + Constants.WINDOW_WIDTH)) - (cam.x + Constants.WINDOW_WIDTH);
        int dy1 = cam.y - round(false, false, cam.y);
        int dy2 = round(true, false, (cam.y + Constants.WINDOW_HEIGHT)) - (cam.y + Constants.WINDOW_HEIGHT);
        BufferedImage temp = new BufferedImage((cMax - c) * Constants.BLOCK_WIDTH, (rMax - r) * Constants.BLOCK_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = temp.createGraphics();

        paintSky(temp, g2);

        for (int row = r; row < rMax; row++, screen.y += Constants.BLOCK_HEIGHT) {
            for (int col = c; col < cMax; col++, screen.x += Constants.BLOCK_WIDTH) {
                if (row >= 0 && col >= 0 && row < Constants.WORLD_HEIGHT && col < Constants.WORLD_WIDTH) {
                    draw(row, col, r, c, screen.x, screen.y, temp, g2, dx1, dy1);
                }
            }
            screen.x = 0;
        }
        g.drawImage(temp, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, dx1, dy1, temp.getWidth() - dx2, temp.getHeight() - dy2, null);
        temp.flush();
        g2.dispose();
    }

    private void draw(int row, int col, int r, int c, int x, int y, BufferedImage temp, Graphics2D g2, int dx1, int dy1) {
        if (World.getBlockType(row, col) != 0) {
            if (y < mouse.y + dy1 && y + Constants.BLOCK_HEIGHT > mouse.y + dy1
                    && x < mouse.x + dx1 && x + Constants.BLOCK_WIDTH > mouse.x + dx1) { //mouse is on the block  
                g2.drawImage(World.getSelect(row, col), x, y, null);
            } else {
                g2.drawImage(World.getImg(row, col), x, y, null);
            }
        }
    }

    private void paintSky(BufferedImage temp, Graphics2D g2) {//sky
        g2.setColor(TimeOfDay.getColor());
        g2.fillRect(0, 0, temp.getWidth(), temp.getHeight());
    }

    private void paintHP() {// hp bar
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.setColor(Color.red);
        String s = "Character Health: " + Character.hp + "/" + (Constants.CHAR_MAXHP + Equipment.hpBonus());
        g.drawString(s, Constants.WINDOW_WIDTH - 230, 30);
    }

    private void paintChar() {        //character will be 50 pixels ABOVE the middle of the screen
        if (direction == 0) {
            g.drawImage(Character.getChar(0), Constants.WINDOW_WIDTH / 2 - (Constants.charWidth() / 2), Constants.WINDOW_HEIGHT / 2 - (Constants.charHeight() / 2) - 50, null);
        } else {
            g.drawImage(Character.getChar(1), Constants.WINDOW_WIDTH / 2 - (Constants.charWidth() / 2), Constants.WINDOW_HEIGHT / 2 - (Constants.charHeight() / 2) - 50, null);
        }
    }

    private void paintQS() {
        int y = 25;
        for (int i = 0, x = 40; i < 9; i++, x += 60) {
            g.drawImage(Character.getChar(2), x, y, null);
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            if (QuickSlot.getType(i) != 0) {
                g.drawImage(Stats.getMini(QuickSlot.getType(i)), x + 10, y + 10, null);
                if (Stats.get(QuickSlot.getType(i)).stack > 1) {
                    g.setColor(Color.ORANGE);
                    g.drawString(String.valueOf(QuickSlot.getQuantity(i)), x + 30, y + 35);
                }
            }
            if (i == QuickSlot.getCurItem()) {
                g.setColor(Color.MAGENTA);
                g.setStroke(new BasicStroke(4));
                g.drawRect(x, y, 40, 40);
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        if (QuickSlot.getType(QuickSlot.getCurItem()) == 0) {
            g.drawString("Hands", 40, 80);
        } else {
            g.drawString(Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).name, 40, 80);
        }
    }

    private void paintMonsters() {//drawing monsters
        BufferedImage temp = new BufferedImage(Constants.WINDOW_WIDTH + 400 * 2, Constants.WINDOW_HEIGHT + 400 * 2, BufferedImage.TYPE_INT_ARGB);
        Rectangle r = new Rectangle(cam.x - 400, cam.y - 400, Constants.WINDOW_WIDTH + 400, Constants.WINDOW_HEIGHT + 400);
        Graphics2D g2 = temp.createGraphics();
        for (int i = 0; i < Monsters.getTotalNum(); i++) {
            Point m = Monsters.getLocation(i);
            if (r.contains(m)) {
                g2.drawImage(Monsters.getImg(i), m.x - r.x, m.y - r.y, MonsterStats.get(Monsters.getCode(i)).width, MonsterStats.get(Monsters.getCode(i)).height, null);
            }
        }
        g.drawImage(temp, 0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 400, 400, temp.getWidth() - 400, temp.getHeight() - 400, null);
        g2.dispose();
        temp.flush();
    }

    private void paintItem() {
        if (QuickSlot.getType(QuickSlot.getCurItem()) != 0 && Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).placable!=1) {
            if (Character.getDirection() == 1) {
                g.drawImage(Images.rotate(Stats.getIcon(QuickSlot.getType(QuickSlot.getCurItem())), Character.getRotation()), Character.charX() - Character.getCam().x + 17, Character.charY() - Character.getCam().y + 8, null);
            } else {
                g.drawImage(Images.flip(Images.rotate(Stats.getIcon(QuickSlot.getType(QuickSlot.getCurItem())), Character.getRotation())), Character.charX() - Character.getCam().x - 26, Character.charY() - Character.getCam().y + 7, null);
            }
        } else if (QuickSlot.getType(QuickSlot.getCurItem()) != 0 && Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).placable==1) {
            if (Character.getDirection() == 1) {
                g.drawImage(Images.rotate(Stats.getMini(QuickSlot.getType(QuickSlot.getCurItem())), Character.getRotation()), Character.charX() - Character.getCam().x + 17, Character.charY() - Character.getCam().y + 18, null);
            } else {
                g.drawImage(Images.flip(Images.rotate(Stats.getMini(QuickSlot.getType(QuickSlot.getCurItem())), Character.getRotation())), Character.charX() - Character.getCam().x - 16, Character.charY() - Character.getCam().y + 17, null);
            }        
        }
    }

    public static int round(boolean up, boolean width, int k) { //rounds to the nearest pixel that is an edge of a block        
        if (up == true) {
            if (width == true && k % Constants.BLOCK_WIDTH != 0) {
                k = (int) (Math.ceil((double) k / Constants.BLOCK_WIDTH)) * Constants.BLOCK_WIDTH;
            } else if (width == false && k % Constants.BLOCK_HEIGHT != 0) {
                k = (int) (Math.ceil((double) k / Constants.BLOCK_HEIGHT)) * Constants.BLOCK_HEIGHT;
            }
        } else {
            if (width == true && k % Constants.BLOCK_WIDTH != 0) {
                k = (int) (Math.floor((double) k / Constants.BLOCK_WIDTH)) * Constants.BLOCK_WIDTH;
            } else if (width == false && k % Constants.BLOCK_HEIGHT != 0) {
                k = (int) (Math.floor((double) k / Constants.BLOCK_HEIGHT)) * Constants.BLOCK_HEIGHT;
            }
        }
        return k;
    }
}