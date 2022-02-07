package cn.tedu.submarine;

import javax.swing.*;
import java.util.Random;

//鱼雷潜艇
public class TorpedoSubmarine extends SeaObject implements EnemyScore {

    public int getScore(){
        return 40;//打掉鱼雷潜艇，玩家得40分
    }

    public TorpedoSubmarine(){
        super(64,20);
    }

    /**重写move()移动*/
    public void move(){
        x += speed;
    }

    /**重写 */
    public ImageIcon getImage(){
        return Images.torpesubm;
    }
}
