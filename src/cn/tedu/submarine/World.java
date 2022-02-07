package cn.tedu.submarine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** 整个游戏世界*/
public class World extends JPanel {
    public static final int WIDTH = 641;    //界面的宽
    public static final int HEIGHT = 479;   //界面的高

    public static final int RUNNING = 0;    //运行状态
    public static final int GAME_OVER = 1;  //游戏结束状态
    private int state = RUNNING;            //游戏状态

    private Battleship ship = new Battleship();
    private SeaObject[] submarines = {};
    private Mine[] mines = {};
    private Bomb[] bombs = {};

    /** 生成潜艇(侦察潜艇、鱼雷潜艇、水雷潜艇) */
    public SeaObject nextSubmarine(){
        Random rand = new Random();//随机数对象
        int type = rand.nextInt(20);//0到19
        if(type<10){//0到9时，返回侦察潜艇对象
            return new ObserveSubmarine();
        }else if (type<15){//10到14，返回鱼雷潜艇对象
            return new TorpedoSubmarine();
        }else {//15到19，返回水雷潜艇对象
            return new MineSubmarine();
        }
    }

    private int subEnterIndex = 0;
    public void submarineEnterAction(){ //每10毫秒走一次
        subEnterIndex++;
        if (subEnterIndex%40==0){ //每400毫秒走一次
            SeaObject obj = nextSubmarine();
            submarines = Arrays.copyOf(submarines,submarines.length+1);
            submarines[submarines.length-1] = obj;
        }
    }

    private int mineEnterIndex = 0;
    public void mineEnterAction(){
        mineEnterIndex++;
        if (mineEnterIndex%100==0){
            for(int i=0;i<submarines.length;i++){
                if (submarines[i] instanceof MineSubmarine){
                    MineSubmarine ms = (MineSubmarine)submarines[i];
                    Mine obj = ms.shootMine();
                    mines = Arrays.copyOf(mines,mines.length+1);
                    mines[mines.length-1] = obj;
                }
            }
        }
    }


    private int score = 0;      //分数
    /** 海洋对象移动 */
    public void moveAction(){
        for (int i=0;i<submarines.length;i++){
            submarines[i].move();
        }
        for (int i=0;i<mines.length;i++){
            mines[i].move();
        }
        for (int i=0;i<bombs.length;i++){
            bombs[i].move();
        }
    }

    public void outOfBoundsAction(){
        for (int i=0;i<submarines.length;i++){
            if (submarines[i].isOutOfBounds() || submarines[i].isDead()){
                submarines[i] = submarines[submarines.length-1];
                submarines = Arrays.copyOf(submarines,submarines.length-1);
            }
        }
        for (int i=0;i<bombs.length;i++){
            if (bombs[i].isOutOfBounds() || bombs[i].isDead()){
                bombs[i] = bombs[bombs.length-1];
                bombs = Arrays.copyOf(bombs,bombs.length-1);
            }
        }
        for (int i=0;i<mines.length;i++){
            if (mines[i].isOutOfBounds() || mines[i].isDead()){
                mines[i] = mines[mines.length-1];
                mines = Arrays.copyOf(mines,mines.length-1);
            }
        }
    }

    public void bombBangAction(){
        for (int i=0;i<bombs.length;i++){
            Bomb b = bombs[i];
            for (int j=0;j<submarines.length;j++){
                SeaObject s = submarines[j];
                if (b.isLive() && s.isLive() && s.isHit(b)){
                    s.goDead();
                    b.goDead();
                    if (s instanceof EnemyScore){
                        EnemyScore es = (EnemyScore)s;
                        score += es.getScore();
                    }
                    if (s instanceof EnemyLife){
                        EnemyLife ea = (EnemyLife)s;
                        int num = ea.getLife();
                        ship.addLife(num);
                    }
                }
            }
        }
    }

    /** 水雷与战舰的碰撞 */
    public void mineBangAction(){
        for (int i=0;i<mines.length;i++){
            Mine m = mines[i];
            if (m.isLive() && ship.isLive() && m.isHit(ship)){
                m.goDead();
                ship.subtractLife();
            }
        }
    }

    public void checkGameOverAction(){
        if(ship.getLife()<=0){
            state = GAME_OVER;
        }
    }

    /** 启动程序的执行 */
    public void action(){
        KeyAdapter k = new KeyAdapter() {
            @Override
            /** 重写keyReleased()按键抬起事件 */
            public void keyReleased(KeyEvent e) { //当按键抬起时会自动触发
                if (e.getKeyCode()==KeyEvent.VK_SPACE){
                    Bomb obj = ship.shoot();
                    bombs = Arrays.copyOf(bombs,bombs.length+1);
                    bombs[bombs.length-1] = obj;
                }
                if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A){
                    ship.moveLeft();
                }
                if (e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D){
                    ship.moveRight();
                }
            }
        };
        this.addKeyListener(k);


        Timer timer = new Timer();//定时器对象
        int interval = 10; //定时间隔(以毫秒为单位)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                submarineEnterAction();//潜艇入场
                mineEnterAction();//炸弹入场
                moveAction();//海洋对象移动
                outOfBoundsAction();//越界对象处理
                bombBangAction();
                mineBangAction();
                checkGameOverAction();
                repaint();//重画
            }
        }, interval, interval);

    }

    public void paint(Graphics g){
        switch (state){
            case GAME_OVER:
                Images.gameover.paintIcon(null,g,0,0);
                break;
            case RUNNING:
                Images.sea.paintIcon(null,g,0,0);
                ship.paintImage(g);
                for(int i=0;i<submarines.length;i++){
                    submarines[i].paintImage(g);
                }
                for (int i=0;i<mines.length;i++){
                    mines[i].paintImage(g);
                }
                for (int i=0;i<bombs.length;i++){
                    bombs[i].paintImage(g);
                }

                g.drawString("SCORE:"+score,200,50);
                g.drawString("LIFE:"+ship.getLife(),400,50);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+16, HEIGHT+39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        world.action();
    }
}














