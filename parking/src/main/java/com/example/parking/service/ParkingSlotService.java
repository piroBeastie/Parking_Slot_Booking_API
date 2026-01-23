package com.example.parking.service;

import com.example.parking.model.ParkingSlot;
import com.example.parking.repository.ParkingSlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public ParkingSlot createSlot(ParkingSlot slot) {
        slot.setAvailable(true);
        return parkingSlotRepository.save(slot);
    }

    public ParkingSlot updateSlot(String id, ParkingSlot updatedSlot) {
        ParkingSlot existing = parkingSlotRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Slot not found"));

        existing.setLocation(updatedSlot.getLocation());
        existing.setSlotNumber(updatedSlot.getSlotNumber());
        existing.setPricePerHour(updatedSlot.getPricePerHour());
        existing.setAvailable(updatedSlot.isAvailable());

        return parkingSlotRepository.save(existing);
    }

    public void deleteSlot(String id) {
        if (!parkingSlotRepository.existsById(id)) {
            throw new RuntimeException("Parking slot not found");
        }
        parkingSlotRepository.deleteById(id);
    }

    public ParkingSlot getSlotById(String id) {
        return parkingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking slot not found"));
    }

    public Page<ParkingSlot> getAllSlots(Pageable pageable) {
        return parkingSlotRepository.findAll(pageable);
    }

    public Page<ParkingSlot> getAvailableSlots(boolean available, Pageable pageable) {
        return parkingSlotRepository.findByAvailable(available, pageable);
    }

    public Page<ParkingSlot> searchByLocation(String location, Pageable pageable) {
        return parkingSlotRepository.findByLocationContainingIgnoreCase(location, pageable);
    }
}
