import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Negociator extends Agent {

    private final double PRICE_AUGMENTATION_COEF = 0.25;

    private List<Supplier> suppliers;
    private HashMap<Supplier, Negociation> negociations;
    private Ticket targetTicket;
    private double maxCost;

    public Negociator(String id, Ticket targetTicket, Supplier... supplier) {
        super(id);
        this.targetTicket = targetTicket;
        this.maxCost = 1.1 * targetTicket.getCost();
        this.suppliers = new ArrayList<>();
        this.negociations = new HashMap<>();

        for(int i = 0; i < supplier.length; i++) {
            suppliers.add(supplier[i]);
        }
    }

    public Ticket getTargetTicket() {
        return targetTicket;
    }

    public void run(){

        for(Supplier s : suppliers) {
            negociations.put(s, new Negociation(this, s));
        }

        while(true) {

            Message msg = null;
            Supplier supplier = null;

            if (messages.size() > 0){
                msg = messages.remove(0);
                sleep();
                supplier = (Supplier) msg.getSender();
            }

            if (msg != null && negociations.get(supplier).isProcessing()){

                Ticket suggestedTicket = msg.getTicket();
                Negociation currentNegociation = negociations.get(supplier);

                if(msg.isRefusingToContinue() || currentNegociation.isRunningOutOfTime()) {
                    //stop the current negociation
                    currentNegociation.stop();
                    break;
                }

                if(msg.isCommercialPurchase()) {
                    //stop all negociations
                    stopAllNegociationsExcept(null);
                    break;
                }

                if(suggestedTicket.equals(targetTicket) || suggestedTicket.getCost() < maxCost) {
                    //Si la suggestion est parfaite, on termine la négo avec les autres et on achète
                    System.out.println(getIdAgent() + " ACCEPT THE PROPOSITION : " + suggestedTicket.toString()
                            + " => " + suggestedTicket.getCost() );

                    stopAllNegociationsExcept(supplier);
                    (new Message(this, supplier, suggestedTicket, true)).send();

                } else {
                    //sinon annonce qu'on augmente notre prix pour la négociation courante uniquement
                    currentNegociation.setTargetPrice(targetTicket.getCost() +
                            PRICE_AUGMENTATION_COEF * (suggestedTicket.getCost() - targetTicket.getCost())
                    );
                    currentNegociation.sendNewPropositionFromNegToSup();
                }
            }
            sleep();
        }
    }

    /***
     * Make the thread wait for a moment
     */
    public void sleep() {
        try {
            sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * Stop all negociations, except a specific one.
     * If s is null, stop all negociations
     * @param s
     */
    public void stopAllNegociationsExcept(Supplier s) {
        for(Map.Entry<Supplier, Negociation> e : negociations.entrySet()) {

            if(s == null || ! e.getKey().equals(s)) {
                e.getValue().stop();
            }
        }
    }
}
