package culminating.activity;

public class Item {

    private int code = 0;
    private int quantity = 0;

    public Item() {
    }

    public Item(int c, int q) {//item code
        code = c;
        quantity = q;
    }

    public void increase(int q) {//increase quantity of item
        quantity += q;
    }

    public void decrease(int q) {//subtract number of item
        quantity -= q;
        if (quantity <= 0) {
            code = 0;
        }
    }

    public void setCode(int c, int q) {//set code for the item
        if (q != 0) {
            code = c;
            quantity = q;
        } else {
            reset();
        }
    }

    public int getCode() {//get the code of the item
        return code;
    }

    public int getQuantity() {//get quantity of the item
        return quantity;
    }

    public void reset() {//reset item
        code = 0;
        quantity = 0;
    }
}