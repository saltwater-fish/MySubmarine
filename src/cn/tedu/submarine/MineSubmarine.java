package cn.tedu.submarine;

import javax.swing.*;

//水雷潜艇
public class MineSubmarine extends SeaObject implements EnemyLife {
    /** 得命 */
    public int getLife(){
        return 1;
    }

    public MineSubmarine(){
        super(63,19);
    }

    /**水雷潜艇移动*/
    public void move(){
        x += speed;
    }

    /**重写getImage()方法 */
    public ImageIcon getImage(){
        return Images.minesubm;
    }

    /** 水雷潜艇发射水雷 */
    public Mine shootMine(){
        int x = this.x+this.width;
        int y = this.y-11;
        return new Mine(x,y);
    }

}
