import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//Trådklassen med skicka och ta emot data metoder

public class Connection extends Thread {

    private WhiteBoard whiteBoard;
    private String message;
    private String clientPort;
    private String hostName;
    private String hostPort;

    //Konstruktor
    public Connection(WhiteBoard whiteBoard, String clientPort,String hostName,String hostPort){
        this.whiteBoard = whiteBoard;
        this.clientPort = clientPort;
        this.hostName = hostName;
        this.hostPort = hostPort;
        start();
    }

    //Skickar datan
    public void sendData(){
        try{
            message =  Integer.toString(whiteBoard.getOldX()) + "," + Integer.toString(whiteBoard.getOldY()) + "," + Integer.toString(whiteBoard.getCurrentX())+ "," + Integer.toString(whiteBoard.getCurrentY()) + "," + Boolean.toString(whiteBoard.getBlack());
            byte[] b = message.getBytes();
            InetAddress host = InetAddress.getByName(hostName);
            DatagramPacket request = new DatagramPacket(b,b.length, host, Integer.parseInt(clientPort));
            DatagramSocket s = new DatagramSocket();
            s.send(request);
        }catch(Exception e){
            System.out.println(e);
        }

    }

    //Kör tråden och tar emot datan
    public void run(){
        try {
            DatagramSocket s = new DatagramSocket(Integer.parseInt(hostPort));
            while(true){
            byte [] buffer = new byte[8000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            s.receive(reply);
            String received = new String(reply.getData(), 0, reply.getLength());
            String[] xy = received.split(",");
            int i1 = Integer.valueOf((xy[0]));
            int i2 = Integer.valueOf((xy[1]));
            int i3 = Integer.valueOf((xy[2]));
            int i4 = Integer.valueOf((xy[3]));
            boolean b5 = Boolean.valueOf(xy[4]);
            whiteBoard.drawOnImage(i1,i2,i3,i4,b5);
            System.out.println(i1);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
