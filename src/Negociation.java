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

    private double targetPrice;
    private int nbSuggestions;
    private boolean processing;

    public Negociation(Negociator n, Supplier s) {

        System.out.println("------------------------------------------------------------------");
        System.out.println(n.getIdAgent() + " start a negociation with " + s.getIdAgent());

        negociator = n;
        supplier = s;
        nbSuggestions = 0;
        processing = true;
        targetPrice = n.getTargetTicket().getCost();

        messageHistory = new ArrayList<>();
        suggestedTickets = new ArrayList<>();

        sendNewPropositionFromNegToSup();

        supplier.startNegociation(this);
        supplier.start();
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public boolean isProcessing() {
        return processing;
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
        System.out.println(supplier.getIdAgent() +
                " to " + negociator.getIdAgent() + " : " + t.toString() +
                " => " + t.getCost() + " € " +
                " for purchasing ? " + false);
    }

    /***
     * Stop the negociation
     */
    public void stop() {

        if(processing) {

            System.out.println("Negociation between "
                    + negociator.getIdAgent() + " and "
                    + supplier.getIdAgent() + " is over !");
            System.out.println("------------------------------------------------------------------");

            supplier.stop();

            processing = false;
        }
    }

    /***
     * Send a new desire to the supplier
     */
    public void sendNewPropositionFromNegToSup() {
        System.out.println(negociator.getIdAgent() +
                " to " + supplier.getIdAgent() + " : " + negociator.getTargetTicket().toString() +
                " => " + getTargetPrice() + " € " +
                " for purchasing ? " + false);
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
