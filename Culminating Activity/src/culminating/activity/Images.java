package culminating.activity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public final class Images {

    static Image[] img = new Image[150];
    static Image[] select = new Image[150];
    static Image[] icon = new Image[150];
    static Image[] mini = new Image[150];
    public static Image[] monsters = new Image[5];
    public static Image[] monsters2 = new Image[5];

    public static void initImages() {//initializing images
        try {
            for (int i = 1; i <= img.length; i++) {
                if (new File("src/culminating/activity/Resources/" + String.valueOf(i) + ".png").exists()) {
                    if (new File("src/culminating/activity/Resources/" + String.valueOf(i) + "_2.png").exists()) {
                        img[i] = ImageIO.read(Images.class.getResource("Resources/" + String.valueOf(i) + "_2.png"));
                        select[i] = select(img[i]);
                        icon[i] = scale1(ImageIO.read(Images.class.getResource("Resources/" + String.valueOf(i) + ".png")),30,30);
                        mini[i] = scale1(ImageIO.read(Images.class.getResource("Resources/" + String.valueOf(i) + ".png")),20,20);
                    } else {
                        img[i] = ImageIO.read(Images.class.getResource("Resources/" + String.valueOf(i) + ".png"));
                        select[i] = select(img[i]);
                        icon[i] = scale1(img[i],30,30);
                        mini[i] = scale1(img[i],20,20);
                    }
                } else if (i == 10) {
                    img[i] = ImageIO.read(Images.class.getResource("Resources/4.png"));
                    select[i] = select(img[i]);
                }
            }
            for (int i = 1; i <= monsters.length; i++) {
                monsters[i - 1] = ImageIO.read(Images.class.getResource("Resources/M" + String.valueOf(i) + ".png"));
                monsters2[i - 1] = flip(monsters[i - 1]);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static Image scale1(Image i, int width, int height) {//scaling things
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(i, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    public static Image select(Image i) {//selecting block, making it white border
        BufferedImage image = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(i, 0, 0, null);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, 30, 3);
        g.fillRect(0, 0, 3, 30);
        g.fillRect(27, 0, 3, 30);
        g.fillRect(0, 27, 30, 3);
        return image;
    }

    public static Image flip(Image i) {//flip y axis
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-i.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter((BufferedImage) i, null);
    }

    public static BufferedImage rotate(Image i, double rotationRequired) {//rotate by degrees
        double locationX = 0;
        double locationY = i.getHeight(null);
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter((BufferedImage) i, null);
    }
}
