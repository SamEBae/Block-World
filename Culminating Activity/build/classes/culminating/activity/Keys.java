package culminating.activity;

public class Keys{
    protected static boolean[] keys = new boolean[525];
    //key listeners
    public void formKeyPressed(java.awt.event.KeyEvent evt) {
        keys[evt.getKeyCode()] = true;
        for(int i = 49; i<=57;i++){
            if(keys[i] == true){
                QuickSlot.setCurItem(i-49);
                break;
            }
        }
    }

    public void formKeyReleased(java.awt.event.KeyEvent evt) {
        keys[evt.getKeyCode()] = false;
    }
}
