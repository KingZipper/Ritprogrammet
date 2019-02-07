import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI klassen

public class Gui {

   private JButton clearButton, blackButton, redButton;
   private WhiteBoard ritgui;

        //Actionlyssnare som lyssnar på GUI knapparna
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (e.getSource() == clearButton){
              ritgui.clear();
          } else if (e.getSource() == blackButton){
              ritgui.black();
          } else if (e.getSource() == redButton){
              ritgui.red();
          }
        }
    };

    //Ritar upp GUI:t
    public void show(String clientPort, String hostName, String hostPort){
        JFrame frame = new JFrame("RITPROGRAMMET");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        ritgui = new WhiteBoard(clientPort, hostName, hostPort);
        content.add(ritgui, BorderLayout.CENTER);
        JPanel controls = new JPanel();
        clearButton = new JButton("Clear");
        clearButton.addActionListener(actionListener);
        blackButton = new JButton("Black");
        blackButton.addActionListener(actionListener);
        redButton = new JButton("Red");
        redButton.addActionListener(actionListener);

        controls.add(redButton);
        controls.add(blackButton);
        controls.add(clearButton);

        content.add(controls, BorderLayout.NORTH);
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    //Main metod
    public static void main(String [] args){
        String clientPort = "2001";
        String hostName = "127.0.0.1";
        String hostPort = "2000";

        if (args.length > 0) {
            if(args[0] != null) {
                clientPort = args[1];
            }
            if(args[1] != null ) {
                hostName = args[0];
            }
            if (args[2] != null){
                hostPort = args[2];
            }
        }

        new Gui().show(clientPort, hostName, hostPort);
    }
}

