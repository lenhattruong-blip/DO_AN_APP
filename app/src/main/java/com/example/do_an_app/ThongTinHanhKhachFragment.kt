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
        // Load file layout (tôi giả sử bạn đặt tên là fragment_passenger.xml)
        return inflater.inflate(R.layout.thongtinhanhkhach_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm các View trong layout bằng ID
        val profileSection = view.findViewById<ConstraintLayout>(R.id.clProfileSection)
        val addPassengerButton = view.findViewById<Button>(R.id.btnAddPassenger)
        val passenger1 = view.findViewById<CardView>(R.id.cardPassenger1)
        val passenger2 = view.findViewById<CardView>(R.id.cardPassenger2)
        val passenger3 = view.findViewById<CardView>(R.id.cardPassenger3)

        // Gán sự kiện click cho mục "Hồ sơ hành khách"
        profileSection.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chi tiết hồ sơ", Toast.LENGTH_SHORT).show()
        }

        // Gán sự kiện click cho nút "Thêm hành khách"
        addPassengerButton.setOnClickListener {
            Toast.makeText(requireContext(), "Mở form thêm hành khách mới", Toast.LENGTH_SHORT).show()
        }

        // Gán sự kiện click cho từng hành khách
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