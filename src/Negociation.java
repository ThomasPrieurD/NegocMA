import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * A Negociation only concern a supplier with a negociator
 */
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

    public Negociator getNegociator() {
        return negociator;
    }

    public void setNegociator(Negociator negociator) {
        this.negociator = negociator;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    public void setMessageHistory(List<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public List<Ticket> getSuggestedTickets() {
        return suggestedTickets;
    }

    public void addSuggestion(Ticket t) {
        suggestedTickets.add(t);
    }
}
