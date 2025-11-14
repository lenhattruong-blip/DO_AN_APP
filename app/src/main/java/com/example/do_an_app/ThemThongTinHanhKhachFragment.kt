package com.example.do_an_app

import android.app.DatePickerDialog
import android.content.Context
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
    private lateinit var db: DatabaseHelper
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
        db = DatabaseHelper(requireContext())
        return inflater.inflate(R.layout.themthongtinhanhkhach_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        etTen = view.findViewById(R.id.etTen)
        etHo = view.findViewById(R.id.etHo)
        etNgaySinh = view.findViewById(R.id.etNgaySinh)
        etQuocTich = view.findViewById(R.id.etQuocTich)
        etChucDanh = view.findViewById(R.id.etChucDanh)
        btnSave = view.findViewById(R.id.btnSave)
        btnCancel = view.findViewById(R.id.btnCancel)

        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        btnSave.setOnClickListener {
            if (validateInput()) {
                val userId = getSavedUserId()
                if (userId == -1) {
                    Toast.makeText(requireContext(), "Lỗi: Chưa đăng nhập", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val newHanhKhach = HanhKhach(
                    userId = userId,
                    ho = etHo.text.toString().trim(),
                    ten = etTen.text.toString().trim(),
                    ngaySinh = etNgaySinh.text.toString().trim(),
                    quocTich = etQuocTich.text.toString().trim(),
                    chucDanh = etChucDanh.text.toString().trim()
                )
                val result = db.insertHanhKhach(newHanhKhach)

                if (result > -1) {
                    Toast.makeText(requireContext(), "Đã lưu hành khách", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
        etNgaySinh.setOnClickListener {
            openDatePicker()
        }

        etChucDanh.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Chức danh", Toast.LENGTH_SHORT).show()
        }

        etQuocTich.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Quốc tịch", Toast.LENGTH_SHORT).show()
        }
    }
    private fun validateInput(): Boolean {
        val ten = etTen.text.toString().trim()
        val ho = etHo.text.toString().trim()
        val ngaySinh = etNgaySinh.text.toString().trim()
        val quocTich = etQuocTich.text.toString().trim()
        val chucDanh = etChucDanh.text.toString().trim()

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

        return true
    }
    private fun openDatePicker() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
            val dateStr = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            etNgaySinh.setText(dateStr)
        }, y, m, d)
        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
    private fun getSavedUserId(): Int {
        val prefs = requireActivity().getSharedPreferences("FlightAppPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("USER_ID", -1) // Trả về -1 nếu không tìm thấy
    }
}