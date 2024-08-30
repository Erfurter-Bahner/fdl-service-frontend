import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AnnualInformation implements Runnable{
    public static TrainEvent informations[] = {};
    public void start(){
        Thread t1 = new Thread(this);
        t1.start();
    }
    @Override
    public void run(){
        while(true){
            try {
                String info = Main.comTask.sendMessage("GET:INFORMATION:"+Main.station);
                processinformation(info);
                Thread.sleep(1000); // holt alle 2 sekunden Informationen
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // 589;commandhere
    // sekunden;command
    private void processinformation(String info) {
        String split[] = info.split(";");
        int time = parseInt(split[0]);
        String command = split[1];

        if(informations[informations.length-1].command.equals(command) && informations[informations.length-1].sekunden == time) return;
        TrainEvent newinformations[] = new TrainEvent[informations.length+1];
        for(int i = 0; i < informations.length;i++){
            newinformations[i] = informations[i];
        }
        TrainEvent newEvent = new TrainEvent(command, time);
        newinformations[newinformations.length-1] = newEvent;
        informations = newinformations;
    }
    private int getLatestEventSeconds(){
        return informations[informations.length-1].sekunden;
    }
    private String getLatestEventCommandAndRemove(){
        String command = informations[informations.length-1].command;
        TrainEvent newinformations[] = new TrainEvent[informations.length-1];
        for(int i = 0; i < newinformations.length;i++){
            newinformations[i] = informations[i];
        }
        informations = newinformations;
        return command;
    }
}
