import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args){

        ArrayList<Ticket> ticketList1 = new ArrayList<>();
        ArrayList<Ticket> ticketList2 = new ArrayList<>();

        Ticket t_MG_45_241217 = new Ticket(new Date(2017,12,24),"Montréal", "Grenoble",450,"Air Canada");
        Ticket t_MG_25_241117 = new Ticket(new Date(2017,11,24),"Montréal", "Grenoble",250,"Air Canada");
        Ticket t_ML_70_241217 = new Ticket(new Date(2017,12,24),"Montréal", "Lyon",700,"Air Canada");
        Ticket t_ML_75_241217 = new Ticket(new Date(2017,12,24),"Montréal", "Lyon",750,"Air Canada");
        Ticket t_ML_58_241217 = new Ticket(new Date(2017,12,24),"Montréal", "Lyon",580,"Swiss Airline");
        Ticket t_ML_55_221217 = new Ticket(new Date(2017,12,22),"Montréal", "Lyon",550,"Air Canada");
        Ticket t_MT_60_221217 = new Ticket(new Date(2017,12,22),"Montréal", "Tokyo",600,"Swiss Airline");
        Ticket t_PL_20_221217 = new Ticket(new Date(2017,12,22),"Paris", "Lyon",200,"Air France");
        Ticket t_GL_80_251217 = new Ticket(new Date(2017,12,25),"Grenoble", "Lyon",800,"Air France");
        Ticket t_GL_75_251217 = new Ticket(new Date(2017,12,25),"Grenoble", "Lyon",750,"Air France");

        Ticket negociatorDesire =  new Ticket(new Date(2017,12,24),"Montréal", "Lyon",550,"Swiss Airline");

        ticketList1.add(t_MG_45_241217);
        ticketList1.add(t_ML_70_241217);
        ticketList1.add(t_ML_58_241217);
        ticketList1.add(t_MT_60_221217);
        ticketList1.add(t_GL_80_251217);

        ticketList2.add(t_MG_25_241117);
        ticketList2.add(t_ML_75_241217);
        ticketList2.add(t_ML_55_221217);
        ticketList2.add(t_PL_20_221217);
        ticketList2.add(t_GL_75_251217);

        Supplier supp = new Supplier("Sup_1", ticketList1);
        Supplier supp2 = new Supplier("Sup_2", ticketList2);

        Negociator nego = new Negociator("Neg_1", negociatorDesire, supp, supp2);

        nego.start();
    }
}
