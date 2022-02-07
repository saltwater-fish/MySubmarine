package cn.tedu.submarine;

import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.Graphics;

public abstract class SeaObject {
    public static final int LIVE = 0;
    public static final int DEAD = 1;
    protected int state = LIVE;

    protected int width;  //宽
    protected int height;  //高
    protected int x;      //x坐标
    protected int y;      //y坐标
    protected int speed;  //速度

    /**专门给三种潜艇准备的构造方法*/
    public SeaObject(int width, int height){
        this.width = width;
        this.height = height;
        x = -width;//负的潜艇的宽
        Random rand = new Random();//随机数对象
        y = rand.nextInt(World.HEIGHT-height-150+1)+150;//150到（窗口高-潜艇高）之内的随机数
        speed = rand.nextInt(2)+1;//1到3
    }

    /** 专门给战舰、水雷、深水炸弹提供的构造方法*/
    public SeaObject(int width,int height,int x,int y,int speed){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    /**移动*/
    public abstract void move();

    /** 获取对象图片 */
    public abstract ImageIcon getImage();

    /** 判断对象状态*/
    public boolean isLive(){
        return state==LIVE;
    }

    /** 判断对象状态*/
    public boolean isDead(){
        return state==DEAD;
    }

    public void paintImage(Graphics g){
        if (isLive()){
            this.getImage().paintIcon(null,g,this.x,this.y);
        }
    }

    /** 检测潜艇越界 */
    public boolean isOutOfBounds(){
        return x>=World.WIDTH; //潜艇越界
    }

    /** 碰撞检测this:当前对象other:其他对象 */
    public boolean isHit(SeaObject other){
        //this指代潜艇，other指代炸弹
        int x1 = this.x-other.width;
        int x2 = this.x+this.width;
        int y1 = this.y-other.height;
        int y2 = this.y+this.height;
        int x = other.x;
        int y = other.y;

        return x>=x1 && x<=x2 && y>=y1 && y<=y2;
    }

    /** 海洋对象去死 */
    public void goDead(){
        state = DEAD;//将对象状态改为Dead
    }
}














