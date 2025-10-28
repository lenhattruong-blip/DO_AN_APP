package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class TheCuaToiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.thecuatoi_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoCard = view.findViewById<CardView>(R.id.cardInfo)
        val addCardButton = view.findViewById<Button>(R.id.btnAddCard)

        // 1. Gán sự kiện click cho CardView thông tin
        infoCard.setOnClickListener {
            // Hiển thị thông báo nhanh (Toast)
            Toast.makeText(requireContext(), "Mở thông tin chi tiết", Toast.LENGTH_SHORT).show()
        }

        // 2. Gán sự kiện click cho nút "Thêm thẻ"
        addCardButton.setOnClickListener {
            // Hiển thị thông báo nhanh (Toast)
            Toast.makeText(requireContext(), "Mở form thêm thẻ mới", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, ThemTheFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}