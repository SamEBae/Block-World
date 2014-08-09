package culminating.activity;
public class Equipment {
    private static Item[] equipment = new Item[4];
    
    public static void initEquipment(){//equipment initialize
        for(int i = 0; i<equipment.length;i++){
            equipment[i]= new Item();
        }
    }
    public static void addEquipment(int j){//adding to equipment
        for(int i = 0; i<equipment.length;i++){
            if(equipment[i].getCode()==0){
                equipment[i].setCode(j, 1);
                break;
            }
        }
    }
    public static boolean getFull(){//checking if equipment is full
        for(int i = 0;i<equipment.length;i++){
            if(equipment[i].getCode()==0){
                return false;
            }
        }
        return true;
    }
    public static int getCode(int i){//code of equipment
        return equipment[i].getCode();
    }
    public static int getJumpBonus(){//jump bonus from equipment
        int jump = 0;
        for(int i= 0; i<equipment.length;i++){
            if(equipment[i].getCode()==116){
                jump+=2;
            } else if (equipment[i].getCode()==117){
                jump+=4;
            }
        }
        return jump;
    }
    
    public static int getSpeedBonus(){//speed bonus from equipment
        int s = 0;
        for(int i= 0; i<equipment.length;i++){
            if(equipment[i].getCode()==118){
                s+=1;
            } 
        }
        return s;
    }
    
    public static boolean jetpack(){//flying with jetpack
        for(int i = 0; i<equipment.length;i++){
            if(equipment[i].getCode()==119){
                return true;                
            }
        }
        return false;
    }
    
    public static int hpBonus(){
        int s = 0;
        for(int i= 0; i<equipment.length;i++){
            if(equipment[i].getCode()==122){
                s+=100;
            } 
        }
        return s;
    }
    
    public static int getCraftingBonus(){//crafting bonus chance via item
        int s = 0;
        for(int i= 0; i<equipment.length;i++){
            if(equipment[i].getCode()==121){
                s+=5;
            } 
        }
        return s;        
    }
    
    public static boolean fallDamage(){//no fall damage
        for(int i = 0; i<equipment.length;i++){
            if(equipment[i].getCode()==120){
                return false;                
            }
        }
        return true;
    }
    
    public static int getLength(){//length of the equipment
        return equipment.length;
    }
    
    public static void delete(int i){//deleting equipment
        equipment[i].reset();
    }
}
