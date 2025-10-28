package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class ThongTinTaiKhoanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.thongtintaikhoan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm các View trong layout
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val btnEditAvatar = view.findViewById<ImageView>(R.id.btnEditAvatar)
        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val fieldHoTen = view.findViewById<TextView>(R.id.fieldHoTen)
        val fieldNgaySinh = view.findViewById<TextView>(R.id.fieldNgaySinh)
        val fieldGioiTinh = view.findViewById<TextView>(R.id.fieldGioiTinh)
        val fieldDiaChi = view.findViewById<TextView>(R.id.fieldDiaChi)
        val btnAddEmail = view.findViewById<TextView>(R.id.btnAddEmail)
        val btnAddPhone = view.findViewById<TextView>(R.id.btnAddPhone)

        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnEditAvatar.setOnClickListener {
            Toast.makeText(requireContext(), "Chỉnh sửa ảnh đại diện", Toast.LENGTH_SHORT).show()
        }

        tvUsername.setOnClickListener {
            Toast.makeText(requireContext(), "Chỉnh sửa tên người dùng", Toast.LENGTH_SHORT).show()
        }

        fieldHoTen.setOnClickListener {
            Toast.makeText(requireContext(), "Chỉnh sửa Họ tên", Toast.LENGTH_SHORT).show()
        }

        fieldNgaySinh.setOnClickListener {
            Toast.makeText(requireContext(), "Mở Lịch chọn ngày sinh", Toast.LENGTH_SHORT).show()
        }
        fieldGioiTinh.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Giới tính", Toast.LENGTH_SHORT).show()
        }

        fieldDiaChi.setOnClickListener {
            Toast.makeText(requireContext(), "Chỉnh sửa Địa chỉ", Toast.LENGTH_SHORT).show()
        }

        btnAddEmail.setOnClickListener {
            Toast.makeText(requireContext(), "Mở trang Thêm Email", Toast.LENGTH_SHORT).show()
        }

        btnAddPhone.setOnClickListener {
            Toast.makeText(requireContext(), "Mở trang Thêm Số di động", Toast.LENGTH_SHORT).show()
        }
    }
}