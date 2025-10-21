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
import java.util.Locale

class HomeFragment: Fragment() {
    private val airports = arrayOf("Hồ Chí Minh (SGN)", "Hà Nội (HAN)", "Đà Nẵng (DAD)", "Phú Quốc (PQC)", "Nha Trang (CXR)")
    private val passengerOptions = arrayOf("1 Người lớn", "2 Người lớn", "1 Người lớn, 1 Trẻ em")
    private val classOptions = arrayOf("Phổ thông", "Thương gia", "Hạng nhất")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
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
            Toast.makeText(context, "Đang tìm kiếm chuyến bay...", Toast.LENGTH_SHORT).show()
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