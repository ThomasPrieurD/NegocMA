public class Negociator extends Agent {

    //private List<Supplier> suppliers;
    private Supplier supplier;
    private Ticket targetTicket;

    public Negociator(String id, Supplier supplier, Ticket targetTicket) {
        super(id);
        this.supplier = supplier;
        this.targetTicket = targetTicket;
    }

    public Ticket getTargetTicket() {
        return targetTicket;
    }

    public void run(){

        //for each supplier
        Negociation negociation = new Negociation(this, supplier);
        supplier.startNegociation(negociation);

        //supplier.start();
        Message msg;

        while(true) {

            msg = null;
            if (messages.size() > 0){
                msg = messages.remove(0);
            }

            if (msg != null){

                Ticket suggestedTicket = msg.getTicket();
                negociation.addSuggestion(suggestedTicket);

                if(msg.isCommercialPurchase()) {
                    //supplier.stop();
                    System.out.println("\n\nEND OF PROGRAM");
                    break;
                }

                //Si la suggestion est parfaite, on termine la négo et on achète
                if(suggestedTicket.equals(targetTicket)) {
                    (new Message(this, supplier, suggestedTicket, true)).send();
                }

            }

            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };



}
