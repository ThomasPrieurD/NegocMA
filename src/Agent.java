import java.util.ArrayList;
import java.util.List;

public abstract class Agent extends Thread {

    List<Message> messages;
    private String idAgent;

    public Agent(String i) {
        this.idAgent = i;
        this.messages = new ArrayList<>();
    }

    public abstract void run();

    public void sendMessage(Message m) {
        messages.add(m);
    }

    public String getIdAgent() {
        return idAgent;
    }
}
