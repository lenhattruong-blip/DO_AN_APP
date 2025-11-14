package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class HanhLyFragment : Fragment() {

    private lateinit var rvBaggageOptions: RecyclerView
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnSave: Button
    private lateinit var adapter: HanhLyAdapter
    private val viewModel: BookingViewModel by activityViewModels()
    // Dữ liệu giả
    private val baggageData = listOf(
        HanhLy(0, 0.0),
        HanhLy(20, 453000.0),
        HanhLy(30, 558000.0),
        HanhLy(40, 660000.0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hanhly_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBaggageOptions = view.findViewById(R.id.rvBaggageOptions)
        tvTotalPrice = view.findViewById(R.id.tvTotalBaggagePrice)
        btnSave = view.findViewById(R.id.btnSaveBaggage)

        setupRecyclerView()
        val currentBaggagePrice = viewModel.baggagePrice.value ?: 0.0
        adapter.setCurrentPrice(currentBaggagePrice)
        updateTotalPriceText(currentBaggagePrice)

        btnSave.setOnClickListener {
            Toast.makeText(requireContext(), "Đã lưu hành lý", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = HanhLyAdapter(baggageData) { selectedOption ->
            updateTotalPriceText(selectedOption.price)
            viewModel.updateBaggagePrice(selectedOption.price)
        }
        rvBaggageOptions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvBaggageOptions.adapter = adapter
    }

    private fun updateTotalPriceText(price: Double) {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        tvTotalPrice.text = formatter.format(price)
    }
}