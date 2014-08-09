package culminating.activity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class EquipmentWindow extends JDialog {

    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<Integer> index = new ArrayList<Integer>();
    private Image img;
    private BufferedImage i = new BufferedImage(660, 480, BufferedImage.TYPE_INT_RGB);
    private JButton equip = new JButton("Equip");
    private JButton unequip = new JButton("Unequip");
    private JPanel c = new JPanel();
    private int curItem1 = -1; //from equipment panel
    private int curItem2 = -1; //equipped
    private Rectangle [] boxes = new Rectangle[]{new Rectangle(180,90,60,60), new Rectangle(270,90,60,60), new Rectangle(360,90,60,60), new Rectangle(450,90,60,60)};
    private JPanel p = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(i, 0, 0, null);
        }
    };

    public static void displayEquipment() {
        new EquipmentWindow();
    }

    public EquipmentWindow() {//the actual window
        try {
            img = ImageIO.read(CraftingWindow.class.getResource("Resources/Equipment.png")).getScaledInstance(660, 480, Image.SCALE_FAST);
        } catch (IOException e) {
            System.out.println(e);
        }
        final JDialog equipment = new JDialog();
        equipment.setSize(img.getWidth(null), img.getHeight(null) + Constants.getTaskBar());
        equipment.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        equipment.setResizable(false);
        equipment.setLocation(300, 300);
        equipment.setTitle("Equipment Menu");
        equipment.setModal(true);

        equipment.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                equipment.setVisible(false);
                equipment.dispose();
                MainGame.showGame();
            }
        });

        p.setBounds(0, 0, img.getWidth(null), img.getHeight(null));
        p.setLayout(null);
        p.setVisible(true);

        p.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent i) {
                mouse(i);
            }
        });

        equip.setBackground(Color.RED);
        equip.setForeground(Color.cyan);
        equip.setBounds(280, 400, 100, 50);
        equip.setVisible(false);
        equip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicked1();
            }
        });

        unequip.setBackground(Color.RED);
        unequip.setForeground(Color.cyan);
        unequip.setBounds(280, 170, 100, 50);
        unequip.setVisible(false);
        unequip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicked2();
            }
        });

        c.setLayout(new GridLayout(0, 1));
        c.setVisible(true);
        JScrollPane scroll = new JScrollPane(c);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(30, 210, 200, 250);
        addEquipment();

        p.add(equip);
        p.add(unequip);
        p.add(scroll);
        equipment.add(p);
        updateBackground();
        equipment.setVisible(true);

    }

    public void addEquipment() {//adding equipment
        c.removeAll();
        curItem1 = -1;
        curItem2 = -1;
        buttons.clear();
        index.clear();
        for (int i = 0; i < 27; i++) {
            if (Inventory.getType(i) != 0 && Stats.get(Inventory.getType(i)).placable == 4) {
                JButton m = new JButton(Stats.get(Inventory.getType(i)).name);
                m.setIcon(new ImageIcon(Stats.getIcon(Inventory.getType(i))));
                m.setHorizontalTextPosition(AbstractButton.RIGHT);
                m.setVerticalTextPosition(AbstractButton.CENTER);
                buttons.add(m);
                index.add(i);
                c.add(m);
                m.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < buttons.size(); i++) {
                            if (e.getSource() == buttons.get(i)) {
                                drawItem(index.get(i));
                                break;
                            }
                        }
                    }
                });
            }
        }
        c.revalidate();
    }

    public void drawItem(int item) {//drawing the equpment
        curItem1 = item;
        curItem2 = -1;
        updateBackground();
    }

    public void mouse(MouseEvent i) {//mouse check
        for(int j = 0; j<boxes.length;j++){
            if(boxes[j].contains(i.getPoint()) && Equipment.getCode(j)!= 0){
                curItem2 = j;
                curItem1 = -1;
            }            
        }        
        updateBackground();
    }

    public void updateBackground() {//update equpiment
        Graphics2D g = i.createGraphics();
        g.drawImage(img, 0, 0, null);

        if (curItem1 != -1) {
            g.setColor(new Color(205, 201, 201));
            g.fillRect(260, 210, 200, 250);
            g.drawImage(Stats.getImg(Inventory.getType(curItem1)), 290, 240, null);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.setColor(new Color(0, 0, 0));
            g.drawString(Stats.get(Inventory.getType(curItem1)).name, 290, 290);
            g.setFont(new Font("SansSerif", Font.PLAIN, 14));
            String [] parts = Stats.get(Inventory.getType(curItem1)).description.split("\\. ", 2);
            for(int i = 0,y=310; i<parts.length;i++,y+=20){
                g.drawString(parts[i], 290, y);
            }
            if(Equipment.getFull()==false){
                equip.setVisible(true);
            }
            unequip.setVisible(false);
        }

        if (curItem2 != -1) {
            g.setColor(Color.white);
            g.setFont(new Font("SansSerif", Font.BOLD, 15));            
            g.drawString(Stats.get(Equipment.getCode(curItem2)).name, 500, 80);
            unequip.setVisible(true);            
            equip.setVisible(false);
            g.setColor(Color.red);     
            g.setStroke(new BasicStroke(6));
            g.drawRect(boxes[curItem2].x, boxes[curItem2].y, boxes[curItem2].width, boxes[curItem2].height);
        }
        
        for(int i = 0; i<4;i++){
            g.drawImage(Stats.getImg(Equipment.getCode(i)), boxes[i].x+15, boxes[i].y+15,null);
        }

        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Stats", 500, 230);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString("Jump: " + (Constants.JUMP_POWER + Equipment.getJumpBonus()), 500, 260);
        g.drawString("Speed: " + (Constants.CHARACTER_SPEED + Equipment.getSpeedBonus()), 500, 280);
        g.drawString("Max HP: " + (Constants.CHAR_MAXHP + Equipment.hpBonus()), 500, 300);
        g.drawString("Crafting Bonus: " + Equipment.getCraftingBonus()+"%",500,320);
                
        p.repaint();
        i.flush();
    }

    public void clicked1() {//deleting
        Equipment.addEquipment(Inventory.getType(curItem1));
        Inventory.delete(curItem1);
        curItem1=-1;
        equip.setVisible(false);
        addEquipment();
        updateBackground();
    }

    public void clicked2() {//deleting
        int temp = Inventory.add(Equipment.getCode(curItem2), 1);
        if(temp==0){
            Equipment.delete(curItem2);
        }
        curItem2=-1;
        unequip.setVisible(false);
        addEquipment();
        updateBackground();
    }
}
