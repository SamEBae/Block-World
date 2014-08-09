package culminating.activity;
import java.awt.*;
import javax.swing.*;

public class InfoWindow {
    private static JTextArea text = new JTextArea();    
    
    public InfoWindow(){//the info window displaying what is going on
        JDialog info = new JDialog();
        info.setTitle("Info Window");
        info.setSize(370,200);
        info.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        info.setResizable(false);
        info.setLocation(Constants.WINDOW_LOCATIONX + Constants.WINDOW_WIDTH, 300);
        //info.setLocation(Constants.WINDOW_LOCATIONX + Constants.WINDOW_WIDTH, Constants.WINDOW_LOCATIONY + Constants.WINDOW_HEIGHT);
        
        text.setLineWrap(true);
        text.setEditable(false);
        text.setVisible(true);
        
        JScrollPane scroll = new JScrollPane(text);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        info.add(scroll);
        info.setVisible(true);
    }    
    
    public static void addText(String i){//adding text to the window
        if(text.getLineCount()>50){
            text.setText("");
        }
        text.append(i + "\n");       
       
    }
}
