import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JPanel implements Runnable{

    int screenWidth = 1920;
    int screenHeight = 1080;
    BufferedImage schienePNG = null;
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
        try {
            schienePNG = ImageIO.read(new File("Schiene.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        g.drawString(Main.halteart+": "+Main.station+"; Gleise: "+Main.gleise,10,30);
        drawDestinationStrings(g);
        drawGleise(g);
        g2.dispose();//male
    }

    private void drawGleise(Graphics g) {
        //tiles zeichnen
    }

    private void drawDestinationStrings(Graphics g) {
        switch(Main.destinationStations.length){
            case 2:
                g.drawString(Main.destinationStations[0],20,screenHeight/2);
                g.drawString(Main.destinationStations[1], screenWidth-50, screenHeight/2);
                break;
            case 3:
                g.drawString(Main.destinationStations[0],20,screenHeight/2);
                g.drawString(Main.destinationStations[1],screenWidth/2,20);
                g.drawString(Main.destinationStations[2], screenWidth-50, screenHeight/2);
                break;
            case 4:
                g.drawString(Main.destinationStations[0],20,screenHeight/2);
                g.drawString(Main.destinationStations[1],screenWidth/2,20);
                g.drawString(Main.destinationStations[2], screenWidth-50, screenHeight/2);
                g.drawString(Main.destinationStations[3],screenWidth/2,screenHeight-50);
                break;
            default:
                break;
        }
    }
}
