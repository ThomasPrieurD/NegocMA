public class Negociator extends Agent {

    private final double PRICE_AUGMENTATION_COEF = 0.25;

    //private List<Supplier> suppliers;
    private Supplier supplier;
    private Ticket targetTicket;
    private double maxCost;

    public Negociator(String id, Supplier supplier, Ticket targetTicket) {
        super(id);
        this.supplier = supplier;
        this.targetTicket = targetTicket;
        this.maxCost = 1.1 * targetTicket.getCost();
    }

    public Ticket getTargetTicket() {
        return targetTicket;
    }

    public void run(){

        //for each supplier
        //Demande à chaque fournisseur qui possède le bon billet
        Negociation negociation = new Negociation(this, supplier);

        while(true) {

            Message msg = null;

            if (messages.size() > 0){
                msg = messages.remove(0);
            }

            if (msg != null){

                Ticket suggestedTicket = msg.getTicket();

                if(msg.isRefusingToContinue() || msg.isCommercialPurchase() || negociation.isRunningOutOfTime()) {
                    //stop all negociations ???
                    negociation.stop();
                    break;
                }

                if(suggestedTicket.equals(targetTicket) || suggestedTicket.getCost() < maxCost) {
                    //Si la suggestion est parfaite, on termine la négo et on achète
                    (new Message(this, supplier, suggestedTicket, true)).send();

                } else {
                    //sinon annonce qu'on augmente notre prix
                    targetTicket.setCost(targetTicket.getCost() +
                            PRICE_AUGMENTATION_COEF * (suggestedTicket.getCost() - targetTicket.getCost())
                    );
                    negociation.sendNewPropositionFromNegToSup();
                }
            }

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
