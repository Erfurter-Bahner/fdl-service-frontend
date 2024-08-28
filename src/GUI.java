import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI extends JPanel implements Runnable{

    int screenWidth = 1920;
    int screenHeight = 1080;
    public GUI() throws IOException {
//        this.addKeyListener(keyH);
//        this.addMouseListener(MouseH);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }
    public void start(){
        Thread t1 = new Thread(this);
        t1.start();
    }
    @Override
    public void run(){
        //pre start
        while(true){
            try {
                update();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            repaint();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //logik
    public void update() throws IOException {

    }
    //zeichnen
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //ersetzt Bild
        Graphics2D g2 = (Graphics2D) g; //größere Lib
        g.setColor(Color.black);
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.white);
        g.drawString(Main.timer.getTime(),10,10);
        g.drawString(Main.station,10,30);
        g2.dispose();//male
    }
}
