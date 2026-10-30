package cli;
import models.*;
import repository.InMemoryDataStore;
import services.*;
import payment.*;
import java.time.LocalDate;
import java.util.*;
public class ConsoleUI {
    private final InMemoryDataStore store; private final SearchService search; private final BookingService bookings;
    private final PaymentService payments; private final ReviewService reviews; private final Scanner sc=new Scanner(System.in);
    public ConsoleUI(InMemoryDataStore store,SearchService search,BookingService bookings,PaymentService payments,ReviewService reviews){
        this.store=store;this.search=search;this.bookings=bookings;this.payments=payments;this.reviews=reviews;
    }
    public void run(){
        while(true){
            System.out.println("\n=== HOTEL BOOKING SYSTEM ===");
            System.out.println("1. List hotels  2. Search rooms  3. Create booking  4. Pay for booking");
            System.out.println("5. Check in  6. Check out  7. Cancel booking  8. Add review  0. Exit");
            System.out.print("Choose: ");String choice=sc.nextLine().trim();
            try{
                switch(choice){
                    case "1" -> store.hotels.values().forEach(System.out::println);
                    case "2" -> doSearch();
                    case "3" -> doBooking();
                    case "4" -> doPayment();
                    case "5" -> {System.out.print("Booking ID: ");bookings.checkIn(sc.nextLine().trim(),true);System.out.println("Checked in (demo assumes ID verified).");}
                    case "6" -> {System.out.print("Booking ID: ");bookings.checkOut(sc.nextLine().trim());System.out.println("Checked out.");}
                    case "7" -> {System.out.print("Booking ID: ");bookings.cancel(sc.nextLine().trim(),payments);System.out.println("Cancelled.");}
                    case "8" -> doReview();
                    case "0" -> {System.out.println("Goodbye!");return;}
                    default -> System.out.println("Choose a valid option.");
                }
            }catch(Exception e){System.out.println("Error: "+e.getMessage());}
        }
    }
    private void doSearch(){
        System.out.print("City: ");String city=sc.nextLine().trim();
        System.out.print("Check-in (YYYY-MM-DD): ");LocalDate in=LocalDate.parse(sc.nextLine().trim());
        System.out.print("Check-out (YYYY-MM-DD): ");LocalDate out=LocalDate.parse(sc.nextLine().trim());
        System.out.print("Guests: ");int n=Integer.parseInt(sc.nextLine().trim());
        search.search(city,in,out,n).forEach(r->System.out.println(r.getId()+" | "+store.hotels.get(r.getHotelId()).getName()+" | "+r));
    }
    private void doBooking(){
        System.out.print("Guest ID (demo: G1): ");String guest=sc.nextLine().trim();
        System.out.print("Room IDs separated by commas (example R1,R2): ");List<String> ids=Arrays.stream(sc.nextLine().split(",")).map(String::trim).toList();
        System.out.print("Check-in (YYYY-MM-DD): ");LocalDate in=LocalDate.parse(sc.nextLine().trim());
        System.out.print("Check-out (YYYY-MM-DD): ");LocalDate out=LocalDate.parse(sc.nextLine().trim());
        Booking b=bookings.create(guest,ids,in,out);System.out.println("Created "+b.getId()+"; amount ₹"+b.getTotalAmount()+". Status: "+b.getStatus());
    }
    private void doPayment(){
        System.out.print("Booking ID: ");Booking b=store.bookings.get(sc.nextLine().trim());if(b==null)throw new IllegalArgumentException("Booking not found");
        System.out.print("Payment method (1 UPI, 2 Card, 3 Pay at Hotel): ");String c=sc.nextLine().trim();
        PaymentMethod method=switch(c){case "1"->new UpiPayment();case "2"->new CardPayment();case "3"->new PayAtHotelPayment();default->throw new IllegalArgumentException("Invalid method");};
        Payment p=payments.process(b,method);System.out.println("Payment "+p.getStatus()+"; booking status "+b.getStatus());
    }
    private void doReview(){
        System.out.print("Booking ID: ");String id=sc.nextLine().trim();System.out.print("Rating (1-5): ");int rating=Integer.parseInt(sc.nextLine().trim());
        System.out.print("Comment (optional): ");String comment=sc.nextLine();Review r=reviews.add(id,rating,comment);System.out.println("Review saved: "+r.getId());
    }
}
