package com.example.do_an_app

import java.io.Serializable

data class DatVe (
    val maDatVe: String,
    val from: String,
    val to: String,
    val date: String,
    val departureTime: String,
    val airline: String,
    val flightCode: String,
    val tenHanhKhach: String,
    val giaVe: Double,
    val status: String
) : Serializable