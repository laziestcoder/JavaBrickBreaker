package brickbreaker;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private  boolean play = false;
    private  int score = 0;

    private  int totalBricks =21 ;

    private Timer time;
    private  int delay = 8;

    private  int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator map;


    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer (delay, this);
        time.start();
    }


    public void paint(Graphics g){
        //background

        g.setColor(Color.black);
        g.fillRect(1,1,692,592);


        // drawing map

        map.draw((Graphics2D)g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //the paddle

        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);


        //Scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);


        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        if(totalBricks <= 0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You won! Scores: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);

        }


        if(ballPosY>575){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over! Scores: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart",230,350);
        }
        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if(play){
            if(new Rectangle(ballPosX, ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYDir = -ballYDir;
            }

          A:  for(int i=0; i<map.map.length;i++){
                for(int j = 0; j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 80;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX,ballPosY,20,20);
                        Rectangle brickRect = rect;
                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score += 5;
                            if(ballPosX +19 <=brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXDir =  -ballXDir;
                            }
                            else{
                                ballYDir = -ballYDir;
                            }

                            break A;
                        }
                    }
                }

            }


            ballPosX += ballXDir;
            ballPosY += ballYDir;

            if(ballPosX < 0){
                ballXDir = -ballXDir;
            }

            if(ballPosY < 0){
                ballYDir = -ballYDir;
            }

            if(ballPosX > 670){
                ballXDir = -ballXDir;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT ) {
            if(playerX >= 590){
                playerX = 590;
            }
            else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT ) {
            if(playerX <= 5){
                playerX = 5;
            }
            else{
                moveLeft();
            }
        }

        if(e.getKeyCode()== KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();

            }
        }
    }

    public void moveRight(){
        play = true;
        playerX +=20;
    }
    public void moveLeft(){
        play = true;
        playerX -=20;
    }




}
