package com.example.tripease.Repository;

import com.example.tripease.DTO.PopularCabModelDto;
import com.example.tripease.DTO.RateRangeDto;
import com.example.tripease.Model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface CabRepository extends JpaRepository<Cab,Integer> {
    @Query(value= "SELECT * FROM cab WHERE available= 1 ORDER BY RAND() LIMIT 1",nativeQuery = true)//HQL - hybernate quesry language
    Cab getAvailableCabRandomly();

    @Query(value="Select " +
            "cab.cab_model, " +
            "sum(booking.trip_distance_in_km) as totalDistance " +
            "from cab " +
            "join driver on cab.cab_id=driver.cab_id " +
            "join booking on driver.driver_id=booking.driver_id " +
            "where booked_at>= :pastDate " +
            "group by cab.cab_model " +
            "order by totalDistance DESC " +
            "limit 1",nativeQuery = true)
    PopularCabModelDto getPopularCarModel(@Param("pastDate") Date pastDate);

    @Query(value = "select cab.cab_model,cab.cab_number,cab.per_km_rate,driver.name,driver.age " +
            "from cab " +
            "join driver on cab.cab_id=driver.cab_id " +
            "where cab.per_km_rate>= :minRate and cab.per_km_rate<= :maxRate " +
            "order by driver.age",nativeQuery = true)
    List<RateRangeDto> getRateRange(@Param("minRate") int minRate,@Param("maxRate") int maxRate);


    @Query(value = "select cab.cab_id,cab.available,cab.cab_model,cab.cab_number,cab.per_km_rate from cab left join driver on cab.cab_id=driver.cab_id " +
            "where driver.driver_id is null",nativeQuery = true)
    List<Cab> getNoDriver();
}
