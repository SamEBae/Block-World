package culminating.activity;

public class QuickSlot {

    private static Item[] qs = new Item[9];
    private static int curItem = 0;
    
    public static void setCurItem(int i) {//setting current item
        curItem = i;
    }

    public static int getCurItem() {//getting current item
        return curItem;
    }

    public static void initQs() {//initialized items
        for (int i = 0; i < qs.length; i++) {
            qs[i] = new Item();
        }
    }

    public static int add(int code, int quantity) {
        loop:
        for (int i = 0; i < qs.length; i++) {
            if (qs[i].getCode() == code && qs[i].getQuantity() < Stats.get(code).stack) {
                int temp = Math.min(Stats.get(code).stack - qs[i].getQuantity(), quantity);
                qs[i].increase(temp);
                quantity -= temp;
                if (quantity <= 0) {
                    break loop;
                }
            }
        }
        if (quantity > 0) {
            for (int i = 0; i < qs.length; i++) {
                if (qs[i].getCode() == 0) {
                    qs[i].setCode(code, quantity);
                    quantity = 0;
                    break;
                }
            }
        }
        if (quantity > 0) {
            InfoWindow.addText("Quick slots are full.");
            return quantity;
        } else {
            return 0;
        }
    }
    public static void setQuantity(int index, int quantity){
        qs[index].setCode(qs[index].getCode(), quantity);        
    }
    public static void setItem(int index, int code, int quantity){
        qs[index].setCode(code, quantity);        
    }
    public static void delete(int i) {
        qs[i].reset();
    }
    public static void decrease(int i){
        qs[i].decrease(1);
    }
    public static int getType(int i){
        return qs[i].getCode();
    }
    public static int getQuantity (int i){
        return qs[i].getQuantity();
    }
    public static void addDrop(int code){//adding item that was dropped from monster
        for(int i = 0; i<qs.length;i++){
            if (qs[i].getCode() == code && qs[i].getQuantity() < Stats.get(code).stack) {
                qs[i].increase(1);
                break;
            }
            if(i == qs.length-1){
                Inventory.add(code,1);
            }
        }
    }
    public static int numItems(int code){
        int temp=0;
        for(int i = 0; i<qs.length;i++){
            if(qs[i].getCode()==code && qs[i].getQuantity()>0){
                temp += qs[i].getQuantity();
            }
        }
        return temp;
    }
    public static int remove(int code, int quantity){//remove item
        for(int i =0 ; i<qs.length;i++){
            if(qs[i].getCode()== code && qs[i].getQuantity()>0){
                int temp = Math.min(quantity, qs[i].getQuantity());
                qs[i].decrease(temp);
                quantity -= temp;
            }            
        }
        return quantity;        
    }    
}
