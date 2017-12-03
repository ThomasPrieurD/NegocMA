import java.util.Date;

public class Ticket {

    private Date travelDate;
    private String departureLocation;
    private String arrivalLocation;
    private double cost;
    private boolean isSold;
    private String company;

    public Ticket(Date travelDate, String departureLocation, String arrivalLocation, double cost, String company) {
        this.travelDate = travelDate;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.cost = cost;
        this.company = company;
        this.isSold = false;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public boolean isSold() {
        return isSold;
    }

    public void sold() {
        isSold = true;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public double getCost() {
        return cost;
    }

    public String getCompany() {
        return company;
    }

    public String print(){
        return "[ date: "+ travelDate +
                " | from: " + departureLocation +
                " | to: "+ arrivalLocation +
                " | cost: " + cost + " ]";
    }

    public boolean equals(Ticket t) {
        return getTravelDate().equals(t.getTravelDate())
                && getDepartureLocation().equals(t.getDepartureLocation())
                && getArrivalLocation().equals(t.getArrivalLocation())
                && getCost() == t.getCost();
    }
}
