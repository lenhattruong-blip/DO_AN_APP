package com.example.do_an_app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DSCBTimKiemFragment : Fragment(R.layout.dscbtimkiem_fragment) {

    // Biến cho ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: ViewPagerAdapter

    // Biến nhận dữ liệu tìm kiếm
    private var searchFrom: String? = null
    private var searchTo: String? = null
    private var departureDate: String? = null
    private var returnDate: String? = null // Có thể null (nếu 1 chiều)
    private var isRoundTrip: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Nhận 5 tham số
        arguments?.let {
            searchFrom = it.getString(ARG_FROM)
            searchTo = it.getString(ARG_TO)
            departureDate = it.getString(ARG_DATE)
            returnDate = it.getString(ARG_RETURN_DATE) // Có thể null
            isRoundTrip = it.getBoolean(ARG_IS_ROUND_TRIP)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ánh xạ
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
        val btnFilter: Button = view.findViewById(R.id.btnFilter)

        // Nút bộ lọc (giữ nguyên)
        btnFilter.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragment_container, BoLocFragment())
                addToBackStack(null)
            }
        }

        // Thiết lập ViewPager
        setupViewPager()
    }

    private fun setupViewPager() {
        // Tạo Adapter cho ViewPager
        pagerAdapter = ViewPagerAdapter(this)

        // Thêm Fragment Chuyến đi (Bắt buộc)
        pagerAdapter.addFragment(
            DSCBMotChieuFragment.newInstance(searchFrom!!, searchTo!!, departureDate!!)
        )

        // Chỉ thêm Fragment Chuyến về NẾU là khứ hồi
        if (isRoundTrip && returnDate != null) {
            pagerAdapter.addFragment(
                // Chuyến về (Đảo ngược Đi/Đến và dùng Ngày về)
                DSCBMotChieuFragment.newInstance(searchTo!!, searchFrom!!, returnDate!!)
            )
        } else {
            // Nếu 1 chiều thì ẩn thanh Tab đi
            tabLayout.visibility = View.GONE
        }

        // Gán adapter
        viewPager.adapter = pagerAdapter

        // Kết nối TabLayout với ViewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Chuyến bay đi" else "Chuyến bay về"
        }.attach()
    }

    /**
     * Adapter cho ViewPager2 (Giống Lab 6 / Week 6 [cite: 271-287])
     */
    private inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        private val fragments = mutableListOf<Fragment>()

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    /**
     * Companion Object mới (Nhận 5 tham số)
     */
    companion object {
        private const val ARG_FROM = "arg_from"
        private const val ARG_TO = "arg_to"
        private const val ARG_DATE = "arg_date"
        private const val ARG_RETURN_DATE = "arg_return_date"
        private const val ARG_IS_ROUND_TRIP = "arg_is_round_trip"

        fun newInstance(
            from: String,
            to: String,
            date: String,
            returnDate: String?, // Cho phép null
            isRoundTrip: Boolean
        ): DSCBTimKiemFragment {
            val fragment = DSCBTimKiemFragment()
            val args = Bundle()
            args.putString(ARG_FROM, from)
            args.putString(ARG_TO, to)
            args.putString(ARG_DATE, date)
            args.putString(ARG_RETURN_DATE, returnDate)
            args.putBoolean(ARG_IS_ROUND_TRIP, isRoundTrip)
            fragment.arguments = args
            return fragment
        }
    }
}