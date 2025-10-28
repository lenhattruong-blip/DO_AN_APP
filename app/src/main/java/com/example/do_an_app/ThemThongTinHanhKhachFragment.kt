package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import java.util.Calendar

class ThemThongTinHanhKhachFragment : Fragment() {

    // Khai báo các View
    private lateinit var toolbar: MaterialToolbar
    private lateinit var etTen: EditText
    private lateinit var etHo: EditText
    private lateinit var etNgaySinh: EditText
    private lateinit var etQuocTich: EditText
    private lateinit var etChucDanh: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load file layout
        return inflater.inflate(R.layout.themthongtinhanhkhach_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ View
        toolbar = view.findViewById(R.id.toolbar)
        etTen = view.findViewById(R.id.etTen)
        etHo = view.findViewById(R.id.etHo)
        etNgaySinh = view.findViewById(R.id.etNgaySinh)
        etQuocTich = view.findViewById(R.id.etQuocTich)
        etChucDanh = view.findViewById(R.id.etChucDanh)
        btnSave = view.findViewById(R.id.btnSave)
        btnCancel = view.findViewById(R.id.btnCancel)

        // --- Gán sự kiện Click ---

        // 1. Nút quay lại (Back) trên Toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 2. Nút Hủy
        btnCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 3. Nút Lưu (với validation đơn giản)
        btnSave.setOnClickListener {
            if (validateInput()) {
                val fullName = "${etHo.text.toString().trim()} ${etTen.text.toString().trim()}"
                Toast.makeText(requireContext(), "Đã lưu: $fullName", Toast.LENGTH_SHORT).show()
                // Đóng fragment sau khi lưu
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        // 4. Các trường "giả" Spinner/DatePicker
        etNgaySinh.setOnClickListener {
            // Mở DatePickerDialog (Giống Lab 2)
            openDatePicker()
        }

        etChucDanh.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Chức danh", Toast.LENGTH_SHORT).show()
        }

        etQuocTich.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Quốc tịch", Toast.LENGTH_SHORT).show()
        }
    }

    // Hàm kiểm tra thông tin (Giống Lab 2)
    private fun validateInput(): Boolean {
        val ten = etTen.text.toString().trim()
        val ho = etHo.text.toString().trim()
        val ngaySinh = etNgaySinh.text.toString().trim()
        val quocTich = etQuocTich.text.toString().trim() // Thực tế nên kiểm tra khác
        val chucDanh = etChucDanh.text.toString().trim() // Thực tế nên kiểm tra khác

        if (ten.isEmpty()) {
            etTen.error = "Vui lòng nhập tên"
            etTen.requestFocus()
            return false
        }

        if (ho.isEmpty()) {
            etHo.error = "Vui lòng nhập họ"
            etHo.requestFocus()
            return false
        }

        if (ngaySinh.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng chọn ngày sinh", Toast.LENGTH_SHORT).show()
            return false
        }

        // (Bạn có thể thêm validation cho các trường khác)

        return true
    }

    // Hàm mở Lịch chọn ngày (Giống Lab 2)
    private fun openDatePicker() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val dateStr = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            etNgaySinh.setText(dateStr)
        }, y, m, d)

        // Không cho chọn ngày tương lai
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
}