package culminating.activity;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
public enum Recipes {
    //code,percent chance of success, number given,requires nothing/crafting table/furnace, items required[num, id, ....]
    STICK(50,75,3,0,new int[]{1,6}),
    WOOD_PLANK(6,75,2 ,0, new int[]{1,4}),
    CRAFTING_TABLE(7,100, 1, 0, new int[] {6, 4, 6, 6}),
    FURNACE(9, 100, 1, 0, new int [] {10, 2, 6, 3}),
    WOOD_WALL(19, 75, 3, 0, new int [] {1, 4}),
    STONE_WALL(20, 75, 3, 0, new int [] {1, 2}),
    DIRT_WALL(21, 75, 3, 0, new int [] {1, 1}),
    SAND_WALL(22,75, 3, 0, new int [] {1,14}),
    
    IRON_BAR(51, 100, 1, 2, new int[] {5, 3, 10, 50}),
    GOLD_BAR(52, 90, 1, 2, new int[] {5, 8, 15, 50}),
    DIAMOND(53, 80, 1, 2, new int[] {5, 16, 20, 50}),
    RUBY(54, 75, 1, 2, new int[] {5, 17, 25, 50}),
    
    WOOD_AXE(100, 75, 1, 1, new int[] {4,4,10,50}),
    WOOD_PICKAXE(101, 75, 1, 1,new int[] {6,4,10,50}),
    WOOD_SWORD(102, 75, 1, 1, new int[] {8,4,10,50}),
            
    STONE_AXE(103, 75,1,1,new int [] {5,2,10,50}),
    STONE_PICKAXE(104,75,1,1,new int [] {10,2,10,50}),
    STONE_SWORD(105, 75,1,1,new int [] {15,2,10,50}),    
            
    IRON_AXE(106, 70, 1,1,new int[] {1,51,10,50}),
    IRON_PICKAXE(107,70,1,1, new int[] {2,51,10,50}),
    IRON_SWORD(108, 70,1,1, new int[] {3,51,10,50}),    
            
    GOLD_AXE(109, 65, 1,1,new int[] {1,52,10,50}),
    GOLD_PICKAXE(110, 65, 1,1,new int[] {2,52,10,50}),
    GOLD_SWORD(111, 65, 1,1,new int[] {3,52,10,50}),    
            
    DIAMOND_AXE(112, 60, 1,1,new int[] {1,53,10,50}),
    DIAMOND_PICKAXE(113, 60, 1,1,new int[] {2,53,10,50}),
    DIAMOND_SWORD(114,60, 1,1,new int[] {3,53,10,50}),
    
    SLAYER_OF_BOSSES(115,100,1,1,new int [] {5,54,2,59}),
    ELIXIR(64,100,3,1,new int[]{1,58,2,60}),
    TELEPORTER(65,50,2,2,new int[]{1,51}),
    
    SPRINGY_SHOES(116,100,1,1,new int[]{3,61,2,57,2,51}),
    WINGED_BOOTS(117,100,1,1,new int[]{6,61,5,63,1,116}),
    BOOTS_OF_SPEED(118,100,1,1,new int[]{5,61,2,52}),
    JETPACK(119,100,1,1,new int[]{3,62,3,54,1,59}),
    PARACHUTE(120,100,1,1,new int[]{10,61,2,51}),
    LUCKY_HORSESHOE(121,100,1,1,new int[]{1,62,1,51,1,52}),
    TITANIUM_SHIELD(122,100,1,1,new int[]{2,62,5,63});
    
    int code;
    int percent;
    int numGiven;
    int req;
    int [] recipe;
    
    private Recipes(int c,int p, int n,int t, int [] r){
        code = c;
        percent = p;
        numGiven = n;
        req = t;
        recipe = r;        
    }
    
    private static final Map<Integer, Recipes> lookup = new HashMap<Integer, Recipes>();

    static {
        for (Recipes d : Recipes.values()) {
            lookup.put(d.getCode(), d);
        }
    }
    
    public int getCode() {
        return code;
    }
    
    public static Recipes get(int code){
        return lookup.get(code);
    }
}
