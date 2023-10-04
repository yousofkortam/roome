package com.booking.roome.controller;

import com.booking.roome.dto.reservationDto;
import com.booking.roome.dto.userDto;
import com.booking.roome.model.User;
import com.booking.roome.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class userController {
    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> users() {
        return userService.users();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody userDto user) {
        return userService.addUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody userDto user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/add-to-fav/{userId}/hotel/{hotelId}")
    public ResponseEntity<?> addHotelToFavorites(@PathVariable int userId, @PathVariable int hotelId) {
        return userService.addHotelToFavorites(userId, hotelId);
    }

    @PostMapping("/remove-from-fav/{userId}/hotel/{hotelId}")
    public ResponseEntity<?> removeHotelFromFavorites(@PathVariable int userId, @PathVariable int hotelId) {
        return userService.removeHotelFromFavorites(userId, hotelId);
    }

    @PostMapping("/book-hotel")
    public ResponseEntity<?> bookHotel(@RequestBody reservationDto reservation) {
        return userService.bookHotel(reservation);
    }
}