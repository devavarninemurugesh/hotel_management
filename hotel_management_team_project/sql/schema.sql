-- Reference SQL schema for the assignment's SQL exercises (not connected to the Java app).
CREATE TABLE Hotel (hotel_id VARCHAR(20) PRIMARY KEY, name VARCHAR(120), city VARCHAR(80), star_rating INT);
CREATE TABLE Room (room_id VARCHAR(20) PRIMARY KEY, hotel_id VARCHAR(20), room_number VARCHAR(20), room_type VARCHAR(20), nightly_price DECIMAL(10,2), capacity INT);
CREATE TABLE Guest (guest_id VARCHAR(20) PRIMARY KEY, name VARCHAR(120), phone VARCHAR(20), email VARCHAR(120));
CREATE TABLE Booking (booking_id VARCHAR(20) PRIMARY KEY, guest_id VARCHAR(20), hotel_id VARCHAR(20), check_in DATE, check_out DATE, status VARCHAR(30), total_amount DECIMAL(10,2));
CREATE TABLE BookingRoom (booking_id VARCHAR(20), room_id VARCHAR(20), PRIMARY KEY (booking_id, room_id));
CREATE TABLE Payment (payment_id VARCHAR(20) PRIMARY KEY, booking_id VARCHAR(20), amount DECIMAL(10,2), payment_method VARCHAR(30), status VARCHAR(20), paid_at TIMESTAMP);
CREATE TABLE Maintenance (maintenance_id VARCHAR(20) PRIMARY KEY, room_id VARCHAR(20), start_date DATE, end_date DATE, reason VARCHAR(200));
CREATE TABLE Review (review_id VARCHAR(20) PRIMARY KEY, booking_id VARCHAR(20), hotel_id VARCHAR(20), guest_id VARCHAR(20), rating INT, comment VARCHAR(500), created_on DATE);
