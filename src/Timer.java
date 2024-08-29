import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Timer extends Thread{
    int sekunden = 0;
    int minuten = 0;
    int stunden = 0;
    int tage = 0;
    @Override
    public void run(){
        while(true){
            String request;
            try {
                request = Main.comTask.sendMessage("GET:TIME");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] time = request.split(":");
            if(time.length != 0){
                tage = parseInt(time[0]);
                stunden = parseInt(time[1]);
                minuten = parseInt(time[2]);
                sekunden = parseInt(time[3]);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public String getTime(){
        return tage+":"+stunden+":"+minuten+":"+sekunden;
    }
}
