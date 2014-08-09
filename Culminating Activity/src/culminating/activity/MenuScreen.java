package culminating.activity;

import culminating.activity.*;
import culminating.activity.Files.ReadWorld;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MenuScreen extends JFrame {
    //some variables
    public static JFrame f;
    private Dimension[] resolutions = new Dimension[]{new Dimension(1440, 900), new Dimension(1280, 960), new Dimension(1152, 768), new Dimension(1024, 768), new Dimension(800, 600)};
    private int intX = 1152, intY = 768, inttut = 1, intsound = 0, intmusic = 0;
    private JDialog Controls1 = new JDialog();
    private Image tutorial = null, tutorial2 = null;

    public void Save() {
        try {
            File f = new File("saves/Config.ini");
            new File("saves").mkdir();
            f.createNewFile();
            Writer output = new BufferedWriter(new FileWriter(f));
            output.write(intX + "," + intY + "," + intsound + "," + intmusic);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            File f = new File("saves/Config.ini");
            if (f.exists()) {
                BufferedReader br = new BufferedReader(new FileReader("saves/Config.ini"));
                String data[] = br.readLine().split(",");
                intX = Integer.parseInt(data[0]);
                Constants.WINDOW_WIDTH = intX;
                intY = Integer.parseInt(data[1]);
                Constants.WINDOW_HEIGHT = intY;
                intsound = Integer.parseInt(data[2]);
                if (intsound % 2 == 0) {
                    Constants.sfx = true;
                } else {
                    Constants.sfx = false;
                }
                intmusic = Integer.parseInt(data[3]);
                if (intmusic % 2 == 0) {
                    Constants.music = true;
                } else {
                    Constants.music = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MenuScreen() {
        read();
        Save();
        read();
        final int start = 450;
        Font font = new Font("Algerian", Font.PLAIN, 40);
        setTitle("Main Menu");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocation(100, 100); //location of the window
        setResizable(false);
        setBackground(Color.GRAY);
        Container content = getContentPane();
        Image main = null, title = null;
        try {
            main = ImageIO.read(MainGame.class.getResource("Resources/MainMenu.png"));
            title = ImageIO.read(MainGame.class.getResource("Resources/Title.png"));
            tutorial = ImageIO.read(MainGame.class.getResource("Resources/Tutorial.png"));
            tutorial2 = ImageIO.read(MainGame.class.getResource("Resources/Tutorial 2.png"));
        } catch (IOException e) {
            System.err.println(e);
        }
        final loadWorld p2 = new loadWorld() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    g.drawImage(ImageIO.read(MainGame.class.getResource("Resources/MainMenu.png")), 0, 0, null);
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        };
        p2.setBounds(0, 0, 1280, 720);
        p2.setLayout(null);
        p2.setVisible(false);
        final ImagePanel panel = new ImagePanel(new ImageIcon(main).getImage());
        panel.setLayout(null);
        panel.setBounds(0, 0, 1280, 720);
        content.add(panel);
        content.add(p2);
        JLabel credits = new JLabel("By David Zhang, Sammie Bae, and Yuke Lu");
        credits.setBounds(850, 625, 450, 100);
        credits.setFont(new Font("Algerian", Font.PLAIN, 20));
        JButton titles = new JButton();         //menu stuff
        titles.setIcon(new ImageIcon(title));
        titles.setBounds(400, 25, 500, 150);
        final JButton Tutorial = new JButton("Tutorial");
        Tutorial.setBounds(100, 50, 250, 100);
        Tutorial.setFont(font);
        final JButton create = new JButton("Create World");
        create.setBounds(start, 200, 400, 100);
        create.setFont(font);
        final JButton continu = new JButton("Continue World");
        continu.setBounds(start, 325, 400, 100);
        continu.setFont(font);
        final JButton settings = new JButton("Settings");
        settings.setBounds(start, 450, 400, 100);
        settings.setFont(font);
        final JButton exit = new JButton("Exit Game");
        exit.setBounds(start, 575, 400, 100);
        exit.setFont(font);
        final JButton back = new JButton("<--");
        back.setBounds(0, 0, 100, 100);
        back.setFont(new Font("Algerian", Font.PLAIN, 50));
        back.setVisible(false);
        final JButton back2 = new JButton("<--");
        back2.setBounds(0, 0, 100, 100);
        back2.setFont(new Font("Algerian", Font.PLAIN, 50));
        back2.setVisible(false);
        final JButton tips = new JButton("Tips");
        tips.setSize(75, 85);
        tips.setFont(new Font("Algerian", Font.PLAIN, 15));
        final JButton next = new JButton("Next");
        next.setBounds(630, 0, 75, 75);
        next.setFont(new Font("Algerian", Font.PLAIN, 15));
        //setting stuff-------------------------------------------------------------------------------------------------------
        final JButton resolution = new JButton("Resolution: " + intX + " X " + intY);
        resolution.setBounds(start - 50, 200, 500, 100);
        resolution.setFont(font);
        resolution.setVisible(false);
        final JButton sound = new JButton();
        sound.setBounds(start - 50, 325, 500, 100);
        sound.setFont(font);
        sound.setVisible(false);
        final JButton music = new JButton();
        music.setBounds(start - 50, 450, 500, 100);
        music.setFont(font);
        music.setVisible(false);
        if (intsound % 2 == 0) {
            sound.setText("Sound:   ON");
        } else {
            sound.setText("Sound:   OFF");
        }
        if (intmusic % 2 == 0) {
            music.setText("Music:   ON");
        } else {
            music.setText("Music:   OFF");
        }
        Controls1.setSize(720, 480);
        final JLabel tutor = new JLabel("Hi");
        tutor.setSize(720, 480);
        tutor.setIcon(new ImageIcon(tutorial));
        Controls1.add(tips);
        Controls1.add(next);
        Controls1.add(tutor);
        final JTextField input = new JTextField();
        final JLabel enter = new JLabel("Enter World Name");
        final JButton next2 = new JButton("Next ------>");
        enter.setFont(font);
        enter.setBounds(450, 200, 400, 100);
        input.setBounds(450, 300, 400, 25);
        next2.setBounds(450, 400, 400, 100);
        next2.setFont(new Font("Algerian", Font.PLAIN, 50));
        enter.setVisible(false);
        input.setVisible(false);
        next2.setVisible(false);

        next2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Constants.WORLD_NAME = input.getText().trim();
                MainGame.initGame();
                WorldGeneration.generateWorld();
                new MainGame();
                setVisible(false);
                dispose();   
            }
        });
        Tutorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controls1.setVisible(true);
                playClick();
            }
        });
        tips.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Tips:\n"
                        + "- Walls are in the background (aren't collidable)\n"
                        + "- Floating islands with rare minerals and dragons are in the sky\n"
                        + "- Monsters respawn at night\n"
                        + "- Build and stand near furnace/crafting table to unlock more crafting options\n"
                        + "- You lose everything in inventory when you die (but not in qs or equip)\n"
                        + "- Take advantage of the suicide option early game to return to the surface with minerals.");
            }
        });
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inttut++;
                if (inttut % 2 == 0) {
                    tutor.setIcon(new ImageIcon(tutorial2));
                } else {
                    tutor.setIcon(new ImageIcon(tutorial));
                }
            }
        });
        continu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                p2.setVisible(true);
            }
        });
        titles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                JOptionPane.showMessageDialog(null, "Welcome to Block World!\nClick 'Create World' to begin");
            }
        });
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                enter.setVisible(true);
                input.setVisible(true);
                Tutorial.setVisible(false);
                create.setVisible(false);
                continu.setVisible(false);
                settings.setVisible(false);
                exit.setVisible(false);
                back2.setVisible(true);
            }
        });
        continu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
            }
        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                Tutorial.setVisible(true);
                resolution.setVisible(false);
                sound.setVisible(false);
                music.setVisible(false);
                create.setVisible(true);
                continu.setVisible(true);
                settings.setVisible(true);
                exit.setVisible(true);
                back.setVisible(false);
            }
        });
        back2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                enter.setVisible(false);
                input.setVisible(false);
                Tutorial.setVisible(true);
                create.setVisible(true);
                continu.setVisible(true);
                settings.setVisible(true);
                exit.setVisible(true);
                back2.setVisible(false);
                next2.setVisible(false);
            }
        });
        settings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                Tutorial.setVisible(false);
                back.setVisible(true);
                resolution.setVisible(true);
                sound.setVisible(true);
                music.setVisible(true);
                create.setVisible(false);
                continu.setVisible(false);
                settings.setVisible(false);
                exit.setVisible(false);
            }
        });
        resolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                for (int i = 0; i < resolutions.length; i++) {
                    if (resolutions[i].getWidth() == intX && resolutions[i].getHeight() == intY) {
                        i++;
                        if (i == resolutions.length) {
                            i = 0;
                        }
                        resolution.setText("Resolution: " + (int) resolutions[i].getWidth() + " X " + (int) resolutions[i].getHeight());
                        intX = (int) resolutions[i].getWidth();
                        intY = (int) resolutions[i].getHeight();
                        Constants.WINDOW_WIDTH = intX;
                        Constants.WINDOW_HEIGHT = intY;
                    }
                }
                Save();
            }
        });
        sound.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                intsound++;
                if (intsound % 2 == 0) {
                    sound.setText("Sound:   ON");
                    Constants.sfx = true;
                } else {
                    sound.setText("Sound:   OFF");
                    Constants.sfx = false;
                }
                Save();
            }
        });
        music.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                intmusic++;
                if (intmusic % 2 == 0) {
                    music.setText("Music:   ON");
                    Constants.music = true;
                } else {
                    music.setText("Music:   OFF");
                    Constants.music = false;
                }
                Save();
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClick();
                System.exit(0);
            }
        });
        input.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if (input.getText().substring(0, 1).equals(" ")) {
                    next2.setVisible(false);
                } else {
                    next2.setVisible(true);
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (input.getText().length() != 0) {
                    if (input.getText().substring(0, 1).equals(" ")) {
                        next2.setVisible(false);
                    } else {
                        next2.setVisible(true);
                    }
                } else {
                    next2.setVisible(false);
                }
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });

        panel.add(credits);     //adding to the panel
        panel.add(back);
        panel.add(back2);
        panel.add(next2);
        panel.add(titles);
        panel.add(Tutorial);
        panel.add(create);
        panel.add(continu);
        panel.add(settings);
        panel.add(exit);
        panel.add(resolution);
        panel.add(sound);
        panel.add(music);
        panel.add(enter);
        panel.add(input);
        setVisible(true);
        JOptionPane.showMessageDialog(null, "Click tutorial for a guide.");
    }

    public static void main(String[] args) {
        f = new MenuScreen();
    }

    public void playClick() {
        if (Constants.sfx == true) {
            Sounds.Click.play();
        }
    }
}

