package com.example.do_an_app
import java.io.Serializable

data class ChuyenBay(
    val airline: String,
    val flightCode: String,
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val price: Double,
    val date: String
): Serializable