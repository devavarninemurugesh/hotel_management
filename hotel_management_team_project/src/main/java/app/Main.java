package app;
import cli.ConsoleUI;
import repository.InMemoryDataStore;
import services.*;
public class Main {
    public static void main(String[] args){
        InMemoryDataStore store=new InMemoryDataStore();
        SearchService search=new SearchService(store);
        PricingService pricing=new PricingService();
        PaymentService payments=new PaymentService(store);
        BookingService bookings=new BookingService(store,search,pricing);
        ReviewService reviews=new ReviewService(store);
        new ConsoleUI(store,search,bookings,payments,reviews).run();
    }
}
