import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static String username;
    public static CommunicationTask comTask;
    public static Timer timer = new Timer();
    public static String stationinfo = "";
    public static String station;

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
