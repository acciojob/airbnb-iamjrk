package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService
{
    HotelManagementRepository hotelManagementRepository= new HotelManagementRepository();
    public String addHotel(Hotel hotel)
    {
        return hotelManagementRepository.addHotel(hotel);
    }
    public Integer addUser(User user)
    {
        return hotelManagementRepository.addUser(user);
    }
    public String getHotelWithMostFacilities()
    {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }
    public int bookARoom(Booking booking)
    {
        return hotelManagementRepository.bookARoom(booking);
    }
    public int getBookings(Integer aadharCard)
    {
        return hotelManagementRepository.getBookings(aadharCard);
    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName)
    {
        return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
    }
}
