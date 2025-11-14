package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class LichSuDatVeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var datVeAdapter: DatVeAdapter

    private val bookingList = mutableListOf<DatVe>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lichsu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvBookingHistory)
        recyclerView.layoutManager = LinearLayoutManager(context)

        createDummyData()

        datVeAdapter = DatVeAdapter(bookingList)
        recyclerView.adapter = datVeAdapter
    }
    private fun createDummyData() {
        bookingList.add(
            DatVe(
                maDatVe = "ABC123",
                from = "Hồ Chí Minh",
                to = "Hà Nội",
                date = "20/10/2025",
                departureTime = "07:00 AM",
                airline = "Vietnam Airlines",
                flightCode = "VN-220",
                tenHanhKhach = "Nguyễn Văn A",
                giaVe = 1500000.0,
                status = "Đã hoàn thành"
            )
        )
        bookingList.add(
            DatVe(
                maDatVe = "DEF456",
                from = "Đà Nẵng",
                to = "Hồ Chí Minh",
                date = "15/09/2025",
                departureTime = "10:30 AM",
                airline = "Vietjet Air",
                flightCode = "VJ-512",
                tenHanhKhach = "Trần Thị B",
                giaVe = 950000.0,
                status = "Đã hoàn thành"
            )
        )
        bookingList.add(
            DatVe(
                maDatVe = "GHI789",
                from = "Hà Nội",
                to = "Phú Quốc",
                date = "01/08/2025",
                departureTime = "14:00 PM",
                airline = "Bamboo Airways",
                flightCode = "QH-115",
                tenHanhKhach = "Lê Nhật Trường",
                giaVe = 950000.0,
                status = "Đã hủy"
            )
        )
    }
}