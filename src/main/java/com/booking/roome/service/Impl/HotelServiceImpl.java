package com.booking.roome.service.Impl;

import com.booking.roome.dto.hotelDto;
import com.booking.roome.exception.ExceptionResponse;
import com.booking.roome.exception.ExceptionRequest;
import com.booking.roome.mapper.HotelMapper;
import com.booking.roome.model.Facility;
import com.booking.roome.model.Hotel;
import com.booking.roome.model.User;
import com.booking.roome.repository.*;
import com.booking.roome.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final FacilityRepository facilityRepository;
    private final HotelMapper hotelMapper;



    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository,
                            UserRepository userRepository,
                            FacilityRepository facilityRepository,
                            HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.facilityRepository = facilityRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public List<Hotel> hotels() {
        return hotelRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getHotel(int id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ExceptionResponse("Hotel not found", HttpStatus.NOT_FOUND));

        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @Override
    public ResponseEntity<?> addHotel(hotelDto hotel) {
        User admin = userRepository.findById(hotel.getAdmin_id()).orElseThrow(() -> new ExceptionResponse("Admin not found", HttpStatus.NOT_FOUND));

        Hotel newHotel = hotelMapper.toEntity(hotel);
        newHotel.setAdmin(admin);
        List<Facility> facilities = facilityRepository.findAllById(hotel.getFacilities());

        for (Facility facility : facilities) {
            newHotel.addFacility(facility);
        }

        try {
            hotelRepository.save(newHotel);
        }catch (Exception e) {
            throw new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(newHotel);
    }

    @Override
    public ResponseEntity<?> updateHotel(hotelDto hotel, int id) {
        Hotel oldHotel = hotelRepository.findById(id).orElseThrow(() -> new ExceptionResponse("Hotel not found", HttpStatus.NOT_FOUND));

        User admin = userRepository.findById(hotel.getAdmin_id()).orElseThrow(() -> new ExceptionResponse("Admin not found", HttpStatus.NOT_FOUND));

        oldHotel.setName(hotel.getName());
        oldHotel.setAdmin(admin);
        oldHotel.setDescription(hotel.getDescription());
        oldHotel.setLocation(hotel.getLocation());
        oldHotel.setRate(hotel.getRate());
        oldHotel.setPrice(hotel.getPrice());
        oldHotel.setNumberRooms(hotel.getNumberRooms());

        List<Facility> facilities = facilityRepository.findAllById(hotel.getFacilities());

        oldHotel.setFacilities(facilities);

        try {
            hotelRepository.save(oldHotel);
        }catch (Exception e) {
            throw new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(oldHotel);
    }

    @Override
    public ResponseEntity<?> deleteHotel(int id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ExceptionResponse("Hotel not found", HttpStatus.NOT_FOUND));

        try {
            hotelRepository.delete(hotel);
        }catch (Exception e) {
            throw new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ExceptionRequest("Hotel deleted successfully", HttpStatus.OK.value())
        );
    }
}