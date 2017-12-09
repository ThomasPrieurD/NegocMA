/**
 * Created by Sachouw on 09/10/2017.
 */
public class Message {

    private Agent sender;
    private Agent receiver;
    private Ticket ticket;
    private boolean isCommercialPurchase;
    private boolean isRefusingToContinue;


    public Message(Agent sender, Agent receiver, Ticket ticket, boolean isCommercialOffer) {
        this.sender = sender;
        this.receiver = receiver;
        this.ticket = ticket;
        this.isCommercialPurchase = isCommercialOffer;
        this.isRefusingToContinue = false;
    }


    public boolean isRefusingToContinue() {
        return isRefusingToContinue;
    }

    public void refuseToContinue() {
        isRefusingToContinue = true;
    }

    public Agent getSender() {
        return sender;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isCommercialPurchase() {
        return isCommercialPurchase;
    }

    public void send() {

        if(! isRefusingToContinue) {
            System.out.println(sender.getIdAgent() +
                    " to " + receiver.getIdAgent() + " : " + ticket.toString() +
                    " for purchasing ? " + isCommercialPurchase);
        }
        receiver.sendMessage(this);
    }
}