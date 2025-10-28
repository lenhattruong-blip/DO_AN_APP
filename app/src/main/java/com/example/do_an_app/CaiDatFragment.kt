package com.example.do_an_app

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

        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        itemAccountInfo.setOnClickListener {
            Toast.makeText(requireContext(), "Mở Thông tin tài khoản", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, ThongTinTaiKhoanFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        itemPassword.setOnClickListener {
            Toast.makeText(requireContext(), "Mở Mật khẩu & Bảo mật", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, MatKhauVabaoMatFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        itemCurrency.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Tiền tệ", Toast.LENGTH_SHORT).show()
        }

        itemLanguage.setOnClickListener {
            Toast.makeText(requireContext(), "Mở chọn Ngôn ngữ", Toast.LENGTH_SHORT).show()
        }

        itemLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Đăng xuất...", Toast.LENGTH_SHORT).show()
        }
    }
}