package com.example.tripease.Repository;

import com.example.tripease.Model.Cab;
import com.example.tripease.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabRepository extends JpaRepository<Cab,Integer> {
    @Query(value= "SELECT * FROM cab WHERE available= 1 ORDER BY RAND() LIMIT 1",nativeQuery = true)//HQL - hybernate quesry language
    Cab getAvailableCabRandomly();
}
