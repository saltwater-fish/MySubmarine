package cn.tedu.submarine;

import javax.swing.*;


//战舰
public class Battleship extends SeaObject {
    /*
    * 宽、高、x坐标、y坐标、命数、移动速度
    * 都为int类型
    * */

    private int life;   //命数

    /** 构造方法*/
    public Battleship(){           //super:超类对象        //this:当前对象
        super(66,26,270,124,20);
        life = 5;
    }

    /**重写move()移动*/
    public void move(){
        //暂时搁置
    }

    /**重写 */
    public ImageIcon getImage(){
        return Images.battleship;
    }

    /** 战舰生成深水炸弹对象 */
    public Bomb shoot(){
        return new Bomb(this.x,this.y); //深水炸弹的坐标就是战舰的坐标
    }

    public void moveLeft(){
        x -= speed;
    }

    public void moveRight(){
        x += speed;
    }

    /** 战舰增命 */
    public void addLife(int num){
        life += num;
    }

    /** 获取命数 */
    public int getLife(){
        return life;
    }

    /** 战舰减命 */
    public void subtractLife(){
        life--;//命数减1
    }
}
