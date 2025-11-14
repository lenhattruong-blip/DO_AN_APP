package com.example.do_an_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.commit

class HomeFragment : Fragment(R.layout.home_fragment), ChuyenBayAdapter.Listener {

    private lateinit var rvFlightDeals: RecyclerView
    private lateinit var adapter: ChuyenBayAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSearch: Button = view.findViewById(R.id.btnSearchFlights)
        rvFlightDeals = view.findViewById(R.id.rvFlightDeals)

        btnSearch.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, TimKiemFragment())
                addToBackStack(null)
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val sampleDeals = listOf(
            ChuyenBay("Vietjet Air", "VJ-123", "5:00 AM", "TP HCM (SGN)", "7:00 AM", "Hà Nội (HAN)", 900000.0, "Tn 4, 21 thg 11"),
            ChuyenBay("Vietnam Airlines", "VN-456", "7:00 AM", "TP HCM (SGN)", "9:00 AM", "Hà Nội (HAN)", 1200000.0, "Tn 4, 21 thg 11"),
            ChuyenBay("Vietnam Airlines", "VN-456", "7:00 AM", "TP HCM (SGN)", "9:00 AM", "Hà Nội (HAN)", 1200000.0, "Tn 4, 21 thg 11"),
            ChuyenBay("Vietjet Air", "VJ-123", "5:00 AM", "TP HCM (SGN)", "7:00 AM", "Hà Nội (HAN)", 900000.0, "Tn 4, 21 thg 11"),
            ChuyenBay("Vietnam Airlines", "VN-456", "7:00 AM", "TP HCM (SGN)", "9:00 AM", "Hà Nội (HAN)", 1200000.0, "Tn 4, 21 thg 11")
        )

        adapter = ChuyenBayAdapter(sampleDeals, this)
        rvFlightDeals.layoutManager = LinearLayoutManager(requireContext())
        rvFlightDeals.adapter = adapter
    }

    override fun onItemClick(flight: ChuyenBay) {
        val detailFragment = ChiTietChuyenBayFragment.newInstance(flight)

        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container, detailFragment)
            addToBackStack(null)
        }
    }
}