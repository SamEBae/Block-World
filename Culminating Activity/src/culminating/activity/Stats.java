package culminating.activity;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public enum Stats {
    // index, name, max health, placable/tool/consumable/equipable/other=0, destructable, collidable, drop %, drop item, able to put in quick slot, dmg to blocks, wood, monsters, number per stack, description
    //1-49 are blocks, 50-99 are items, 100-124 are tools, 125-149 are equipment
    DIRT_BLOCK(1, "Dirt Block", 100, 1, true,true, 100, 1, true, 10, 10, 10,99,""),
    STONE_BLOCK(2, "Stone Block", 750, 1, true,true, 100, 2, true, 15, 15, 15,99,""),
    IRON_BLOCK(3, "Iron Block", 2000, 1, true,true, 100, 3, true, 20, 20, 20,99,""),
    WOOD_BLOCK(4, "Wood Block", 350, 1, true,true, 100, 4, true, 20, 20, 20, 99,""),
    LEAF_BLOCK(5, "Leaf Block", 1, 1, true,false, 15, 55, false,0, 0, 0, 99,""),
    WOOD_PLANK(6, "Wood Plank", 100, 1, true,true, 100, 6, true, 10, 10, 10, 99,""),
    CRAFTING_TABLE(7, "Crafting Table", 300, 1, true,true, 100, 7, true, 15, 15, 15, 1,""),
    GOLD_BLOCK(8, "Gold Block", 5000, 1, true,true, 100, 8, true, 25, 25, 25, 99,""),
    FURNACE(9, "Furnace", 750, 1, true,true, 100, 9, true, 15,15,15,1,""),
    WOOD_BLOCK_TREE(10, "Wood Block", 350, 0, true, false, 100, 4, false, 20, 20, 20, 99,""),
    GLASS(11,"Glass Block", 200, 1, true, true, 0, 11, true, 5, 5,5, 99,"Shatters when mined."),    
    STEEL_ACROSS(12, "Steel", 200000, 1, true, true, 100, 12, true, 75, 75, 75, 99,"Almost indestructable!"),
    STEEL_UP(13, "Steel", 200000, 1, true, true, 100, 13, true, 75, 75, 75, 99,"Almost indestructable!"),
    SAND_BLOCK(14, "Sand Block", 150, 1, true, true, 100, 14, true, 10, 10, 10, 99,""),
    BRICK(15, "Brick", 10000, 1, true, true, 100, 15, true, 25, 25, 25, 99,""),
    DIAMOND_BLOCK(16, "Diamond Block", 25000, 1, true,true, 100, 16, true, 90, 90, 90, 99,""),
    RUBY_BLOCK(17, "Ruby Block", 200000, 1, true,true, 100, 17, true, 100, 100, 100, 99,""),
    HEART_STONE(18, "Heart Stone", 50, 0, true, true, 100, 56, false, 0, 0, 0, 99,""),
    WOOD_WALL(19, "Wood Wall", 150, 1, true, false, 100, 19, true,15, 15, 15, 99,""),
    STONE_WALL(20, "Stone Wall", 350, 1, true, false, 100, 20, true, 15, 15, 15, 99,""),
    DIRT_WALL(21, "Dirt Wall", 75, 1, true, false, 100, 21,true,15, 15, 15, 99,""),
    SAND_WALL(22, "Sand Wall", 50, 1, true, false, 100, 22, true, 15,15,15,99,""),
    CACTUS_BLOCK(23,"Cactus Block", 400, 0, true, false, 100, 4, false, 0,0,0,99,""),
    BEDROCK(24, "Bedrock", 1, 1, false, true, 0, 0, true, 0, 0, 0, 99,""),
    
    STICK(50, "Stick", 0, 0, true,false, 0, 0, false, 0, 0, 0, 99,""),
    IRON_BAR(51, "Iron Bar", 0, 0, false, false, 0, 0, false, 0, 0, 0, 9,""),
    GOLD_BAR(52, "Gold Bar", 0, 0, false, false, 0, 0, false, 0, 0, 0, 9,""),
    DIAMOND(53, "Diamond", 0, 0, false, false, 0, 0, false, 0, 0, 0, 9,""),
    RUBY(54, "Ruby", 0, 0, false, false, 0, 0, false, 0, 0, 0, 9,""),
    APPLE(55, "Apple", 0, 3, false, false, 0, 0, true, 0, 0, 0, 9,"Heals 30 HP."),
    HEART(56, "Heart", 0, 3, false, false, 0, 0, true, 0, 0, 0, 1,"Permanently increases max HP by 20."),
    BONE(57, "Bone", 0,0, false, false, 0,0,false,0,0,0,99,""),
    DRAGON_BLOOD(58, "Dragon Blood", 0, 0, false, false, 0, 0, false, 0, 0, 0, 99, ""),
    DRAGON_GEM(59, "Dragon Gem", 0, 0, false, false, 0, 0, false, 0, 0, 0, 99, ""),
    SNAKE_MEAT (60, "Snake Meat",0, 3, false, false, 0, 0, true, 0, 0, 0, 99, "Recovers 60 HP."),
    SNAKE_SKIN(61, "Snake Skin",0, 0, false, false, 0, 0, false, 0, 0, 0, 99, ""),
    TITANIUM(62, "Titanium",0, 0, false, false, 0, 0, false, 0, 0, 0, 99, ""),
    ZOMBIE_TOOTH (63, "Zombie Tooth",0, 0, false, false, 0, 0, false, 0, 0, 0, 99, ""),
    ELIXIR(64, "Magical Elixir", 0,3,false,false,0,0,true,0,0,0,9,"Recovers all HP."),
    MAGICAL_TELEPORTER(65, "Magical Teleporter",0,3,false, false,0,0,true,0,0,0,9,"Returns to spawn point."),
    SCROLL_OF_DESTINY(66, "Scroll of Destiny", 0, 3, false, false, 0,0,true,0,0,0,9,"Sets spawn point to current view."),
    
    WOOD_AXE(100, "Wood Axe", 0, 2, false,false, 0, 0, true, 25, 50, 25, 1,""),
    WOOD_PICKAXE(101, "Wood Pickaxe", 0, 2, false,false, 0, 0, true, 75, 25,25,1,""),
    WOOD_SWORD(102, "Wood Sword", 0, 2, false,false, 0, 0, true, 25, 25, 50, 1,""),
    
    STONE_AXE(103, "Stone Axe", 0, 2, false,false, 0, 0, true, 50, 70, 100, 1,""),
    STONE_PICKAXE(104, "Stone Pickaxe", 0, 2, false,false, 0, 0, true, 250, 35, 50, 1,""),
    STONE_SWORD(105, "Stone Sword", 0, 2, false,false, 0, 0, true, 25, 25, 250, 1,""),
    
    IRON_AXE(106, "Iron Axe", 0, 2, false,false, 0, 0, true, 100, 120, 1000, 1,""),
    IRON_PICKAXE(107, "Iron Pickaxe", 0, 2, false,false, 0, 0, true, 500, 50, 250, 1,""),
    IRON_SWORD(108, "Iron Sword", 0, 2, false,false, 0, 0, true, 25, 25, 2500, 1,""),
    
    GOLD_AXE(109, "Gold Axe", 0, 2, false,false, 0, 0, true, 200, 350, 2500, 1,""),
    GOLD_PICKAXE(110, "Gold Pickaxe", 0, 2, false,false, 0, 0, true, 2500, 70, 250, 1,""),
    GOLD_SWORD(111, "Gold Sword", 0, 2, false, false,0, 0, true, 25, 25, 25000, 1,""),
    
    DIAMOND_AXE(112, "Diamond Axe", 0, 2, false, false, 0, 0, true,1000 ,350,25000,1,""),
    DIAMOND_PICKAXE(113, "Diamond Pickaxe", 0, 2, false, false, 0, 0, true, 12500,120, 2500,1,""),
    DIAMOND_SWORD(114,"Diamond Sword", 0, 2, false, false, 0, 0, true, 25,25,100000,1,""),
    
    SLAYER_OF_BOSSES(115, "Slayer of Bosses", 0, 2, false, false, 0, 0, true, 25,25,1000000,1,""),
    
    SPRINGY_SHOES(116, "Springy Shoes", 0,4, false, false, 0, 0,false, 0,0,0,1,"Jump Power +2"),
    WINGED_BOOTS (117, "Winged Boots", 0,4, false, false, 0, 0,false, 0,0,0,1,"Jump Power +4"),
    BOOTS_OF_SPEED (118, "Boots of Speed", 0,4, false, false, 0, 0,false, 0,0,0,1,"Char Speed +1. Glide over small holes."),
    JETPACK (119, "Jetpack", 0,4, false, false, 0, 0,false, 0,0,0,1,"Fly! Hold W to use."),
    PARACHUTE (120, "Parachute", 0,4, false, false, 0, 0,false, 0,0,0,1,"No fall damage."),
    LUCKY_HORSESHOE (121, "Lucky Horseshoe", 0,4, false, false, 0, 0,false, 0,0,0,1,"Crafting Chance +5%"),
    TITANIUM_SHIELD (122, "Titanium Shield",0,4,false,false,0,0,false,0,0,0,1,"Max HP +100");
    
    int code;
    public String name;
    int health;
    int placable;
    boolean destructable;
    public boolean collidable;
    int dropP;
    int dropItem;
    boolean quickSlot;
    int dmgB;
    int dmgW;
    int dmgM;
    int stack;
    String description;
    private static final Map<Integer, Stats> lookup = new HashMap<Integer, Stats>();

    static {
        for (Stats d : Stats.values()) {
            lookup.put(d.getCode(), d);
        }
    }

    private Stats(int cd, String n, int health, int p, boolean d,boolean c, int dp, int di, boolean qs, int dmgb, int dmgw, int dmgm, int s, String de) {
        this.code = cd;
        this.name = n;
        this.health = health;
        this.placable = p;
        this.destructable = d;
        this.collidable=c;
        this.dropP = dp;
        this.dropItem = di;
        this.quickSlot = qs;
        this.dmgB = dmgb;
        this.dmgW = dmgw;
        this.dmgM = dmgm;
        this.stack = s;
        this.description = de;
    }

    public int getCode() {
        return code;
    }

//    public String getName() {
//        return name;
//    }

    public static Stats get(int code) {
        return lookup.get(code);
    }

    public static boolean isWood(int code) {
        if (code == 4) {
            return true;
        } else {
            return false;
        }
    }

    public static Image getImg(int code) {
        return Images.img[code];
    }

    public static Image getSelect(int code) {
        return Images.select[code];
    }

    public static Image getIcon(int code) {
        return Images.icon[code];
    }
    
    public static Image getMini(int code) {
        return Images.mini[code];
    }
    
    public static void useItem(int i){
        switch(i){
            case 55:
                int temp = Math.min(30,Constants.CHAR_MAXHP + Equipment.hpBonus()-Character.hp);
                Character.hp+=temp;
                InfoWindow.addText("You have recovered " + temp+" HP.");
                break;
            case 56:
                Constants.CHAR_MAXHP+=20;
                InfoWindow.addText("Max HP permanently increased by 20.");
                break;
            case 60:
                int h = Math.min(60,Constants.CHAR_MAXHP + Equipment.hpBonus()-Character.hp);
                Character.hp+=h;
                InfoWindow.addText("You have recovered " + h+" HP.");
                break;
            case 64:
                InfoWindow.addText("You have recovered " + (Constants.CHAR_MAXHP + Equipment.hpBonus()-Character.hp) +" HP.");
                Character.hp = Constants.CHAR_MAXHP + Equipment.hpBonus();
                break;
            case 65:
                InfoWindow.addText("Character returned to spawn point.");
                Character.setCam(Character.getSpawn());
                break;
            case 66:
                InfoWindow.addText("New spawn set to (" + Character.getCam().x +","+Character.getCam().y +").");
                Character.setSpawn(Character.getCam().x, Character.getCam().y);
                break;
        }
        
        
    }
}