class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}

class loadWorld extends JPanel {

    public loadWorld() {//loading the world
        JPanel c = new JPanel();
        c.setLayout(new GridLayout(0, 1));
        c.setVisible(true);
        JScrollPane scroll = new JScrollPane(c);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(500, 250, 200, 300);
        add(scroll);
        final JLabel load = new JLabel("Available Worlds: ");
        load.setFont(new Font("Algerian", Font.PLAIN, 35));
        load.setBounds(500,200,500,50);
        add(load);
        File folder = new File("saves");
        final ArrayList<JButton> worlds = new ArrayList<JButton>();
        if (folder.exists()) {
            File[] list = folder.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile() && list[i].getName().endsWith(".dat")) {
                    JButton j = new JButton(list[i].getName().split(".dat")[0]);
                    j.setHorizontalTextPosition(AbstractButton.RIGHT);
                    j.setVerticalTextPosition(AbstractButton.CENTER);
                    j.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int i = 0; i < worlds.size(); i++) {
                                if (e.getSource() == worlds.get(i)) {
                                    Constants.WORLD_NAME = worlds.get(i).getText();
                                    ReadWorld.read(new File("saves/" + worlds.get(i).getText() +".dat"));
                                    culminating.activity.Character.respawn();
                                    MenuScreen.f.setVisible(false);
                                    MenuScreen.f.dispose();  
                                    new MainGame();                            
                                }
                            }
                        }
                    });
                    worlds.add(j);
                    c.add(j);
                }
            }
        }
    }
}