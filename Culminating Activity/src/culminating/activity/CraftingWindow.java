package culminating.activity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.*;

public class CraftingWindow extends JDialog {

    private Image img;
    private BufferedImage i = new BufferedImage(360, 300, BufferedImage.TYPE_INT_RGB);
    private JButton[] buttons = new JButton[37];
    private JButton craft = new JButton("Craft");
    private final int[] index = {50, 6, 7, 9, 19, 20, 21, 22,51, 52, 53, 54,65,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,64,116,117,118,119,120,121,122};
    private int curItem = 0;
    private JPanel c = new JPanel();
    private JPanel items = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(i, 0, 0, null);
        }
    };

    public static void displayCrafting() {//calls CraftingWindow
        new CraftingWindow();
    }

    public CraftingWindow() {
        try {
            img = ImageIO.read(CraftingWindow.class.getResource("Resources/Crafting.png")).getScaledInstance(660, 480, Image.SCALE_FAST);
        } catch (IOException e) {
            System.out.println(e);
        }
        final JDialog crafting = new JDialog();
        crafting.setSize(img.getWidth(null), img.getHeight(null) + Constants.getTaskBar());
        crafting.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        crafting.setResizable(false);
        crafting.setLocation(300, 300);
        crafting.setTitle("Crafting Menu");
        crafting.setModal(true);

        crafting.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                crafting.setVisible(false);
                crafting.dispose();
                MainGame.showGame();
            }
        });
        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };
        p.setBounds(0, 0, img.getWidth(null), img.getHeight(null));
        p.setLayout(null);
        p.setVisible(true);

        c.setLayout(new GridLayout(0, 1));
        c.setVisible(true);
        JScrollPane scroll = new JScrollPane(c);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30, 90, 200, 300);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(Stats.get(index[i]).name);
            buttons[i].setIcon(new ImageIcon(Stats.getIcon(index[i])));
            buttons[i].setHorizontalTextPosition(AbstractButton.RIGHT);
            buttons[i].setVerticalTextPosition(AbstractButton.CENTER);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < buttons.length; i++) {
                        if (e.getSource() == buttons[i]) {
                            drawItems(index[i]);
                            break;
                        }
                    }
                }
            });
        }
        rangeCheck();
        items.setBounds(270, 90, i.getWidth(), i.getHeight());
        items.setVisible(false);

        craft.setBackground(Color.RED);
        craft.setForeground(Color.cyan);
        craft.setBounds(280, 400, 100, 50);
        craft.setVisible(false);
        craft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicked();
            }
        });

        p.add(scroll);
        p.add(items);
        p.add(craft);
        crafting.add(p);
        crafting.setVisible(true);
    }

    public void clicked() {//clicked item
        int[] ingredients = Recipes.get(curItem).recipe;
        Random r = new Random();
        InfoWindow.addText("****** Crafting " + Stats.get(curItem).name + " ******");
        InfoWindow.addText("Rolling a number from 1 to " + (Recipes.get(curItem).percent + Equipment.getCraftingBonus()) + " out of 100 will get you the item.");
        int num = r.nextInt(100) + 1;
        InfoWindow.addText("You have rolled: " + num);
        for (int i = 0; i < ingredients.length; i += 2) {
            int temp = Inventory.remove(ingredients[i + 1], ingredients[i]);
            QuickSlot.remove(ingredients[i + 1], temp);
        }
        if (num <= Recipes.get(curItem).percent + Equipment.getCraftingBonus()) {
            InfoWindow.addText("Crafting successful.");
            if (Stats.get(curItem).quickSlot == true && Stats.get(curItem).placable == 2) {
                int temp = QuickSlot.add(curItem, Recipes.get(curItem).numGiven);
                if (temp > 0) {
                    temp = Inventory.add(curItem, temp);
                }
                checkFailure(temp);
            } else {
                int temp = Inventory.add(curItem, Recipes.get(curItem).numGiven);
                if (temp != 0 && Stats.get(curItem).quickSlot == true) {
                    temp = QuickSlot.add(curItem, temp);
                    checkFailure(temp);
                } else if (temp != 0 && Stats.get(curItem).quickSlot == false) {
                    checkFailure(temp);
                }
            }
        } else {
            InfoWindow.addText("Crafting failed.");
        }
        InfoWindow.addText("**************************");
        drawItems(curItem);
    }

    public void checkFailure(int temp) {//failure of craft
        if (temp > 0) {
            if (temp > 1) {
                InfoWindow.addText(temp + " " + Stats.get(curItem).name + "s have been lost due to lack of slots.");
            } else if (temp == 1) {
                InfoWindow.addText(temp + " " + Stats.get(curItem).name + " has been lost due to lack of slots.");
            }
        }

    }

    public void drawItems(int item) {//drawing items
        curItem = item;
        Graphics2D g = i.createGraphics();
        g.setColor(new Color(255, 250, 243));
        g.fillRect(0, 0, items.getWidth(), items.getHeight());

        g.setColor(new Color(75, 75, 51));
        g.setFont(new Font("Dialog", Font.BOLD, 18));
        g.drawString("Crafting " + Stats.get(item).name, 75, 25);

        g.setColor(new Color(113, 0, 192));
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString("Required: ", 35, 60);
        g.drawString("You have: ", 220, 60);

        g.setStroke(new BasicStroke(4));
        int[] ingredients = Recipes.get(item).recipe;
        g.drawLine(200, 60, 200, ingredients.length / 2 * 50 + 60);

        g.setStroke(new BasicStroke(2));
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        boolean hasItems = true;

        for (int i = 0, y = 60; i < ingredients.length; i += 2, y += 50) {
            g.setColor(new Color(113, 0, 192));
            if (ingredients[i] > 1) {
                g.drawString(String.valueOf(ingredients[i]) + " " + Stats.get(ingredients[i + 1]).name + "s", 35, y + 30);
            } else {
                g.drawString(String.valueOf(ingredients[i]) + " " + Stats.get(ingredients[i + 1]).name, 35, y + 30);
            }
            g.drawImage(Stats.getIcon(ingredients[i + 1]), 160, y + 10, null);
            g.drawLine(35, y + 50, 320, y + 50);

            int q = QuickSlot.numItems(ingredients[i + 1]) + Inventory.numItems(ingredients[i + 1]);
            if (q < ingredients[i]) {
                hasItems = false;
                g.setColor(new Color(254, 80, 120));
                g.drawString(String.valueOf(q), 250, y + 30);
            } else {
                g.setColor(new Color(0, 128, 64));
                g.drawString(String.valueOf(q), 250, y + 30);
            }
        }
        g.setColor(new Color(113, 0, 192));
        if (Recipes.get(item).numGiven > 1) {
            g.drawString("= " + Recipes.get(item).numGiven + " " + Stats.get(item).name + "s", 20, ingredients.length / 2 * 50 + 90);
        } else {
            g.drawString("= " + Recipes.get(item).numGiven + " " + Stats.get(item).name, 20, ingredients.length / 2 * 50 + 90);
        }
        g.drawImage(Stats.getIcon(item), 160, ingredients.length / 2 * 50 + 70, null);
        if(Equipment.getCraftingBonus()==0){
            g.drawString("This recipe has a " + Recipes.get(item).percent + "% rate of success.", 35, 280);
        } else {
            g.drawString("This recipe has a " + Recipes.get(item).percent + "% (+"+Equipment.getCraftingBonus()+"%) rate of success.", 35, 280);
        }

        if (hasItems) {
            craft.setVisible(true);
        } else {
            craft.setVisible(false);
        }

        if (items.isVisible() == false) {
            items.setVisible(true);
        } else {
            items.repaint();
        }
    }

    public void rangeCheck() {//range for furnace or a crafting table
        int c = Draw.round(false, true, Character.getCam().x) / Constants.BLOCK_WIDTH;
        int r = Draw.round(false, false, Character.getCam().y) / Constants.BLOCK_HEIGHT;
        int cMax = Draw.round(true, true, Character.getCam().x + Constants.WINDOW_WIDTH) / Constants.BLOCK_WIDTH;
        int rMax = Draw.round(true, false, Character.getCam().y + Constants.WINDOW_HEIGHT) / Constants.BLOCK_HEIGHT;
        boolean ct = false, f = false;

        for (int row = r; row < rMax; row++) {
            for (int col = c; col < cMax; col++) {
                if (row >= 0 && col >= 0 && row < Constants.WORLD_HEIGHT && col < Constants.WORLD_WIDTH) {
                    if ((new Point(Character.charX() + Constants.charWidth() / 2, Character.charY() + Constants.charHeight() / 2)).distance(new Point(col * Constants.BLOCK_WIDTH + Constants.BLOCK_WIDTH / 2,row * Constants.BLOCK_HEIGHT + Constants.BLOCK_HEIGHT / 2)) <= Constants.CRAFTING_RANGE) {
                        if (World.getBlockType(row, col) == 7) {
                            ct = true;
                        } else if (World.getBlockType(row, col) == 9) {
                            f = true;
                        }
                    }
                }
            }
        }
        for (int k = 0; k < buttons.length; k++) {
            int req = Recipes.get(index[k]).req;
            if (req == 0 || (ct == true && req == 1) || (f == true && req == 2)) {
                this.c.add(buttons[k]);
                buttons[k].setVisible(true);
            } else {
                buttons[k].setVisible(false);
            }
        }
    }
}
