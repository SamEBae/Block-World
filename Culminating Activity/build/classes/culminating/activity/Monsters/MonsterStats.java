package culminating.activity.Monsters;

import culminating.activity.Images;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public enum MonsterStats {
    //code, name,width, height,hp, speed, jumpPower, Knockback Power, kb against, damage, drops[percent, id....], monster max amount
    SNAKE(1,"Snake",40,40,150, 6, 8, 8, 13, 10,new int[] {75,61,15,60},65),
    ZOMBIE(2, "Zombie", 30, 90, 500, 5,10, 9,15, 20,new int [] {100,63,10,65},15),    
    SKELETON(3, "Skeleton", 50,90,1000,5,10,10,15,25,new int[]{100,57},10),
    OVERLORD(4, "Overlord", 200, 300, 100000, 4, 6, 10,6,50, new int []{90,62,25,66},3),
    DRAGON(5, "Dragon", 300, 300, 3000000, 17, 14, 12, 10,100, new int[]{100,58,50,59}, 0);
    
    int code;
    String name;
    public int width;
    public int height;
    int hp;
    int speed;
    int jump;
    int kb;
    public int kba;
    int dmg;
    int [] drops;
    public int amount;
    
    private MonsterStats(int c,String n,int x, int y, int hp, int s, int j, int k,int k2, int d, int [] dr, int a){
        this.code = c;
        this.name = n;
        this.width = x;
        this.height = y;
        this.hp=hp;
        this.speed = s;
        this.jump = j;
        this.kb = k;
        this.kba = k2;
        this.dmg = d;
        this.drops = dr;        
        this.amount = a;
    }
    
    private static final Map<Integer, MonsterStats> lookup = new HashMap<Integer, MonsterStats>();

    static {
        for (MonsterStats d : MonsterStats.values()) {
            lookup.put(d.getCode(), d);
        }
    }
    
    public int getCode() {
        return code;
    }
    
    public Image getImg(int i){
        return Images.monsters[i-1];        
    }
    
    public Image getImg2(int i){
        return Images.monsters2[i-1];        
    }
    
    public static MonsterStats get(int code) {
        return lookup.get(code);
    }
}
