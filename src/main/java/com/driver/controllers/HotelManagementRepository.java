package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class HotelManagementRepository
{
    HashMap<String, Hotel> hotelDB= new HashMap<>(); //Hotel Database
    HashMap<Integer, User>userDB= new HashMap<>(); //User Database
    HashMap<String, Booking>bookingDB= new HashMap<>(); //Booking Database
    public String addHotel(Hotel hotel)
    {
        if(hotel.getHotelName()==null || hotel==null)
        {
            return "FAILURE";
        }
        if(hotelDB.containsKey(hotel.getHotelName()))
        {
            return "FAILURE";
        }
        hotelDB.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }
    public Integer addUser(User user)
    {
        userDB.put(user.getaadharCardNo(),user);

        return user.getaadharCardNo();
    }
    public String getHotelWithMostFacilities()
    {
        int facilityQuantity=0;
        String nameOfHotel="";
        for(String hotelName: hotelDB.keySet())
        {
            Hotel h=hotelDB.get(hotelName);
            List<Facility> facility=h.getFacilities();
            if(facility.size()>facilityQuantity)
            {
                facilityQuantity=facility.size();//getting the size of facility
                nameOfHotel=h.getHotelName(); //storing name of hotel for comparison
            }
            else if (facilityQuantity!=0 && facility.size()==facilityQuantity)
            {
                //Previous Hotel Name is greater then current hotel name(lexicographically)
                if(nameOfHotel.compareTo(h.getHotelName())>0)
                {
                    nameOfHotel=h.getHotelName();
                }
            }
        }
        return nameOfHotel;
    }
    public int bookARoom(Booking booking)
    {
        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid

        UUID uuid=UUID.randomUUID();//generating new random ID
        String bookingId=uuid.toString();
        booking.setBookingId(bookingId);

        String nameOfHotel= booking.getHotelName();
        Hotel hotel= hotelDB.get(nameOfHotel);
        int pricePerNight= hotel.getPricePerNight();
        int noOfRooms=booking.getNoOfRooms();
        int availableRooms=hotel.getAvailableRooms();

        if(noOfRooms>availableRooms)return -1;

        int amountPaid=noOfRooms*pricePerNight;
        booking.setAmountToBePaid(amountPaid);

        hotel.setAvailableRooms(availableRooms-noOfRooms);
        bookingDB.put(bookingId,booking);
        hotelDB.put(nameOfHotel,hotel);

        return amountPaid;

    }
    public int getBookings(Integer aadharCard)
    {
        //In this function return the bookings done by a person
        int noOfBookings=0;

        for(String bookingId:bookingDB.keySet())
        {
            Booking booking= bookingDB.get(bookingId);
            if(booking.getBookingAadharCard()==aadharCard)
            {
                noOfBookings++;
            }
        }

        return noOfBookings;
    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName)
    {
        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible
        Hotel hotel= hotelDB.get(hotelName);
        List<Facility> existingFacilities= hotel.getFacilities();

        for(Facility facility: newFacilities)
        {
            if(existingFacilities.contains(facility)) continue;
            else
            {
                existingFacilities.add(facility);
            }
        }

        hotel.setFacilities(existingFacilities);
        hotelDB.put(hotelName,hotel);
        return hotel;
    }
}
