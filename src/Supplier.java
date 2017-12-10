import java.util.ArrayList;
import java.util.List;

public class Supplier extends Agent {

    private List<Ticket> tickets;
    private Negociator negociator;
    private Negociation negociation;

    public Supplier(String id, List<Ticket> tickets) {
        super(id);
        this.tickets = tickets;
    }

    public void run(){

        while(true){

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = null;

            if (messages.size() > 0){
                msg = messages.remove(0);
            }

            if (msg != null){

                if (negociator == null){
                    this.negociator = (Negociator)msg.getSender();
                }

                if(msg.isCommercialPurchase()) {

                    if(msg.getTicket().isSold()) {

                        System.out.println("IMPOSSIBLE DE VENDRE LE TICKET => IL EST DEJA VENDU !");

                    } else {

                        System.out.println(msg.getTicket().toString()
                                + " IS SOLD at " + msg.getTicket().getCost());

                        msg.getTicket().sold();
                        (new Message(this, negociator, msg.getTicket(), true)).send();
                        break;
                    }
                } else {

                    Ticket bestTicket = getBestTicket(msg.getTicket());
                    negociation.addSuggestion(bestTicket);
                }
            }

        }
    }

    /***
     * Get the best ticket matching the targetTicket
     * @param targetTicket
     * @return
     */
    public Ticket getBestTicket(Ticket targetTicket){

        List<Ticket> suggestedTickets = negociation.getSuggestedTickets();
        Ticket bestTicket = null;
        int bestDiff = Integer.MAX_VALUE;
        int diff;

        for (Ticket t : tickets){

            //Si le ticket correspond en terme de lieux
            if(t.getArrivalLocation().equals(targetTicket.getArrivalLocation())
                    && t.getDepartureLocation().equals(targetTicket.getDepartureLocation())) {

                //Si on n'a proposé aucun ticket OU qu'on n'a pas encore proposé le ticket courant
                if (suggestedTickets.size() == 0 || !suggestedTickets.contains(t)) {

                    diff = ticketDifferences(t, targetTicket);

                    if (diff < bestDiff) {
                        bestDiff = diff;
                        bestTicket = t;
                    }
                }
            }
        }
        return bestTicket;
    }

    /***
     * Return a value to indicate how different a ticket is from another one.
     * Plus on s'approche de 0 plus les tickets sont identiques. Plus on s'éloigne, plus ils sont différents.
     * @param t
     * @param targetTicket
     * @return
     */
    public int ticketDifferences(Ticket t, Ticket targetTicket){
        int diff = 0;
        if (t.getTravelDate() != targetTicket.getTravelDate()){
            diff ++;
        }
        if (t.getCompany() != targetTicket.getCompany()){
            diff ++;
        }
        //On propose toujours le ticket le plus cher en premier !
        diff += (negociation.getTargetPrice() - t.getCost());
        return diff;
    }

    /***
     * Get the negociation to focus on
     * @param n
     */
    public void startNegociation(Negociation n) {
        negociation = n;
    }
}
