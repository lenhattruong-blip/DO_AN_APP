package com.example.do_an_app

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.util.Locale

class TimKiemFragment: Fragment() {
    private val airports = arrayOf("Hồ Chí Minh (SGN)", "Hà Nội (HAN)", "Đà Nẵng (DAD)", "Phú Quốc (PQC)", "Nha Trang (CXR)")
    private val passengerOptions = arrayOf("1 Người lớn", "2 Người lớn", "1 Người lớn, 1 Trẻ em")
    private val classOptions = arrayOf("Phổ thông", "Thương gia", "Hạng nhất")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timkiem_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvFrom = view.findViewById<TextView>(R.id.tvFrom)
        val tvTo = view.findViewById<TextView>(R.id.tvTo)
        val tvDepartureDate = view.findViewById<TextView>(R.id.tvDepartureDate)
        val tvReturnDate = view.findViewById<TextView>(R.id.tvReturnDate)
        val layoutReturnDate = view.findViewById<LinearLayout>(R.id.layoutReturnDate)
        val tvPassengers = view.findViewById<TextView>(R.id.tvPassengers)
        val tvClass = view.findViewById<TextView>(R.id.tvClass)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        val rgTripType = view.findViewById<RadioGroup>(R.id.rgTripType)


        rgTripType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbRoundTrip) {
                layoutReturnDate.visibility = View.VISIBLE
            } else {
                layoutReturnDate.visibility = View.GONE
            }
        }
        tvFrom.setOnClickListener {
            showSelectionDialog("Chọn điểm đi", airports, tvFrom)
        }

        tvTo.setOnClickListener {
            showSelectionDialog("Chọn điểm đến", airports, tvTo)
        }

        tvPassengers.setOnClickListener {
            showSelectionDialog("Chọn hành khách", passengerOptions, tvPassengers)
        }

        tvClass.setOnClickListener {
            showSelectionDialog("Chọn hạng vé", classOptions, tvClass)
        }

        tvDepartureDate.setOnClickListener {
            showDatePickerDialog(tvDepartureDate)
        }
        tvReturnDate.setOnClickListener {
            showDatePickerDialog(tvReturnDate)
        }

        btnSearch.setOnClickListener {
            // 1. Lấy dữ liệu người dùng đã nhập
            val from = tvFrom.text.toString()
            val to = tvTo.text.toString()
            val departureDate = tvDepartureDate.text.toString()
            val returnDate = tvReturnDate.text.toString()
            val isRoundTrip = (rgTripType.checkedRadioButtonId == R.id.rbRoundTrip)

            // 2. Kiểm tra (Validate)
            if (from.isBlank() || to.isBlank() || departureDate.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng nhập Điểm đi, Điểm đến và Ngày đi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isRoundTrip && returnDate.isBlank()) {
                Toast.makeText(requireContext(), "Vui lòng chọn Ngày về cho vé khứ hồi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (from == to) {
                Toast.makeText(requireContext(), "Điểm đi và điểm đến không được trùng nhau", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. GỌI HÀM newInstance MỚI (với 5 tham số)
            val resultsFragment = DSCBTimKiemFragment.newInstance(
                from,
                to,
                departureDate,
                if (isRoundTrip) returnDate else null, // Chỉ gửi ngày về nếu là khứ hồi
                isRoundTrip
            )

            // 4. Mở Fragment kết quả
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, resultsFragment)
                addToBackStack(null)
            }
        }
    }

    private fun showSelectionDialog(title: String, options: Array<String>, targetTextView: TextView) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setItems(options) { dialog, which ->
            targetTextView.text = options[which]
            dialog.dismiss()
        }
        builder.create().show()
    }
    private fun showDatePickerDialog(targetTextView: TextView) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                targetTextView.text = dateFormat.format(selectedDate.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
}