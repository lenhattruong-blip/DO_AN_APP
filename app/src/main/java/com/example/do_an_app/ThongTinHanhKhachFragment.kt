package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class ThongTinHanhKhachFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.thongtinhanhkhach_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileSection = view.findViewById<ConstraintLayout>(R.id.clProfileSection)
        val addPassengerButton = view.findViewById<Button>(R.id.btnAddPassenger)
        val passenger1 = view.findViewById<CardView>(R.id.cardPassenger1)
        val passenger2 = view.findViewById<CardView>(R.id.cardPassenger2)
        val passenger3 = view.findViewById<CardView>(R.id.cardPassenger3)

        profileSection.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chi tiết hồ sơ", Toast.LENGTH_SHORT).show()
        }
        addPassengerButton.setOnClickListener {
            Toast.makeText(requireContext(), "Mở form thêm hành khách mới", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, ThemThongTinHanhKhachFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        passenger1.setOnClickListener {
            Toast.makeText(requireContext(), "Xem chi tiết: Viet Thang Pham", Toast.LENGTH_SHORT).show()
        }
        passenger2.setOnClickListener {
            Toast.makeText(requireContext(), "Xem chi tiết: Nhat Truong Nguyen", Toast.LENGTH_SHORT).show()
        }
        passenger3.setOnClickListener {
            Toast.makeText(requireContext(), "Xem chi tiết: Viet Chan Nguyen", Toast.LENGTH_SHORT).show()
        }
    }
}