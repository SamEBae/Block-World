import java.awt.Graphics.*;
import java.awt.geom.AffineTransform; 
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Rotate {
    public static void startRotate(JLabel pic1, JLabel pic2,double angle){        
        Image myImage, newImage;
        myImage =iconToImage(pic2.getIcon());
        newImage = applyTransform(myImage, angle);
        ImageIcon receivedIcon = new ImageIcon(newImage);
        pic1.setIcon(receivedIcon);
    }
 private static Image applyTransform(Image image, double angle) { 
    int scale = 1;
    int width = image.getWidth(null);
    int height = image.getHeight(null);
 
    AffineTransform at = new AffineTransform();          
    at.rotate(angle, width/2, height/2);           
    int w = Math.min(2000, (int) (scale * width));     
    int h = Math.min(2000, (int) (scale * height));             
    BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);     
    Graphics2D  graphics = (Graphics2D) buffer.getGraphics();     
    graphics.drawImage(image, at, null);     
    return(buffer);   
} 
    static Image iconToImage(Icon icon) { 
          if (icon instanceof ImageIcon) { 
              return ((ImageIcon)icon).getImage(); 
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
