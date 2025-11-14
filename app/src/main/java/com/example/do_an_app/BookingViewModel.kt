package com.example.do_an_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookingViewModel : ViewModel() {

    // Dùng để lưu giá vé máy bay (từ ChuyenBay)
    private val _basePrice = MutableLiveData<Double>(0.0)

    // Dùng để lưu giá hành lý (từ HanhLyFragment)
    private val _baggagePrice = MutableLiveData<Double>(0.0)
    val baggagePrice: LiveData<Double> = _baggagePrice // Cho Fragment đọc

    // Dùng để lưu giá chọn ghế (từ ChonGheFragment)
    private val _seatPrice = MutableLiveData<Double>(0.0)
    val seatPrice: LiveData<Double> = _seatPrice // Cho Fragment đọc

    // Dùng để lưu tổng tiền cuối cùng
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice: LiveData<Double> = _totalPrice

    // Hàm này được gọi khi bấm "Tiếp tục" ở trang Chi Tiết
    fun setBaseFlight(flight: ChuyenBay) {
        _basePrice.value = flight.price
        calculateTotal()
    }

    // Hàm này được gọi khi chọn hành lý
    fun updateBaggagePrice(price: Double) {
        _baggagePrice.value = price
        calculateTotal()
    }

    // Hàm này được gọi khi chọn ghế
    fun updateSeatPrice(price: Double) {
        _seatPrice.value = price
        calculateTotal()
    }

    // Tự động cộng dồn
    private fun calculateTotal() {
        val base = _basePrice.value ?: 0.0
        val baggage = _baggagePrice.value ?: 0.0
        val seat = _seatPrice.value ?: 0.0
        _totalPrice.value = base + baggage + seat
    }
    // Dùng Set để lưu danh sách hãng bay được chọn (ví dụ: "Vietjet Air")
    private val _selectedAirlines = MutableLiveData<Set<String>>(emptySet())
    val selectedAirlines: LiveData<Set<String>> = _selectedAirlines

    private val _selectedPriceRanges = MutableLiveData<Set<String>>(emptySet())
    val selectedPriceRanges: LiveData<Set<String>> = _selectedPriceRanges

    // Hàm này được gọi khi bấm "Lưu" ở trang Bộ lọc
    fun setFilters(airlines: Set<String>, priceRanges: Set<String>) {
        _selectedAirlines.value = airlines
        _selectedPriceRanges.value = priceRanges // Sửa
    }

    // Hàm này được gọi khi bấm "Đặt lại"
    fun resetFilters() {
        _selectedAirlines.value = emptySet()
        _selectedPriceRanges.value = emptySet() // Sửa
    }

    // --- (Companion Object để các Fragment khác dùng chung key) ---
    companion object {
        const val PRICE_RANGE_1 = "R1" // Dưới 1 triệu
        const val PRICE_RANGE_2 = "R2" // 1-3 triệu
        const val PRICE_RANGE_3 = "R3" // 3-5 triệu
        const val PRICE_RANGE_4 = "R4" // Trên 5 triệu
    }
}