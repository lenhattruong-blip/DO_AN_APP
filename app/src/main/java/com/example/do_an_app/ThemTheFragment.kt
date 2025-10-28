package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class ThemTheFragment : Fragment() {

    // Khai báo các View để truy cập
    private lateinit var etSoThe: EditText
    private lateinit var etHieuLuc: EditText
    private lateinit var etCvv: EditText
    private lateinit var etTenThe: EditText
    private lateinit var btnThemTheMoi: Button
    private lateinit var toolbar: MaterialToolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load file layout
        return inflater.inflate(R.layout.themthe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm các View trong layout
        toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        etSoThe = view.findViewById<EditText>(R.id.etSoThe)
        etHieuLuc = view.findViewById<EditText>(R.id.etHieuLuc)
        etCvv = view.findViewById<EditText>(R.id.etCvv)
        etTenThe = view.findViewById<EditText>(R.id.etTenThe)
        btnThemTheMoi = view.findViewById<Button>(R.id.btnThemTheMoi)

        // --- Gán sự kiện Click ---

        // 1. Nút quay lại (Back) trên Toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 2. Nút "Thêm thẻ mới"
        btnThemTheMoi.setOnClickListener {
            // Lấy dữ liệu từ các ô EditText
            val soThe = etSoThe.text.toString().trim()
            val hieuLuc = etHieuLuc.text.toString().trim()
            val cvv = etCvv.text.toString().trim()
            val tenThe = etTenThe.text.toString().trim()

            // Kiểm tra xem người dùng đã nhập đủ thông tin chưa
            if (soThe.isEmpty() || hieuLuc.isEmpty() || cvv.isEmpty() || tenThe.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                // (Trong thực tế, bạn sẽ xử lý logic thêm thẻ tại đây)
                Toast.makeText(requireContext(), "Đang thêm thẻ: $tenThe", Toast.LENGTH_SHORT).show()

                // (Tùy chọn) Sau khi thêm, có thể đóng Fragment này
                // requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}
