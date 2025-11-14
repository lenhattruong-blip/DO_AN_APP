package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ChonGheFragment : Fragment() {
    private val seatViews = mutableListOf<TextView>()
    private var selectedSeat: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chonghe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnConfirm: Button = view.findViewById(R.id.btnConfirmSeat)

        // Tìm các ghế có thể chọn (bỏ qua ghế đã đặt)
        val seat1A: TextView = view.findViewById(R.id.seat1A)
        val seat1B: TextView = view.findViewById(R.id.seat1B)
        val seat1C: TextView = view.findViewById(R.id.seat1C)
        val seat1D: TextView = view.findViewById(R.id.seat1D)
        val seat2A: TextView = view.findViewById(R.id.seat2A)
        // Ghế 2B, 2C, 2D là ghế đã chọn/đã đặt, chúng ta không thêm listener

        seatViews.addAll(listOf(seat1A, seat1B, seat1C, seat1D, seat2A))

        // Gán listener cho từng ghế
        for (seat in seatViews) {
            seat.setOnClickListener { onSeatClicked(it as TextView) }
        }

        // Xử lý nút Xác nhận
        btnConfirm.setOnClickListener {
            if (selectedSeat != null) {
                Toast.makeText(requireContext(), "Đã chọn ghế: ${selectedSeat?.text}", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Vui lòng chọn 1 ghế", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSeatClicked(seat: TextView) {
        // 1. Bỏ chọn ghế cũ (nếu có)
        selectedSeat?.let {
            it.setBackgroundResource(R.drawable.ghexam_chuachon)
            it.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        }

        // 2. Chọn ghế mới
        seat.setBackgroundResource(R.drawable.ghexanh_dachon)
        seat.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))

        // 3. Lưu ghế mới
        selectedSeat = seat
    }
}