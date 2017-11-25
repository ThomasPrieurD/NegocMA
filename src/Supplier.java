import java.util.ArrayList;
import java.util.List;

public class Supplier extends Agent {

    private List<Ticket> tickets;
    private List<Negociator> negociators;
    private Negociator negociator; //todo remove ??
    private Negociation negociation;

    public Supplier(String id, List<Ticket> tickets) {
        super(id);
        this.tickets = tickets;
        this.negociators = new ArrayList<Negociator>();
    }

    public Supplier(String id, List<Ticket> tickets, Negociator negociator) {
        super(id);
        this.tickets = tickets;
        this.negociator = negociator;
        this.negociators = new ArrayList<Negociator>();
    }

    public void startNegociation(Negociation n) {
        negociation = n;
    }

    public void run(){
        Message msg;
        while(true){

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            msg = null;
            if (messages.size() > 0){
                msg = messages.remove(0);
                System.out.println("Sup read message from : " + msg.getSender().getIdAgent());
            }

            if (msg != null){

                if (negociator == null){
                    this.negociator = (Negociator)msg.getSender();
                }

                if(msg.isCommercialPurchase()) {
                    //todo : vérifier que le ticket est toujours dispo et le vendre
                    if(msg.getTicket().isSold()) {
                        //retourner erreur
                    } else {
                        System.out.println(msg.getTicket().print() + " IS SOLD");
                        msg.getTicket().sold();
                        (new Message(this, negociator, msg.getTicket(), true)).send();
                        break;
                    }
                } else {

                    Ticket bestTicket = getBestTicket(msg.getTicket());
                    addSuggestionFor(negociator, bestTicket);
                    (new Message(this, negociator,bestTicket, false)).send();

                }
            }

        }
    }
    // récupère le meilleur ticket n'ayant pas été suggéré
    public Ticket getBestTicket(Ticket targetTicket){
        Ticket bestTicket = null;
        int bestDiff = Integer.MAX_VALUE;
        int diff;

        for (Ticket t : tickets){
            if (suggestedTickets.get(negociator) == null || !suggestedTickets.get(negociator).contains(t)){
                diff = ticketDifferences(t, targetTicket);
                if (diff < bestDiff){
                    bestDiff = diff;
                    bestTicket = t;
                }
            }
        }
        return bestTicket;
    }

    //renvoie le nombre de différences entre 2 tickets
    public int ticketDifferences(Ticket t, Ticket targetTicket){
        int diff = 0;
        if (t.getTravelDate() != targetTicket.getTravelDate()){
            diff ++;
        }
        if (t.getDepartureLocation() != targetTicket.getDepartureLocation()){
            diff += 5;
        }
        if (t.getArrivalLocation() != targetTicket.getArrivalLocation()){
            diff += 5;
        }
        if (t.getCompany() != targetTicket.getCompany()){
            diff ++;
        }
        //diférence si prix ticket Four >> prix ticket Négo
        if (t.getCost() > 1.2 * targetTicket.getCost()){
            diff += 0.5;
        }
        return diff;
    }
}
