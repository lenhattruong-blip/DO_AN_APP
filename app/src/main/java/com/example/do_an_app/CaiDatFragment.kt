package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class CaiDatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load file layout (tôi giả sử bạn đặt tên là fragment_settings.xml)
        return inflater.inflate(R.layout.caidat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm các View trong layout bằng ID
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val itemAccountInfo = view.findViewById<ConstraintLayout>(R.id.itemAccountInfo)
        val itemPassword = view.findViewById<ConstraintLayout>(R.id.itemPassword)
        val itemCurrency = view.findViewById<ConstraintLayout>(R.id.itemCurrency)
        val itemLanguage = view.findViewById<ConstraintLayout>(R.id.itemLanguage)
        val itemLogout = view.findViewById<ConstraintLayout>(R.id.itemLogout)

        // --- Gán sự kiện Click ---

        // 1. Nút quay lại (Back) trên Toolbar
        toolbar.setNavigationOnClickListener {
            // Quay lại Fragment/Activity trước đó
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 2. Thông tin tài khoản
        itemAccountInfo.setOnClickListener {
            Toast.makeText(requireContext(), "Mở Thông tin tài khoản", Toast.LENGTH_SHORT).show()
            // (Trong tương lai, bạn có thể chuyển sang một Fragment/Activity khác tại đây)
        }

        // 3. Mật khẩu & Bảo mật
        itemPassword.setOnClickListener {
            Toast.makeText(requireContext(), "Mở Mật khẩu & Bảo mật", Toast.LENGTH_SHORT).show()
        }

        // 4. Tiền tệ
        itemCurrency.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Tiền tệ", Toast.LENGTH_SHORT).show()
        }

        // 5. Ngôn ngữ
        itemLanguage.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Ngôn ngữ", Toast.LENGTH_SHORT).show()
        }

        // 6. Đăng xuất
        itemLogout.setOnClickListener {
            // (Trong thực tế, bạn nên hiển thị một Dialog xác nhận trước khi đăng xuất)
            Toast.makeText(requireContext(), "Đăng xuất...", Toast.LENGTH_SHORT).show()
        }
    }
}