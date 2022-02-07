package cn.tedu.submarine;

import javax.swing.*;

//深水炸弹
public class Bomb extends SeaObject {

    public Bomb(int x, int y){
        super(9,12,x,y,3);
    }

    /**深水炸弹移动*/
    public void move(){
        y += speed;
    }

    /**重写getImage()方法 */
    public ImageIcon getImage(){
        return Images.bomb;
    }

    /** 检测深水炸弹越界 */
    public boolean isOutOfBounds(){
        return y>=World.HEIGHT; //深水炸弹越界
    }
}
