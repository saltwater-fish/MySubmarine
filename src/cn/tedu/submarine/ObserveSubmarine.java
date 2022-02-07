package cn.tedu.submarine;

import javax.swing.*;
import java.util.Random;

//侦察潜艇
public class ObserveSubmarine extends SeaObject implements EnemyScore {

    public int getScore(){
        return 10;//打掉侦察潜艇，玩家得10分
    }

    public ObserveSubmarine(){
        super(63,19);
    }

    /**移动*/
    public void move(){
        x += speed;
    }

    /**重写 */
    public ImageIcon getImage(){
        return Images.obsersubm;
    }
}
