package com.example.do_an_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.appbar.MaterialToolbar

class BoLocFragment : Fragment() {

    private val viewModel: BookingViewModel by activityViewModels()

    // Views
    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnSave: Button
    private lateinit var tvReset: TextView
    private lateinit var cbVietjet: CheckBox
    private lateinit var cbBamboo: CheckBox
    private lateinit var cbVietnamAirlines: CheckBox

    //Khai báo CheckBox giá
    private lateinit var cbPrice1: CheckBox
    private lateinit var cbPrice2: CheckBox
    private lateinit var cbPrice3: CheckBox
    private lateinit var cbPrice4: CheckBox

    // Dữ liệu giả tên các hãng
    private val VIETJET = "Vietjet Air"
    private val BAMBOO = "Bamboo Airways"
    private val VNA = "Vietnam Airlines"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.boloc_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ Views
        toolbar = view.findViewById(R.id.toolbar)
        btnSave = view.findViewById(R.id.btnSaveFilter)
        tvReset = view.findViewById(R.id.tvResetAirlines)
        cbVietjet = view.findViewById(R.id.cbVietjet)
        cbBamboo = view.findViewById(R.id.cbBamboo)
        cbVietnamAirlines = view.findViewById(R.id.cbVietnamAirlines)
        cbPrice1 = view.findViewById(R.id.cbPriceRange1)
        cbPrice2 = view.findViewById(R.id.cbPriceRange2)
        cbPrice3 = view.findViewById(R.id.cbPriceRange3)
        cbPrice4 = view.findViewById(R.id.cbPriceRange4)

        // Load cài đặt lọc hiện tại từ ViewModel
        loadCurrentFilters()

        // Gán sự kiện
        toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        tvReset.setOnClickListener {
            viewModel.resetFilters()
            loadCurrentFilters() // Tải lại UI về mặc định
        }

        btnSave.setOnClickListener {
            saveFiltersToViewModel()
            requireActivity().supportFragmentManager.popBackStack() // Đóng trang lọc
        }
    }

    private fun loadCurrentFilters() {
        // Lấy danh sách hãng bay đã chọn
        val currentAirlines = viewModel.selectedAirlines.value ?: emptySet()
        cbVietjet.isChecked = currentAirlines.contains(VIETJET)
        cbBamboo.isChecked = currentAirlines.contains(BAMBOO)
        cbVietnamAirlines.isChecked = currentAirlines.contains(VNA)

        // Lấy khoảng giá đã chọn
        val currentRanges = viewModel.selectedPriceRanges.value ?: emptySet()
        cbPrice1.isChecked = currentRanges.contains(BookingViewModel.PRICE_RANGE_1)
        cbPrice2.isChecked = currentRanges.contains(BookingViewModel.PRICE_RANGE_2)
        cbPrice3.isChecked = currentRanges.contains(BookingViewModel.PRICE_RANGE_3)
        cbPrice4.isChecked = currentRanges.contains(BookingViewModel.PRICE_RANGE_4)
    }

    private fun saveFiltersToViewModel() {
        // 1. Tạo Set (danh sách) hãng bay mới
        val selectedAirlines = mutableSetOf<String>()
        if (cbVietjet.isChecked) selectedAirlines.add(VIETJET)
        if (cbBamboo.isChecked) selectedAirlines.add(BAMBOO)
        if (cbVietnamAirlines.isChecked) selectedAirlines.add(VNA)

        // 2. Lấy giá
        val selectedRanges = mutableSetOf<String>()
        if (cbPrice1.isChecked) selectedRanges.add(BookingViewModel.PRICE_RANGE_1)
        if (cbPrice2.isChecked) selectedRanges.add(BookingViewModel.PRICE_RANGE_2)
        if (cbPrice3.isChecked) selectedRanges.add(BookingViewModel.PRICE_RANGE_3)
        if (cbPrice4.isChecked) selectedRanges.add(BookingViewModel.PRICE_RANGE_4)

        // 3. Gọi ViewModel
        viewModel.setFilters(selectedAirlines, selectedRanges)
    }
}