package culminating.activity;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import culminating.activity.Files.*;
import java.awt.image.*;
import javax.swing.*;

public class MainGame extends JFrame {

    static Image img;
    static JLabel world = new JLabel();
    static JPanel death = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Character.getChar(3), 0, 0, null);
        }
    };
    static JPanel item = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Character.getChar(4), 0, 0, null);
        }
    };
    static JPanel crafting = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Character.getChar(5), 0, 0, null);
        }
    };
    static JPanel equipment = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(Character.getChar(6), 0, 0, null);
        }
    };
    static JPanel game = new JPanel();
    static JPanel pause = new JPanel() {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }
    };

    public MainGame() {//the main game and its drawings
        setTitle("Block World");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocation(Constants.WINDOW_LOCATIONX, Constants.WINDOW_LOCATIONY); //location of the window
        setResizable(false);
        setBackground(Color.GRAY);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                culminating.activity.Files.WriteWorld.write(Constants.WORLD_NAME);
            }
        });

        world.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        game.setLayout(null);
        game.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        game.setFocusable(true);
        game.add(world);

        death.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        death.setLayout(null);
        item.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        crafting.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        equipment.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        pause.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        pause.setLayout(null);

        JButton respawn = new JButton("Click to Respawn");
        respawn.setFont(new Font("SansSerif", Font.BOLD, 18));
        respawn.setBackground(Color.RED);
        respawn.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT - 200, 200, 70);
        respawn.setVisible(true);

        respawn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Inventory.wipe();
                Character.respawn();
                InfoWindow.addText("You have lost all the items in your inventory.");
                showGame();
            }
        });
        death.add(respawn);
        showGame();

        JButton resume = new JButton("Resume Game");
        resume.setFont(new Font("SansSerif", Font.BOLD, 18));
        resume.setBackground(Color.RED);
        resume.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT / 2 - 100, 200, 70);
        resume.setVisible(true);
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainGame.showGame();
            }
        });

        JButton suicide = new JButton("Suicide");
        suicide.setFont(new Font("SansSerif", Font.BOLD, 18));
        suicide.setBackground(Color.RED);
        suicide.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT / 2 + 30, 200, 70);
        suicide.setVisible(true);
        suicide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainGame.showDeath();
                MainGame.pause.setVisible(false);
                Character.getVector().reset();
                Physics.queue.clear();
            }
        });

        JButton save = new JButton("Save and Exit");
        save.setFont(new Font("SansSerif", Font.BOLD, 18));
        save.setBackground(Color.RED);
        save.setBounds(Constants.WINDOW_WIDTH / 2 - 100, Constants.WINDOW_HEIGHT / 2 + 160, 200, 70);
        save.setVisible(true);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                culminating.activity.Files.WriteWorld.write(Constants.WORLD_NAME);
                System.exit(0);
            }
        });
        pause.add(resume);
        pause.add(suicide);
        pause.add(save);

        add(game);
        game.add(death);
        game.add(item);
        game.add(crafting);
        game.add(equipment);
        game.add(pause);

        new InfoWindow();

        if (Constants.music == true) {
            Sounds.BGM.loop(Integer.MAX_VALUE);
        }

        setVisible(true);
        //action listeners
        Timer m_timer = new Timer(Constants.REFRESH_TIME, new TimerAction());
        m_timer.start();
        game.addKeyListener(new java.awt.event.KeyAdapter() {
            Keys key = new Keys();

            public void keyPressed(java.awt.event.KeyEvent evt) {
                key.formKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                key.formKeyReleased(evt);
            }
        });

        game.addMouseListener(new MouseAdapter() {
            Mouse m = new Mouse();

            public void mousePressed(MouseEvent i) {
                m.click(i);

            }

            public void mouseReleased(MouseEvent i) {
                m.release(i);
            }
        });
        game.addMouseMotionListener(new MouseAdapter() {
            Mouse m = new Mouse();

            public void mouseMoved(MouseEvent i) {
                m.move(i);
            }

            public void mouseDragged(MouseEvent i) {
                m.dragged(i);
            }
        });
        game.addMouseWheelListener(new MouseWheelListener() {
            Mouse m = new Mouse();

            public void mouseWheelMoved(MouseWheelEvent i) {
                m.wheel(i);
            }
        });
    }

    public static void showGame() {//showing game
        death.setVisible(false);
        crafting.setVisible(false);
        item.setVisible(false);
        equipment.setVisible(false);
        pause.setVisible(false);
        world.setVisible(true);
    }

    public static void showItem() {//showing item
        world.setVisible(false);
        item.setVisible(true);
    }

    public static void showDeath() {//death screen
        death.setVisible(true);
        world.setVisible(false);
    }

    public static void showCrafting() {//craftin screen
        world.setVisible(false);
        crafting.setVisible(true);
    }

    public static void showEquipment() {//equipment screen
        world.setVisible(false);
        equipment.setVisible(true);
    }

    public static void showPause() {//pause screen
        world.setVisible(false);
        img = grayScale(iconToImage(world.getIcon()));
        pause.setVisible(true);
    }

    public static void initGame() {
        QuickSlot.initQs();
        Inventory.initInventory();
        Images.initImages();
        Equipment.initEquipment();
        Character.loadChar();
        World.initBlocks();
    }

    public static Image grayScale(Image i) {
        ImageFilter filter = new GrayFilter(true, 50);
        ImageProducer producer = new FilteredImageSource(i.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(producer);
    }
    
     public static Image changeBrightness(Image i) {//brightness change
        BufferedImage buff = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        buff.createGraphics().drawImage(i, 0, 0, null);
        RescaleOp op = new RescaleOp(0.8f,0, null);
        BufferedImage buff1 = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        buff1.createGraphics().drawImage(buff, op, 0, 0);
        return Toolkit.getDefaultToolkit().createImage(buff1.getSource());
    }

    private static Image iconToImage(Icon icon) {//icon to image convert
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
}