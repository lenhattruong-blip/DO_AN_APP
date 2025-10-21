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
    private lateinit var DatVeAdapter: DatVeAdapter
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
        DatVeAdapter = DatVeAdapter(bookingList)
        recyclerView.adapter = DatVeAdapter
    }
    private fun createDummyData() {
        bookingList.add(
            DatVe(
                "Hồ Chí Minh", "Hà Nội", "20/10/2025", "Vietnam Airlines",
                "Đã hoàn thành"
            )
        )
        bookingList.add(
            DatVe(
                "Đà Nẵng", "Hồ Chí Minh", "15/09/2025", "Vietjet Air",
                "Đã hoàn thành"
            )
        )
        bookingList.add(
            DatVe(
                "Hà Nội", "Phú Quốc", "01/08/2025", "Bamboo Airways",
                "Đã hủy"
            )
        )
    }
}