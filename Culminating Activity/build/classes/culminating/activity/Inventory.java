package culminating.activity;

public class Inventory {

    private static Item[] inventory = new Item[27];

    public static void initInventory() {//initialize inventory
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new Item();       
            //inventory[i].setCode(101, 1);
        }
    }

    public static int add(int code, int quantity) {//add to inventory
        loop:
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() == code && inventory[i].getQuantity() < Stats.get(code).stack) {
                int temp = Math.min(Stats.get(code).stack - inventory[i].getQuantity(), quantity);
                inventory[i].increase(temp);
                quantity -= temp;
                if (quantity <= 0) {
                    break loop;
                }
            }
        }
        if (quantity > 0) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i].getCode() == 0) {
                    inventory[i].setCode(code, quantity);
                    quantity = 0;
                    break;
                }
            }
        }
        if (quantity > 0) {
            InfoWindow.addText("Inventory is full.");
            return quantity;
        } else {
            return 0;
        }
    }

    public static void setQuantity(int index, int quantity) {//setting quantity
        inventory[index].setCode(inventory[index].getCode(), quantity);
    }
    public static void setItem(int index, int code, int quantity){//seting certian item
        inventory[index].setCode(code, quantity);        
    }
    public static void sort() {//sort
        outer:
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() == 0) {
                inner:
                for (int j = i + 1; j < inventory.length; j++) {
                    if (inventory[j].getCode() != 0) {
                        inventory[i].setCode(inventory[j].getCode(), inventory[j].getQuantity());
                        inventory[j].reset();
                        break inner;
                    }
                    if (j == inventory.length - 1) {
                        break outer;
                    }
                }
            }
        }
    }

    public static int slots() {//slots
        int empty = 0;
        loop:
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() == 0) {
                empty++;
            }
        }
        return empty;
    }

    public static void delete(int i) {//deleting items
        inventory[i].reset();
    }

    public static int getType(int i) {//return type of item
        return inventory[i].getCode();
    }

    public static int getQuantity(int i) {//return the quantity of item
        return inventory[i].getQuantity();
    }

    public static void printInventory() {//printing what you have
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() != 0) {
                System.out.println(Stats.get(inventory[i].getCode()).name + "      " + inventory[i].getQuantity());
            }
        }
    }

    public static void wipe() {//wipe everything in inventory
        for (int i = 0; i < inventory.length; i++) {
            inventory[i].reset();
        }
    }

    public static int numItems(int code) {//maxing an item
        int temp = 0;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() == code && inventory[i].getQuantity() > 0) {
                temp += inventory[i].getQuantity();
            }
        }
        return temp;
    }

    public static int remove(int code, int quantity) {//removing all quantity
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getCode() == code && inventory[i].getQuantity() > 0) {
                int temp = Math.min(quantity, inventory[i].getQuantity());
                inventory[i].decrease(temp);
                quantity -= temp;
            }
        }
        return quantity;
    }
}
