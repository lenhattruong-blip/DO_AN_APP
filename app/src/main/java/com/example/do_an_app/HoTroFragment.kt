package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class HoTroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trungtamhotro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val contactButton = view.findViewById<CardView>(R.id.btnContact)
        val link1 = view.findViewById<TextView>(R.id.link1)
        val link2 = view.findViewById<TextView>(R.id.link2)
        val link3 = view.findViewById<TextView>(R.id.link3)
        val link4 = view.findViewById<TextView>(R.id.link4)
        val link5 = view.findViewById<TextView>(R.id.link5)


        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        contactButton.setOnClickListener {
            Toast.makeText(requireContext(), "Mở trang liên hệ...", Toast.LENGTH_SHORT).show()
        }

        link1.setOnClickListener {
            Toast.makeText(requireContext(), "Mở: Cách đổi lịch vé", Toast.LENGTH_SHORT).show()
        }

        link2.setOnClickListener {
            Toast.makeText(requireContext(), "Mở: Đặt chỗ an toàn", Toast.LENGTH_SHORT).show()
        }

        link3.setOnClickListener {
            Toast.makeText(requireContext(), "Mở: Cách hủy vé", Toast.LENGTH_SHORT).show()
        }

        link4.setOnClickListener {
            Toast.makeText(requireContext(), "Mở: Cách sửa tên", Toast.LENGTH_SHORT).show()
        }

        link5.setOnClickListener {
            Toast.makeText(requireContext(), "Mở: Cách hủy và hoàn tiền", Toast.LENGTH_SHORT).show()
        }
    }
}