package brickbreaker;
import javax.swing.JFrame;

public class Main {
    public  static void main(String[] arg ){
        JFrame obj = new JFrame();
        Gameplay gamePlay = new Gameplay();

        obj.setBounds (10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);



    }

}
