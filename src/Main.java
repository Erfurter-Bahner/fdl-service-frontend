import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;


public class Main {
    public static String username;
    public static CommunicationTask comTask;
    public static Timer timer = new Timer();
    public static String stationinfo = "";
    public static String station = "";
    public static int gleise = 0;
    public static String halteart = "";
    public static String[] destinationStations = {};
    public static String rowsString = "";
    public static int[][] layout = {};

    static {
        try {
            comTask = new CommunicationTask("localhost",12345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        username = scanner.nextLine();
        station = comTask.sendMessage("POST:USER="+username);
        JFrame window = new JFrame();
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Fahrdienstleiter");
        GUI gamePanel = new GUI();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
        gamePanel.start();
        timer.start();
        Thread.sleep(100);
        try {
            stationinfo = comTask.sendMessage("GET:STATION;"+Main.station);
            System.out.println(stationinfo);
            processingstationinfo(stationinfo);
        } catch (IOException e) {
            System.out.println("error getting stationinfo");
        }
    }

    private static void processingstationinfo(String stationinfo) {
        String split[] = stationinfo.split(";");
        gleise = parseInt(split[1]);
        destinationStations = split[3].split(",");
        halteart = split[2];
        rowsString = split[4];
        System.out.println(split[4]);
        String[] rowsArray = split[4].split(":");
        int[][] tilelayout = new int[10][10];
        for(int i = 0; i<rowsArray.length;i++){
            String[] currentcells = rowsArray[i].split(",");
            for(int j=0;j< currentcells.length;j++){
                tilelayout[i][j] = parseInt(currentcells[j]);
                System.out.print(" "+currentcells[j]);
            }
            System.out.println();
        }
        layout = tilelayout;
    }
}

class CommunicationTask {
    private Client client;

    CommunicationTask(String adress, int Port) throws IOException {
        client = new Client("localhost", 12345); // Replace with your server address and port
    }
    public String sendMessage(String msg) throws IOException{
        // Example of sending a message
        client.sendMessage(msg);
        // Example of receiving a message
        String response = client.receiveMessage();
        if (response != null) {
            return response;
        } else return "";
    }
}
