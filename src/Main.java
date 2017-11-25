import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args){

        ArrayList<Ticket> tickets = new ArrayList<>();
        Ticket tick1 = new Ticket(new Date(2017,12,24),"Montréal", "Grenoble",450,"AirCanada");
        tickets.add(tick1);
        Ticket tick2 = new Ticket(new Date(2017,11,24),"Montréal", "Grenoble",250,"AirCanada");
        tickets.add(tick2);
        Ticket tick3 = new Ticket(new Date(2017,12,24),"Montréal", "Lyon",750,"AirCanada");
        tickets.add(tick3);

        Supplier supp = new Supplier("Sup1", tickets);
        Negociator nego = new Negociator("Neg1", supp, tick3);

        nego.start();


    }
}
