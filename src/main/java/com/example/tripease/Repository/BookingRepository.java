package com.example.tripease.Repository;

import com.example.tripease.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query(value="SELECT * FROM booking WHERE customer_id = :customerId ORDER BY booked_at DESC LIMIT 1",nativeQuery = true)
    Booking findLatestById(@Param("customerId") int customerId);

    @Query(value = "SELECT driver_id FROM booking WHERE booking_id = :bookingId", nativeQuery = true)
    Integer findDriverIdByBookingId(@Param("bookingId") int bookingId);

    @Query(value = "SELECT * FROM booking WHERE booked_at >= :date1 and booked_at <= :date2 and driver_id = :driverid", nativeQuery = true)
    List<Booking> findBookingsByDriverId(int driverid, Date date1, Date date2);
}
