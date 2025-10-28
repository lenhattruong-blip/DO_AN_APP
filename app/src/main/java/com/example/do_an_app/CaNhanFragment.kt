package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class CaNhanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.canhan_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Mở hồ sơ cá nhân", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<CardView>(R.id.itemPayment).setOnClickListener {
            Toast.makeText(requireContext(), "Đi đến: Thanh toán", Toast.LENGTH_SHORT).show()
            
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            
            fragmentTransaction.replace(R.id.fragment_container, TheCuaToiFragment())
            fragmentTransaction.addToBackStack(null) 
            fragmentTransaction.commit()
        }

        view.findViewById<CardView>(R.id.itemPassenger).setOnClickListener {
            Toast.makeText(requireContext(), "Đi đến: Thông tin hành khách", Toast.LENGTH_SHORT).show()

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, ThongTinHanhKhachFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.findViewById<CardView>(R.id.itemSupport).setOnClickListener {
            Toast.makeText(requireContext(), "Đi đến: Trung tâm hỗ trợ", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, HoTroFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.findViewById<CardView>(R.id.itemSettings).setOnClickListener {
            Toast.makeText(requireContext(), "Đi đến: Cài đặt", Toast.LENGTH_SHORT).show()
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment_container, CaiDatFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}