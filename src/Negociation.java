import java.util.ArrayList;
import java.util.List;

/***
 * A Negociation only concern a supplier with a negociator
 */
public class Negociation {

    private final int NB_MAX_PROPOSITIONS = 3;

    private Negociator negociator;
    private Supplier supplier;

    private List<Message> messageHistory;
    private List<Ticket> suggestedTickets;
    private int nbSuggestions;

    public Negociation(Negociator n, Supplier s) {

        System.out.println("------------------------------------------------------------------");
        System.out.println(n.getIdAgent() + " start a negociation with " + s.getIdAgent());

        negociator = n;
        supplier = s;
        nbSuggestions = 0;
        messageHistory = new ArrayList<>();
        suggestedTickets = new ArrayList<>();

        sendNewPropositionFromNegToSup();

        supplier.startNegociation(this);
        supplier.start();
    }

    public List<Ticket> getSuggestedTickets() {
        return suggestedTickets;
    }

    /***
     * Add a suggested Ticket from the supplier
     * @param t
     */
    public void addSuggestion(Ticket t) {

        if(t == null) {
            Message m = (new Message(supplier, negociator, t, false));
            m.refuseToContinue();
            m.send();

        } else {
            suggestedTickets.add(t);
            nbSuggestions++;
            (new Message(supplier, negociator, t, false)).send();
        }
    }

    /***
     * Stop the negociation
     */
    public void stop() {
        System.out.println("Negociation between "
                + negociator.getIdAgent() + " and "
                + supplier.getIdAgent() + " is over !");
        System.out.println("------------------------------------------------------------------");
    }

    /***
     * Send a new desire to the supplier
     */
    public void sendNewPropositionFromNegToSup() {
        Message m = new Message(negociator, supplier, negociator.getTargetTicket(), false);
        m.send();
        messageHistory.add(m);
    }

    /***
     * Check if the negociation can continue or not
     * @return
     */
    public boolean isRunningOutOfTime() {
        return nbSuggestions >= NB_MAX_PROPOSITIONS;
    }
}
