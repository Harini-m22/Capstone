package com.example.demoBus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associated user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Route details (copied from the BusRoute at booking time)
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private double price;

    // Passenger details
    private String passengerName;
    private String seatPreference;

    // New fields for multiple seats booking
    private int numberOfSeats;
    private double totalAmount; // price * numberOfSeats
    private String coPassengerNames; // Optional names for additional passengers

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getPassengerName() {
        return passengerName;
    }
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSeatPreference() {
        return seatPreference;
    }
    public void setSeatPreference(String seatPreference) {
        this.seatPreference = seatPreference;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }


    // Getter
    public double getTotalAmount() {
        return totalAmount;
    }

    // Setter
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getCoPassengerNames() {
        return coPassengerNames;
    }
    public void setCoPassengerNames(String coPassengerNames) {
        this.coPassengerNames = coPassengerNames;
    }

    public void setBookingDate(LocalDateTime now) {
    }
}
