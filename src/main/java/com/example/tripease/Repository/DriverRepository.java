package com.example.tripease.Repository;

import com.example.tripease.DTO.BestMatchDto;
import com.example.tripease.DTO.TopDriverDto;
import com.example.tripease.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    @Query(value = "select * from driver where cab_id = :cabId",nativeQuery = true)
    Driver getDriverByCabId(@Param("cabId") int cabId);

    @Query(value = "select * from driver where driver_id = :driverId",nativeQuery = true)
    Optional<Driver> getDriverByDriverId(@Param("driverId") int driverId);

    @Query(value =
            "SELECT " +
                    "d.name AS driverName, " +
                    "SUM(b.bill_amount) AS totalEarnings " +
                    "FROM driver d " +
                    "JOIN booking b ON b.driver_id = d.driver_id " +
                    "WHERE b.trip_status = 'COMPLETED' AND b.booked_at >= :pastDate " +
                    "GROUP BY d.driver_id, d.name " +
                    "ORDER BY totalEarnings DESC " +
                    "LIMIT :topN", // Efficiently restricts the result set
            nativeQuery = true)
    List<TopDriverDto> findTopDriversNative(@Param("pastDate") Date pastDate,
                                            @Param("topN") int topN);

    Optional<Driver> findByCabCabId(int newCabId);

    @Query(value = "select driver.driver_id,driver.name,cab.per_km_rate,sum(trip_distance_in_km) as total_distance " +
            "from driver " +
            "join cab on cab.cab_id = driver.cab_id " +
            "join booking on booking.driver_id = driver.driver_id " +
            "where cab.available=1 " +
            "group by driver.driver_id,driver.name,cab.per_km_rate " +
            "order by cab.per_km_rate ASC, total_distance DESC " +
            "limit 1",nativeQuery = true)
    BestMatchDto bestMatch();

    @Query(value = "select cab_id from driver where driver_id= :driverId",nativeQuery = true)
    int getCabId(@Param("driverId") int driverId);

    @Query(value = "SELECT d.name " +
            "FROM driver d " +
            "LEFT JOIN booking b ON d.driver_id = b.driver_id AND b.booked_at > :pastDate " +
            "GROUP BY d.name, d.driver_id " +
            "HAVING COUNT(b.booking_id) = 0 OR SUM(CASE WHEN b.trip_status <> 'Cancelled' THEN 1 ELSE 0 END) = 0",
            nativeQuery = true)
    List<String> noActivityDrivers(Date pastDate);
}
