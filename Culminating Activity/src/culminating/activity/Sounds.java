package culminating.activity;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
   
public enum Sounds {
    BGM("BGM2.wav"),
    Drop("Drop Damaged.wav"),//damage soound
    Blop("Blop.wav"),//buidling sound
    Woosh("Woosh.wav"),//mining
    Click("Click.wav");//click sound
   private Clip clip;
   Sounds(String soundFileName) {
      try{
          File f = new File("src/culminating/activity/Resources/"+soundFileName);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
   }
   public void play() {
         if (clip.isRunning())
            clip.stop();   
            clip.setFramePosition(0);
            clip.start(); 
        }
   public void loop(int loopcount){
       if(!clip.isRunning()){
           clip.loop(loopcount);
       }
   }
   static void init() {
      values(); 
   }
}