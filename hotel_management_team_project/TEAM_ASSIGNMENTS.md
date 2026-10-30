# Team assignments

1. **Team leader / Member 1 — Integration**
   - Own `app/Main.java`, `cli/ConsoleUI.java`, `repository/InMemoryDataStore.java`, README and integration.
   - Keep exactly one `Main.java`.
2. **Member 2 — Hotel, room and maintenance**
   - Own `models/Hotel.java`, room subclasses, `models/MaintenanceBlock.java`, `services/HotelService.java`.
3. **Member 3 — Search and availability**
   - Own `services/SearchService.java`; coordinate changes to room/booking models before editing shared interfaces.
4. **Member 4 — Booking lifecycle**
   - Own `models/Booking.java`, `services/BookingService.java`.
5. **Member 5 — Pricing, payment, reviews and SQL**
   - Own payment classes, `services/PricingService.java`, `services/PaymentService.java`, review classes/services and `sql/`.

## Branch suggestions
- `feature/hotel-room-management`
- `feature/search-availability`
- `feature/booking-management`
- `feature/payment-review-sql`

Before merging, each member should pull the latest `main`, run `mvn compile`, test their feature, and create a pull request.
