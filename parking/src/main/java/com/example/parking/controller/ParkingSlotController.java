package com.example.parking.controller;

import com.example.parking.model.ParkingSlot;
import com.example.parking.service.ParkingSlotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {
    private ParkingSlotService parkingSlotService;

    ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ParkingSlot> createSlot(@RequestBody ParkingSlot parkingSlot) {
        return new ResponseEntity<>(parkingSlotService.createSlot(parkingSlot), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSlot> updateSlot(@PathVariable String id, @RequestBody ParkingSlot parkingSlot) {
        return ResponseEntity.ok(parkingSlotService.updateSlot(id,parkingSlot));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable String id){
        parkingSlotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlot> getSlot(@PathVariable String id){
        return ResponseEntity.ok(parkingSlotService.getSlotById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSlot>> getAllSlots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "location") String sortBy,
            @RequestParam(defaultValue = "asc") String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(parkingSlotService.getAllSlots(pageable));
    }



    @GetMapping("/search")
    public ResponseEntity<Page<ParkingSlot>> searchByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(parkingSlotService.searchByLocation(location, pageable));
    }

}
