-- Query 1: per city: hotels, confirmed bookings, average booking value; only cities with >2 hotels.
SELECT h.city, COUNT(DISTINCT h.hotel_id) AS total_hotels,
       COUNT(DISTINCT CASE WHEN b.status='CONFIRMED' THEN b.booking_id END) AS confirmed_bookings,
       AVG(CASE WHEN b.booking_id IS NOT NULL THEN b.total_amount END) AS average_booking_value
FROM Hotel h LEFT JOIN Booking b ON b.hotel_id=h.hotel_id
GROUP BY h.city
HAVING COUNT(DISTINCT h.hotel_id)>2
ORDER BY confirmed_bookings DESC;

-- Query 2: per hotel total bookings, checked-out revenue, cancellations; hotels with >=3 bookings.
SELECT h.hotel_id, h.name, COUNT(b.booking_id) AS total_bookings,
       SUM(CASE WHEN b.status='CHECKED_OUT' THEN b.total_amount ELSE 0 END) AS checked_out_revenue,
       SUM(CASE WHEN b.status='CANCELLED' THEN 1 ELSE 0 END) AS cancelled_count
FROM Hotel h JOIN Booking b ON b.hotel_id=h.hotel_id
GROUP BY h.hotel_id,h.name
HAVING COUNT(b.booking_id)>=3;

-- Query 3: per room type booking count, average nights, distinct hotels; types used in >2 hotels.
SELECT r.room_type, COUNT(DISTINCT br.booking_id) AS total_bookings,
       AVG(DATEDIFF(b.check_out,b.check_in)) AS average_nights,
       COUNT(DISTINCT b.hotel_id) AS distinct_hotels_booked
FROM Room r JOIN BookingRoom br ON br.room_id=r.room_id JOIN Booking b ON b.booking_id=br.booking_id
GROUP BY r.room_type
HAVING COUNT(DISTINCT b.hotel_id)>2
ORDER BY total_bookings DESC;
