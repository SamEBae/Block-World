package culminating.activity;

import culminating.activity.Monsters.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class TimerAction implements ActionListener {
    //actionlinsters based on time
    @Override
    public void actionPerformed(ActionEvent e) {
        if (MainGame.death.isVisible() == false && MainGame.pause.isVisible() == false) {
            new Draw(Character.getCam(), Mouse.getCoords(), Character.getDirection());
            if (Character.hp <= 0) {
                MainGame.showDeath();
                Character.getVector().reset();
                Physics.queue.clear();
            }
            if(Keys.keys[27]==true){
                MainGame.showPause();
            }
            if (Keys.keys[73] == true) {
                releaseKeys();
                MainGame.showItem();
                InventoryWindow.displayInventory();
            }
            if (Keys.keys[67] == true) {
                releaseKeys();
                MainGame.showCrafting();
                CraftingWindow.displayCrafting();
            }
            if (Keys.keys[69] == true) {
                releaseKeys();
                MainGame.showEquipment();
                EquipmentWindow.displayEquipment();
            }
            TimeOfDay.increment();
            Physics.physics();
            MonsterPhysics.move();
            if (Mouse.getClick() == true) {
                Character.setRotation(Character.getRotation() + Constants.ROTATION);
                Point p = Mouse.getCoords();
                int x = (Character.getCam().x + p.x) / Constants.BLOCK_WIDTH;
                int y = (Character.getCam().y + p.y) / Constants.BLOCK_HEIGHT;
                if (Character.consume == true) { //consumes an item
                    Character.setRotation(0);
                    InfoWindow.addText("You have used a " + Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).name + ".");
                    Stats.useItem(QuickSlot.getType(QuickSlot.getCurItem()));
                    QuickSlot.decrease(QuickSlot.getCurItem());
                    Character.consume = false;
                } else if (x >= 0 && y >= 0 && x < Constants.WORLD_WIDTH && y < Constants.WORLD_HEIGHT) {
                    int temp = World.getBlockType(y, x);
                    int code;
                    if ((code = MonsterPhysics.onMob(new Point(x * Constants.BLOCK_WIDTH, y * Constants.BLOCK_HEIGHT))) >= 0) {
                        if (Character.damageCountdown == 0 && rangeCheckDamage(new Rectangle(Character.charX(), Character.charY(), Constants.charWidth(), Constants.charHeight()), new Rectangle(Monsters.getLocation(code), new Dimension(MonsterStats.get(Monsters.getCode(code)).width, MonsterStats.get(Monsters.getCode(code)).height))) == true) {
                            if (QuickSlot.getType(QuickSlot.getCurItem()) == 0) {
                                Monsters.damage(code, Constants.HAND_DAMAGE);
                            } else {
                                Monsters.damage(code, Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).dmgM);
                            }
                            double direction = new Vector(Monsters.getLocation(code).x + MonsterStats.get(Monsters.getCode(code)).width / 2 - (culminating.activity.Character.charX() + Constants.charWidth() / 2),
                                    -1 * (Monsters.getLocation(code).y + MonsterStats.get(Monsters.getCode(code)).height / 2 - (culminating.activity.Character.charY() + Constants.charHeight() / 2)), false).getAngle();
                            Vector v = new Vector(MonsterStats.get(Monsters.getCode(code)).kba, direction);
                            if (v.xComponent() <= 0 && v.yComponent() < 1 && v.yComponent() > -1) {
                                v.add(new Vector(3, Math.toRadians(135)));
                            } else if (v.xComponent() > 0 && v.yComponent() < 1 && v.yComponent() > -1) {
                                v.add(new Vector(3, Math.toRadians(45)));
                            }
                            if (Constants.sfx == true) {
                                Sounds.Drop.play();
                            }
                            Character.setRotation(0);
                            MonsterPhysics.queue.put(code, v);
                            Character.damageCountdown = Constants.DAMAGE_SPEED;
                        }
                    } else if (Character.place == true && temp == 0 && QuickSlot.getType(QuickSlot.getCurItem()) != 0) {
                        Character.setRotation(0);
                        Rectangle character = new Rectangle(Character.charX(), Character.charY(), Constants.charWidth(), Constants.charHeight());
                        Rectangle block = new Rectangle(x * Constants.BLOCK_WIDTH, y * Constants.BLOCK_HEIGHT, Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT);
                        if (character.intersects(block) == false && MonsterPhysics.OnMob(block) == -1 || Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).collidable == false) {
                            if (rangeCheck(new Point(x * Constants.BLOCK_WIDTH, y * Constants.BLOCK_HEIGHT), new Point(Character.charX(), Character.charY())) == true) {
                                World.setBlock(y, x, QuickSlot.getType(QuickSlot.getCurItem()));
                                World.resetHp(y, x);
                                QuickSlot.decrease(QuickSlot.getCurItem());
                                if (Constants.sfx == true) {
                                    Sounds.Blop.play();
                                }
                            } else {
                                //InfoWindow.addText("Block is out of range.");
                            }
                        }
                    } else if (Character.mine == true && temp != 0) {
                        if (x != Character.lastx || y != Character.lasty) {
                            Character.miningCountdown = Constants.MINING_SPEED;
                            Character.setRotation(0);
                        }
                        if (Character.miningCountdown == 0) {
                            if (World.getHp(y, x) > 0) {
                                if (rangeCheck(new Point(x * Constants.BLOCK_WIDTH, y * Constants.BLOCK_HEIGHT), new Point(Character.charX(), Character.charY())) == true) {
                                    if (QuickSlot.getType(QuickSlot.getCurItem()) == 0) {
                                        World.setHp(y, x, World.getHp(y, x) - Constants.HAND_DAMAGE);
                                    } else {
                                        if (World.getBlockType(y, x) == 4 || World.getBlockType(y, x) == 10) {//wood
                                            World.setHp(y, x, World.getHp(y, x) - Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).dmgW);
                                        } else {
                                            World.setHp(y, x, World.getHp(y, x) - Stats.get(QuickSlot.getType(QuickSlot.getCurItem())).dmgB);
                                        }
                                    }
                                    Character.setRotation(0);
                                    if (Constants.sfx == true) {
                                        Sounds.Woosh.play();
                                    }
                                } else {
                                    InfoWindow.addText("Block is out of range.");
                                }
                            }
                            Character.miningCountdown = Constants.MINING_SPEED;
                        } else if (Character.miningCountdown > 0) {
                            Character.miningCountdown -= 1;
                        }
                    } else if (Character.damageCountdown == 0) {
                        Character.setRotation(0);
                    }
                }
                Character.lastx = x;
                Character.lasty = y;
            }
            if (Character.damageCountdown > 0) {
                Character.damageCountdown--;
            }
            if (Character.damageInterval > 0) {
                Character.damageInterval--;
            }
        }
    }

    public boolean rangeCheck(Point b, Point c) {//range check for mining
        b.translate(Constants.BLOCK_HEIGHT / 2, Constants.BLOCK_WIDTH / 2);
        c.translate(Character.getChar(0).getWidth(null) / 2, Character.getChar(0).getHeight(null) / 2);
        if (b.distance(c) < Constants.PLACE_RANGE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean rangeCheckDamage(Rectangle Char, Rectangle Mob) {//range check for hunting
        if (Mob.contains(Char)) {
            return true;
        } else {
            Point center = new Point((int) Char.getCenterX(), (int) Char.getCenterY());
            double[] distances = new double[]{center.distance(new Point((int) Mob.getMaxX(), (int) Mob.getMaxY())), center.distance(new Point((int) Mob.getMaxX(), (int) Mob.getMinY())), center.distance(new Point((int) Mob.getMinX(), (int) Mob.getMaxY())), center.distance(new Point((int) Mob.getMinX(), (int) Mob.getMinY()))};
            int lowest = Integer.MAX_VALUE;
            for (int i = 0; i < distances.length; i++) {
                if ((int) (distances[i]) < lowest) {
                    lowest = (int) distances[i];
                }
            }
            if (lowest < Constants.ATTACK_RANGE) {
                return true;
            } else {
                //InfoWindow.addText("This monster is out of range.");
                return false;
            }
        }
    }

    public void releaseKeys() {
        for (int i = 0; i < Keys.keys.length; i++) {
            Keys.keys[i] = false;
        }
    }
}