package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DSCBMotChieuFragment : Fragment(), DSCBTimKiemAdapter.Listener {

    // Lấy ViewModel chung (để lọc)
    private val viewModel: BookingViewModel by activityViewModels()

    private lateinit var rvFlights: RecyclerView
    private lateinit var tvNoFlights: TextView
    private lateinit var adapter: DSCBTimKiemAdapter

    private var searchFrom: String? = null
    private var searchTo: String? = null
    private var searchDate: String? = null

    // Database giả  (Nên được chuyển ra ViewModel hoặc 1 file riêng)
    private val allFlightsDatabase = listOf(
        ChuyenBay("Vietjet Air", "VJ-123", "5:00 AM", "Hồ Chí Minh (SGN)", "7:00 AM", "Hà Nội (HAN)", 900000.0, "20/11/2025"),
        ChuyenBay("Vietnam Airlines", "VN-456", "7:00 AM", "Hồ Chí Minh (SGN)", "9:00 AM", "Hà Nội (HAN)", 1200000.0, "20/11/2025"),
        ChuyenBay("Bamboo Airways", "QH-111", "9:00 AM", "Hồ Chí Minh (SGN)", "11:00 AM", "Đà Nẵng (DAD)", 700000.0, "20/11/2025"),
        ChuyenBay("Vietjet Air", "VJ-555", "10:00 AM", "Hà Nội (HAN)", "12:00 PM", "Hồ Chí Minh (SGN)", 950000.0, "21/11/2025"),
        ChuyenBay("Vietnam Airlines", "VN-999", "14:00 PM", "Đà Nẵng (DAD)", "15:00 PM", "Hồ Chí Minh (SGN)", 800000.0, "22/11/2025"),
        ChuyenBay("Vietjet Air", "VJ-111", "8:00 AM", "Hồ Chí Minh (SGN)", "10:00 AM", "Hà Nội (HAN)", 920000.0, "20/11/2025")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchFrom = it.getString(ARG_FROM)
            searchTo = it.getString(ARG_TO)
            searchDate = it.getString(ARG_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dscbmotchieu_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFlights = view.findViewById(R.id.rvFlightList)
        tvNoFlights = view.findViewById(R.id.tvNoFlights)
        // Lắng nghe thay đổi từ Bộ lọc (ViewModel)
        viewModel.selectedAirlines.observe(viewLifecycleOwner) {
            setupRecyclerView()
        }
        viewModel.selectedPriceRanges.observe(viewLifecycleOwner) {
            setupRecyclerView()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val airlinesToFilter = viewModel.selectedAirlines.value ?: emptySet()
        val priceRangesToFilter = viewModel.selectedPriceRanges.value ?: emptySet()

        val filteredList = allFlightsDatabase.filter { flight ->
            // Điều kiện tìm kiếm (như cũ)
            val matchFrom = flight.departureAirport == searchFrom
            val matchTo = flight.arrivalAirport == searchTo
            val matchDate = flight.date == searchDate

            // Lọc hãng bay
            val matchAirline = airlinesToFilter.isEmpty() || airlinesToFilter.contains(flight.airline)
            // Lọc giá
            val matchPrice = checkPriceInRange(flight.price, priceRangesToFilter)

            matchFrom && matchTo && matchDate && matchAirline && matchPrice
        }

        // Thông báo nếu không tìm thấy
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "Không tìm thấy chuyến bay", Toast.LENGTH_SHORT).show()
        }
        if (filteredList.isEmpty()) {
            rvFlights.visibility = View.GONE    // Ẩn RecyclerView
            tvNoFlights.visibility = View.VISIBLE // Hiện TextView
        } else {
            rvFlights.visibility = View.VISIBLE  // Hiện RecyclerView
            tvNoFlights.visibility = View.GONE     // Ẩn TextView
        }
        // Hiển thị danh sách ĐÃ LỌC
        adapter = DSCBTimKiemAdapter(filteredList, this)
        rvFlights.layoutManager = LinearLayoutManager(requireContext())
        rvFlights.adapter = adapter
    }

    override fun onItemClick(flight: ChuyenBay) {
        // (Bấm vào 1 chuyến bay -> Đi đến trang chi tiết)
        val detailFragment = ChiTietChuyenBayFragment.newInstance(flight)
        // Dùng parentFragmentManager vì chúng ta đang ở trong 1 Fragment con
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container, detailFragment)
            addToBackStack(null)
        }
    }

    private fun checkPriceInRange(price: Double, ranges: Set<String>): Boolean {
        if (ranges.isEmpty()) {
            return true
        }
        if (ranges.contains(BookingViewModel.PRICE_RANGE_1) && price < 1000000) {
            return true
        }
        if (ranges.contains(BookingViewModel.PRICE_RANGE_2) && price >= 1000000 && price <= 3000000) {
            return true
        }
        if (ranges.contains(BookingViewModel.PRICE_RANGE_3) && price > 3000000 && price <= 5000000) {
            return true
        }
        if (ranges.contains(BookingViewModel.PRICE_RANGE_4) && price > 5000000) {
            return true
        }
        return false
    }

    companion object {
        private const val ARG_FROM = "arg_from"
        private const val ARG_TO = "arg_to"
        private const val ARG_DATE = "arg_date"

        fun newInstance(from: String, to: String, date: String): DSCBMotChieuFragment {
            val fragment = DSCBMotChieuFragment()
            val args = Bundle()
            args.putString(ARG_FROM, from)
            args.putString(ARG_TO, to)
            args.putString(ARG_DATE, date)
            fragment.arguments = args
            return fragment
        }
    }
}