package culminating.activity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class InventoryWindow extends JFrame {

    private int curItem = -1;
    private Point corner = new Point(-1, -1);
    JButton qs = new JButton();
    JButton destroy = new JButton("Destroy");
    Image img;
    BufferedImage image = new BufferedImage(660, 480, BufferedImage.TYPE_INT_RGB);
    JPanel p = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    };

    public static void displayInventory() {
        new InventoryWindow();
    }

    public InventoryWindow() {
        try {
            img = ImageIO.read(InventoryWindow.class.getResource("Resources/Inventory.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }

        final JDialog invent = new JDialog();
        invent.setSize(img.getWidth(null), img.getHeight(null) + Constants.getTaskBar());
        invent.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        invent.setResizable(false);
        invent.setLocation(300, 300);
        invent.setTitle("Inventory");
        invent.setModal(true);

        p.setLayout(null);
        p.setBounds(0, 0, img.getWidth(null), img.getHeight(null));

        p.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent i) {
                click(i);
            }
        });
        qs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1();
            }
        });
        invent.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                invent.setVisible(false);
                invent.dispose();
                MainGame.showGame();
            }
        });

        qs.setBackground(new Color(174, 64, 62));
        qs.setFont(new Font("SansSerif", Font.BOLD, 16));
        qs.setForeground(new Color(62, 174, 176));
        qs.setBounds(240, 150, 180, 50);
        qs.setVisible(false);
        
        JButton sort = new JButton("Sort");
        sort.setBackground(new Color(174, 64, 62));
        sort.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sort.setForeground(new Color(62, 174, 176));
        sort.setBounds(530, 435, 70, 30);
        sort.setVisible(true);

        sort.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button2();
            }
        });

        destroy.setBackground(new Color(174, 64, 62));
        destroy.setFont(new Font("SansSerif", Font.PLAIN, 12));
        destroy.setForeground(new Color(62, 174, 176));
        destroy.setBounds(430, 435, 80, 30);
        destroy.setVisible(false);

        destroy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button3();
            }
        });
        p.add(qs);        
        p.add(sort);
        p.add(destroy);        
        invent.add(p);
        drawItems();
        invent.setVisible(true);

    }

    public void button1() {
        if (curItem <= 8) { //from qs to inventory
            int temp = Inventory.add(QuickSlot.getType(curItem), QuickSlot.getQuantity(curItem));
            if (temp != 0) {
                QuickSlot.setQuantity(curItem, temp);
                InfoWindow.addText("Move operation unsuccessful.");
            } else {
                QuickSlot.delete(curItem);
            }
        } else { //from inventory to qs
            int temp = QuickSlot.add(Inventory.getType(curItem - 9), Inventory.getQuantity(curItem - 9));
            if (temp != 0) {
                Inventory.setQuantity(curItem - 9, temp);
                InfoWindow.addText("Move operation unsuccessful.");
            } else {
                Inventory.delete(curItem - 9);
            }
        }
        drawItems();
    }

    public void button2() {
        curItem = -1;
        corner.setLocation(-1,-1);
        Inventory.sort();
        drawItems();
    }

    public void button3() {
        if (curItem <= 8) {
            InfoWindow.addText(QuickSlot.getQuantity(curItem) + " " + Stats.get(QuickSlot.getType(curItem)).name + "(s) was/were destroyed.");
            QuickSlot.delete(curItem);
        } else {
            InfoWindow.addText(Inventory.getQuantity(curItem - 9) + " " + Stats.get(Inventory.getType(curItem - 9)).name + "(s) was/were destroyed.");
            Inventory.delete(curItem - 9);
        }
        curItem = -1;
        corner.setLocation(-1,-1);
        drawItems();
    }

    public void click(MouseEvent i) {
        if (i.getButton() == 1) {
            Point coords = i.getPoint();
            if (coords.x >= 60 && coords.x <= 600 && coords.y >= 60 && coords.y <= 120) {
                curItem = (coords.x - 60) / 60;
                corner.setLocation((int) (coords.x / 60) * 60, (int) (coords.y / 60) * 60);
            } else if (coords.x >= 60 && coords.x <= 600 && coords.y >= 240 && coords.y <= 420) {
                curItem = (int) ((coords.x-60) / 60) + 9 * (int) ((coords.y - 180) / 60);
                corner.setLocation((int) (coords.x / 60) * 60, (int) (coords.y / 60) * 60);
            } else {
                curItem = -1;
                corner.setLocation(-1, -1);
                qs.setVisible(false);
            }
            drawItems();
        }
    }

    public final void drawItems() {
        Graphics2D g = image.createGraphics();
        g.drawImage(img, 0, 0, null);

        if (corner.x > 0 && corner.y > 0) {
            g.setStroke(new BasicStroke(6));
            g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, 16));
            if ((curItem <= 8 && QuickSlot.getType(curItem) != 0)) {
                g.drawRect(corner.x, corner.y, 60, 60);
                g.setColor(Color.white);
                g.drawString(Stats.get(QuickSlot.getType(curItem)).name, 480, 35);
                qs.setVisible(true);
                qs.setText("Move to Inventory");
                destroy.setVisible(true);
                if(Stats.get(QuickSlot.getType(curItem)).description.length()>0){
                    g.setColor(Color.WHITE);
                    g.drawString(Stats.get(QuickSlot.getType(curItem)).description,60,450);
                }
            } else if (curItem >= 9 && curItem <= 35 && Inventory.getType(curItem - 9) != 0) {
                g.drawRect(corner.x, corner.y, 60, 60);
                g.setColor(Color.white);
                g.drawString(Stats.get(Inventory.getType(curItem - 9)).name, 480, 220);
                destroy.setVisible(true);                
                if (Stats.get(Inventory.getType(curItem - 9)).quickSlot == true) {
                    qs.setVisible(true);
                    qs.setText("Move to Quick Slot");
                } else {
                    qs.setVisible(false);
                    g.setColor(Color.MAGENTA);
                    g.drawString("This item cannot be placed in the Quickslot.",160,180);
                }
                if(Stats.get(Inventory.getType(curItem-9)).description.length()>0){
                    g.setColor(Color.WHITE);
                    g.drawString(Stats.get(Inventory.getType(curItem-9)).description,60,450);
                }
            } else {
                qs.setVisible(false);
                destroy.setVisible(false);
            }
        }
        g.setColor(Color.cyan);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (int i = 0; i < 9; i++) {
            if (QuickSlot.getType(i) != 0) {
                g.drawImage(Stats.getIcon(QuickSlot.getType(i)), 60 * (i + 1) + 15, 75, null);
                if (Stats.get(QuickSlot.getType(i)).stack > 1) {
                    g.drawString(String.valueOf(QuickSlot.getQuantity(i)), 60 * (i + 1) + 40, 100);
                }
            }
        }
        for (int i = 0; i < 27; i++) {
            if (Inventory.getType(i) != 0) {
                g.drawImage(Stats.getIcon(Inventory.getType(i)), 60 * (i % 9 + 1) + 15, 240 + ((int) (i / 9)) * 60 + 15, null);
                if (Stats.get(Inventory.getType(i)).stack > 1) {
                    g.drawString(String.valueOf(Inventory.getQuantity(i)), 60 * (i % 9 + 1) + 40, 240 + ((int) (i / 9)) * 60 + 50);
                }
            }
        }
        p.repaint();
        image.flush();
        g.dispose();
    }
}