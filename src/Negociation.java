import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Negociation {

    private Negociator negociator;
    private Supplier supplier;
    private List<Message> messageHistory;
    private Long startTime;
    private List<Ticket> suggestedTickets;

    public Negociation(Negociator n, Supplier s) {
        negociator = n;
        supplier = s;
        messageHistory = new ArrayList<>();
        startTime = System.currentTimeMillis();
        suggestedTickets = new ArrayList<>();

        Message m = new Message(negociator, supplier, negociator.getTargetTicket(), false);
        m.send();
        messageHistory.add(m);

        supplier.start();
    }

    //todo remove ?
    public void addSuggestion(Ticket t) {
        suggestedTickets.add(t);
    }
}
