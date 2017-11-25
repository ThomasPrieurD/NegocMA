/**
 * Created by Sachouw on 09/10/2017.
 */
public class Message {

    private Agent sender;
    private Agent receiver;
    private Ticket ticket;
    private boolean isCommercialPurchase;


    public Message(Agent sender, Agent receiver, Ticket ticket, boolean isCommercialOffer) {
        this.sender = sender;
        this.receiver = receiver;
        this.ticket = ticket;
        this.isCommercialPurchase = isCommercialOffer;
    }


    public Agent getReceiver() {
        return receiver;
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
        System.out.println(sender.getIdAgent() + " send to " + receiver.getIdAgent() + " : " + ticket.print());
        receiver.sendMessage(this);
    }
}