# Hotel Booking System — Java LLD Team Project

A console-based hotel booking system using Java OOP and in-memory collections. No database connection is required.

## Requirements
- Java 17+
- Maven (optional but recommended)

## Run
From this project folder:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="app.Main"
```
If the Maven exec plugin is not installed/configured, run `app.Main` from VS Code's Java extension after opening this folder.

## Team module split
- **Member 1 / Team leader:** `app.Main`, `cli`, `repository`, integration, documentation.
- **Member 2:** `services.HotelService`, `models.Hotel`, `models.Room` subclasses, `models.MaintenanceBlock`.
- **Member 3:** `services.SearchService`.
- **Member 4:** `models.Booking`, `services.BookingService`.
- **Member 5:** `models.Payment`, `payment`, `services.PricingService`, `services.PaymentService`, `models.Review`, `services.ReviewService`, `sql/`.

## Important collaboration rule
This is one shared repository and one Java project. Do not create a separate project or another `Main.java` for each member. Members should work on their assigned branch and open a pull request to `main`.

## Notes
- Data is stored in `InMemoryDataStore`; restarting the program resets runtime-created records.
- This is an educational LLD implementation, not a production payment or booking service.
