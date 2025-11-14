package com.example.do_an_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import java.text.NumberFormat
import java.util.Locale

class ThongTinHKDatVeFragment : Fragment() {

    private var flight: ChuyenBay? = null
    private lateinit var db: DatabaseHelper
    private var currentUserId: Int = -1

    // Khai báo Views
    private lateinit var tvTotalPrice: TextView
    private lateinit var btnContinueToPay: Button
    private lateinit var btnAddPassenger: ImageButton
    private lateinit var btnEditContact: ImageButton

    private lateinit var btnAddSeat: ImageButton
    private lateinit var btnAddBaggage: ImageButton

    private lateinit var tvContactName: TextView
    private lateinit var tvContactEmail: TextView
    private lateinit var tvContactPhone: TextView
    private val viewModel: BookingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DatabaseHelper(requireContext())

        // Lấy ID người dùng (đã lưu khi đăng nhập)
        currentUserId = getSavedUserId()

        arguments?.let {
            flight = it.getSerializable(ARG_FLIGHT) as? ChuyenBay
            flight?.let { f ->
                viewModel.setBaseFlight(f)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.thongtinhkdatve_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ Views
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice)
        btnContinueToPay = view.findViewById(R.id.btnContinueToPay)
        btnAddPassenger = view.findViewById(R.id.btnAddPassenger)
        btnEditContact = view.findViewById(R.id.btnEditContact)

        btnAddSeat = view.findViewById(R.id.btnAddSeat)
        btnAddBaggage = view.findViewById(R.id.btnAddBaggage)

        tvContactName = view.findViewById(R.id.tvContactName)
        tvContactEmail = view.findViewById(R.id.tvContactEmail)
        tvContactPhone = view.findViewById(R.id.tvContactPhone)
        viewModel.totalPrice.observe(viewLifecycleOwner) { newTotal ->
            // Khi tổng tiền (base + baggage + seat) thay đổi, hàm này sẽ tự động chạy
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            tvTotalPrice.text = formatter.format(newTotal)
        }
        loadData()

        btnAddPassenger.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, ThemThongTinHanhKhachFragment())
                addToBackStack(null)
            }
        }

        // Nút "Sửa Liên hệ" (edit)
        btnEditContact.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, ThongTinLienHeFragment())
                addToBackStack(null)
            }
        }

        // 3. Nút "Chọn chỗ ngồi" (+)
        btnAddSeat.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, ChonGheFragment())
                addToBackStack(null)
            }
        }

        // 4. Nút "Chọn Hành lý" (+)
        btnAddBaggage.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, HanhLyFragment())
                addToBackStack(null)
            }
        }

        // 5. Nút "Tiếp tục" (LƯU VÉ VÀO DATABASE)
        btnContinueToPay.setOnClickListener {
            saveTicketToDatabase()
        }
    }

    private fun loadData() {
        flight?.let {
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            tvTotalPrice.text = formatter.format(it.price)
        }
        if (currentUserId != -1) {
            val user = db.getUserById(currentUserId)
            user?.let {
                tvContactName.text = it.name
                tvContactEmail.text = it.email
                tvContactPhone.text = it.phone
            }
        } else {
            tvContactName.text = "Khách vãng lai"
            tvContactEmail.text = "Vui lòng cập nhật"
            tvContactPhone.text = ""
        }
    }

    private fun saveTicketToDatabase() {
        if (currentUserId == -1) {
            Toast.makeText(requireContext(), "Lỗi: Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show()
            return
        }

        flight?.let { chuyenBayDuocChon ->
            val tenHanhKhach = "Nguyễn Nhật Trường (Demo)"
            val finalPrice = viewModel.totalPrice.value ?: chuyenBayDuocChon.price
            val newDatVe = DatVe(
                maDatVe = "VN${System.currentTimeMillis() % 100000}",
                from = chuyenBayDuocChon.departureAirport,
                to = chuyenBayDuocChon.arrivalAirport,
                date = chuyenBayDuocChon.date,
                departureTime = chuyenBayDuocChon.departureTime,
                airline = chuyenBayDuocChon.airline,
                flightCode = chuyenBayDuocChon.flightCode,
                tenHanhKhach = tenHanhKhach,
                giaVe = finalPrice,//DÙNG GIÁ TỔNG
                status = "Đã hoàn thành"
            )

            val result = db.addDatVe(newDatVe, currentUserId)

            if (result > -1) {
                Toast.makeText(requireContext(), "Đặt vé thành công!", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
                Toast.makeText(requireContext(), "Đặt vé thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSavedUserId(): Int {
        val prefs = requireActivity().getSharedPreferences("FlightAppPrefs", Context.MODE_PRIVATE)
        return prefs.getInt("USER_ID", -1)
    }

    companion object {
        private const val ARG_FLIGHT = "arg_flight"
        fun newInstance(flight: ChuyenBay?): ThongTinHKDatVeFragment {
            val fragment = ThongTinHKDatVeFragment()
            val args = Bundle()
            args.putSerializable(ARG_FLIGHT, flight)
            fragment.arguments = args
            return fragment
        }
    }
}