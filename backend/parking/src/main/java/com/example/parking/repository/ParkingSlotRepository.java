package com.example.parking.repository;

import com.example.parking.model.ParkingSlot;
//using pages as fetching all together is not good so divide in pages tell which page ez
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingSlotRepository extends MongoRepository<ParkingSlot, String>{
    Page<ParkingSlot> findByAvailable(boolean available, Pageable pageable);

    //Containing is like "Like" in MySql %location% IgnoreCase yk
    Page<ParkingSlot> findByLocationContainingIgnoreCase(String location, Pageable pageable);
}
