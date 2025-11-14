package com.example.do_an_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class ThongTinLienHeFragment : Fragment() {

    private lateinit var db: DatabaseHelper
    private var currentUserId: Int = -1 // Biến lưu ID người dùng

    private lateinit var toolbar: MaterialToolbar
    private lateinit var etHo: TextInputEditText
    private lateinit var etTen: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // --- (2) Khởi tạo DB ---
        db = DatabaseHelper(requireContext())
        return inflater.inflate(R.layout.thongtinlienhe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ View
        toolbar = view.findViewById(R.id.toolbar)
        etHo = view.findViewById(R.id.etHo)
        etTen = view.findViewById(R.id.etTen)
        etPhone = view.findViewById(R.id.etPhone)
        etEmail = view.findViewById(R.id.etEmail)
        btnSave = view.findViewById(R.id.btnSave)

        // Lấy ID người dùng và tải thông tin
        currentUserId = getSavedUserId()
        if (currentUserId != -1) {
            loadUserData(currentUserId)
        } else {
            Toast.makeText(requireContext(), "Lỗi: Không tìm thấy người dùng", Toast.LENGTH_SHORT).show()
        }

        // 1. Nút quay lại (Back) trên Toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 2. Nút "Lưu" (Sửa lại)
        btnSave.setOnClickListener {
            if (validateInput()) {
                saveChanges()
            }
        }
    }

    /**
     * Hàm đọc ID người dùng từ SharedPreferences
     */
    private fun getSavedUserId(): Int {
        val prefs = requireActivity().getSharedPreferences("FlightAppPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("USER_ID", -1)
    }

    /**
     * HÀM MỚI: Tải thông tin User từ DB
     */
    private fun loadUserData(userId: Int) {
        val user = db.getUserById(userId)
        user?.let {
            // Tách Họ và Tên từ user.name (giả sử tên là "Lê Nhật Trường")
            val nameParts = it.name.split(" ", limit = 2)
            val ho = nameParts.getOrNull(0) ?: ""
            val ten = nameParts.getOrNull(1) ?: ""

            etHo.setText(ho)
            etTen.setText(ten)
            etPhone.setText(it.phone)
            etEmail.setText(it.email)
        }
    }

    /**
     * HÀM MỚI: Lưu thay đổi vào DB
     */
    private fun saveChanges() {
        val ho = etHo.text.toString().trim()
        val ten = etTen.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()

        // Gộp Họ và Tên lại
        val fullName = "$ho $ten"

        // Gọi hàm updateUser
        val result = db.updateUser(currentUserId, fullName, phone, email)

        if (result > 0) {
            Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Hàm kiểm tra thông tin
     */
    private fun validateInput(): Boolean {
        if (etHo.text.toString().trim().isEmpty()) {
            etHo.error = "Vui lòng nhập họ"
            etHo.requestFocus()
            return false
        }
        if (etTen.text.toString().trim().isEmpty()) {
            etTen.error = "Vui lòng nhập tên"
            etTen.requestFocus()
            return false
        }
        if (etPhone.text.toString().trim().isEmpty()) {
            etPhone.error = "Vui lòng nhập số điện thoại"
            etPhone.requestFocus()
            return false
        }
        if (etEmail.text.toString().trim().isEmpty()) {
            etEmail.error = "Vui lòng nhập email"
            etEmail.requestFocus()
            return false
        }
        return true
    }
}