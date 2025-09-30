package com.example.tripease.Repository;

import com.example.tripease.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query(value="SELECT * FROM booking WHERE customer_id = :customerId ORDER BY booked_at DESC LIMIT 1",nativeQuery = true)
    Booking findLatestById(@Param("customerId") int customerId);

    @Query(value = "SELECT driver_id FROM booking WHERE booking_id = :bookingId", nativeQuery = true)
    Integer findDriverIdByBookingId(@Param("bookingId") int bookingId);

}
