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
    public static BufferedImage[] imagearr = null;

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
            BufferedImage nullPNG = ImageIO.read(new File("0.png"));
            BufferedImage onePNG = ImageIO.read(new File("-Schiene.png"));
            BufferedImage twoPNG = ImageIO.read(new File("ISchiene.png"));
            BufferedImage threePNG = ImageIO.read(new File("NordOstWeiche.png"));
            BufferedImage fourPNG = ImageIO.read(new File("NordWestWeiche.png"));
            BufferedImage fivePNG = ImageIO.read(new File("SuedOstWeiche.png"));
            BufferedImage sixPNG = ImageIO.read(new File("SuedWestWeiche.png"));
            imagearr = new BufferedImage[]{
                    nullPNG,
                    onePNG,
                    twoPNG,
                    threePNG,
                    fourPNG,
                    fivePNG,
                    sixPNG
            };
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
        //    westost-gleis: 1
//    nordsüd-gleis: 2
//    nordost-weiche: 3
//    nordwest-weiche: 4
//    südost-weiche: 5
//    südwest-weiche: 6
//      Bahnhofslayout ist in einem 10*10 layout
//      designed. Zahlen geben die Tiles an, welche
//      dann als 2D-Array verschickt werden, um im Frontend
//      gezeichnet werden können.
        if(Main.layout.length==0) return;
        for(int i = 0;i<10;i++){
            for(int j = 0;j<10;j++){
                g.drawImage(imagearr[Main.layout[j][i]],460+i*100,40+j*100,this);
            }
        }
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
