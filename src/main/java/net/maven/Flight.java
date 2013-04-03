package net.maven;

public class Flight {
    public String flight_number;
    public int tickets_count;
    public String date;

    public Flight(String fl_n, int t_c, String d){
        flight_number=fl_n;
        tickets_count=t_c;
        date = d;
    }

    public String getFlight_number(){
        return flight_number;
    }
    public int getTickets_count(){
        return tickets_count;
    }
    public String getDate(){
        return date;
    }
}
