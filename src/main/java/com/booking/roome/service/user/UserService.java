package com.booking.roome.service.user;

import com.booking.roome.dto.reservationDto;
import com.booking.roome.dto.userDto;
import com.booking.roome.model.Hotel;
import com.booking.roome.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> users();
    User getUser(int id);
    User addUser(userDto newUser);
    User updateUser(userDto updatedUser, int id);
    void deleteUser(int id);
    ResponseEntity<?> addHotelToFavorites(int userId, int hotelId);
    ResponseEntity<?> removeHotelFromFavorites(int userId, int hotelId);
    ResponseEntity<?> bookHotel(reservationDto reservation);
    List<Hotel> getFavByUserId(int id);
    List<Hotel> getNearMeHotel(int id);
    List<Hotel> getRecommendedHotels(int id);
}
