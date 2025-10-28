package com.example.do_an_app // Ghi chú: Gói (package) của bạn có thể khác

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class MatKhauVabaoMatFragment : Fragment() { // ĐÃ ĐỔI TÊN LỚP

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Load file layout
        return inflater.inflate(R.layout.matkhauvabaomat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tìm các View trong layout
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val pinLink = view.findViewById<TextView>(R.id.tvPinLink)
        val switchBiometric = view.findViewById<SwitchMaterial>(R.id.switchBiometric)
        val switchDevice = view.findViewById<SwitchMaterial>(R.id.switchDevice)
        val deleteLink = view.findViewById<TextView>(R.id.tvDeleteLink)
        val itemDelete = view.findViewById<ConstraintLayout>(R.id.itemDelete)


        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        pinLink.setOnClickListener {
            Toast.makeText(requireContext(), "Mở trang thiết lập PIN", Toast.LENGTH_SHORT).show()
        }

        deleteLink.setOnClickListener {
            showDeleteToast()
        }
        itemDelete.setOnClickListener {
             showDeleteToast()
        }

        switchBiometric.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(requireContext(), "Đã BẬT xác thực sinh trắc học", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Đã TẮT xác thực sinh trắc học", Toast.LENGTH_SHORT).show()
            }
        }

        switchDevice.setOnCheckedChangeListener { buttonView, isChecked ->
             if (isChecked) {
                Toast.makeText(requireContext(), "Đã BẬT thiết bị đáng tin", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Đã TẮT thiết bị đáng tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun showDeleteToast() {
        Toast.makeText(requireContext(), "Yêu cầu xóa tài khoản...", Toast.LENGTH_SHORT).show()
    }
}