package culminating.activity;

import java.awt.Color;

public class TimeOfDay {//time of the day for spawn purposes
    private static int hour=8;
    private static int minute=0;
    private static int second=0;
    public static void increment(){
        second+=2;
        if(second == 60){
            minute++;
            second = 0;
        }
        if(minute == 60){
            hour++;
            minute = 0;            
        }
        if(hour == 24){
            hour = 0;
        }       
        if(hour == 20 && minute == 0 && second == 0){
            WorldGeneration.spawnMonsters();
            InfoWindow.addText("It is now night.");
            InfoWindow.addText("Monsters (excluding dragons) have respawned.");
        }
    }
    
    public static Color getColor(){
        if(hour>=8 && hour <18){ //daylight
            return new Color(95, 172, 250);
        } else if (hour>=18&&hour<20){ //halfdark
            return new Color(140,190,214);
        } else if (hour>=20 && hour<=23 || hour>=0&&hour<6){ //night
            return new Color(61,135,169);
        } else if (hour>=6 && hour <8){ //early morning
            return new Color(192,216,255);
        }        
        return null;        
    }
    
    public static int[] getTime(){
        return new int[]{hour,minute,second};
    }
    
    public static void setTime(int [] time){
        hour = time[0];
        minute = time[1];
        second = time[2];
    }
}
