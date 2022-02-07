package cn.tedu.submarine;

import javax.swing.*;

//水雷
public class Mine extends SeaObject {

    public Mine(int x, int y){
        super(11,11,x,y,1);
    }

    /**移动*/
    public void move(){
        y -= speed;
    }

    /**重写 */
    public ImageIcon getImage(){
        return Images.mine;
    }

    /** 检测水雷越界 */
    public boolean isOutOfBounds(){
        return y<=150-height; //水雷越界
    }
}
