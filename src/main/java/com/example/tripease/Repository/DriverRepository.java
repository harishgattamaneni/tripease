package com.example.tripease.Repository;

import com.example.tripease.Enum.Gender;
import com.example.tripease.Model.Customer;
import com.example.tripease.Model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer> {

    @Query(value = "select * from driver where cab_id = :cabId",nativeQuery = true)
    Driver getDriverByCabId(@Param("cabId") int cabId);
}
