package com.example.tripease.Repository;

import com.example.tripease.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query(value="SELECT * FROM booking WHERE customer_id = :customerId ORDER BY booked_at DESC LIMIT 1",nativeQuery = true)
    Booking findLatestById(@Param("customerId") int customerId);

    @Query(value = "SELECT driver_id FROM booking WHERE booking_id = :bookingId", nativeQuery = true)
    Integer findDriverIdByBookingId(@Param("bookingId") int bookingId);

    @Query(value = "SELECT * FROM booking WHERE booked_at >= :date1 and booked_at <= :date2 and driver_id = :driverid", nativeQuery = true)
    List<Booking> findBookingsByDriverId(@Param("driverid")int driverid, @Param("date1")Date date1,@Param("date2")Date date2);

    @Query(value = "SELECT driver_id from booking where booking_id= :booking_id",nativeQuery = true)
    int findByBookingId(@Param("booking_id") int booking_id);

    @Query(value = "SELECT count(*) from booking where driver_id = :driver_id and trip_status ='Ongoing'",nativeQuery = true)
    int findBookingsByDriverId(@Param("driver_id") int driverId);
}
