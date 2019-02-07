import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


    //Klass för det man ritar på i GUI:t
    public class WhiteBoard extends JComponent {
        private Image image;
        private Graphics2D g2;
        private int currentX, currentY, oldX, oldY;
        private boolean black = true;

    // Get-metod för att kolla om färgen är svart
    public boolean getBlack(){
        return black;
    }

    //Get metod för att kolla nuvarande x värde
    public int getCurrentX(){
        return currentX;
    }

    //Get metod för att kolla nuvarande y värde
    public int getCurrentY(){
        return currentY;
    }

    //Get metod för att kolla gamla x värdet
    public int getOldX(){
        return oldX;
    }

    //Get metod för att kolla gamla y värdet
    public int getOldY(){
        return oldY;
    }

    //Konstruktor
    public WhiteBoard(String clientPort, String hostName, String hostPort){
        setDoubleBuffered(false);
        Connection connection = new Connection(this, clientPort, hostName, hostPort);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        //Muslyssnare
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
               currentX = e.getX();
               currentY = e.getY();

               if(g2 != null){
                   g2.drawLine(oldX, oldY, currentX,currentY);
                   repaint();
                   oldX = currentX;
                   oldY = currentY;
                   connection.sendData();
               }
            }
        });
    }

    // Metod för att rita ut värdena som tas emot från andra programmet
    protected void drawOnImage(int oX, int oY, int cX, int cY, boolean tof){
        if(tof){
            black();
        }else if(!tof){
            red();
        }
        g2.drawLine(oX, oY, cX, cY);
        repaint();
    }

    //Metod för att rita på "whiteboarden"
    protected void paintComponent(Graphics g){
        if(image == null) {
            // image tot draw null == skapa en ny
            image = createImage(getSize().width,getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    //Metod för att "göra rent whiteboarden"
    public void clear(){
        g2.setPaint(Color.white);
        g2.fillRect(0,0,getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    //Metod för att måla med röd färg
    public void red(){
        g2.setPaint(Color.RED);
        black = false;

    }

    // Metod för att måla med svart färg
    public void black(){
    g2.setPaint(Color.BLACK);
    black = true;
    }
}
