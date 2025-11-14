package com.example.do_an_app

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import java.text.NumberFormat
import java.util.Locale

class ChiTietChuyenBayFragment : Fragment(R.layout.chitietchuyenbay_fragment) {

    private var flight: ChuyenBay? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            flight = it.getSerializable(ARG_FLIGHT) as? ChuyenBay
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnConfirm: Button = view.findViewById(R.id.btnConfirm)
        val tvAirline: TextView = view.findViewById(R.id.tvDetailAirline)
        val tvDate: TextView = view.findViewById(R.id.tvDetailDate)
        val tvPrice: TextView = view.findViewById(R.id.tvDetailPrice)
        val tvDepTime: TextView = view.findViewById(R.id.tvDetailDepTime)
        val tvDepCode: TextView = view.findViewById(R.id.tvDetailDepCode)
        val tvArrTime: TextView = view.findViewById(R.id.tvDetailArrTime)
        val tvArrCode: TextView = view.findViewById(R.id.tvDetailArrCode)
        val tvFlightCode: TextView = view.findViewById(R.id.tvDetailFlightCode)
        flight?.let {
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

            tvAirline.text = it.airline
            tvDate.text = it.date
            tvPrice.text = formatter.format(it.price)
            tvDepTime.text = it.departureTime
            tvDepCode.text = it.departureAirport
            tvArrTime.text = it.arrivalTime
            tvArrCode.text = it.arrivalAirport
            tvFlightCode.text = "${it.airline} (${it.flightCode})"

        }

        btnConfirm.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, ThongTinHKDatVeFragment.newInstance(flight))
                addToBackStack(null)
            }
        }
    }
    companion object {
        private const val ARG_FLIGHT = "arg_flight"

        fun newInstance(flight: ChuyenBay): ChiTietChuyenBayFragment {
            val fragment = ChiTietChuyenBayFragment()
            val args = Bundle()
            args.putSerializable(ARG_FLIGHT, flight)
            fragment.arguments = args
            return fragment
        }
    }
}