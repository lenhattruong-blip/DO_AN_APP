package com.example.do_an_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DatVeAdapter(private val bookingList: List<DatVe>) :
    RecyclerView.Adapter<DatVeAdapter.BookingViewHolder>() {
    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStatus: TextView = itemView.findViewById(R.id.tvBookingStatus)
        val tvAirline: TextView = itemView.findViewById(R.id.tvAirline)
        val tvFrom: TextView = itemView.findViewById(R.id.tvFrom)
        val tvTo: TextView = itemView.findViewById(R.id.tvTo)
        val tvDepartureDate: TextView = itemView.findViewById(R.id.tvDepartureDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_datve, parent, false)
        return BookingViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]

        holder.tvFrom.text = booking.from
        holder.tvTo.text = booking.to
        holder.tvDepartureDate.text = "Ngày đi: ${booking.date}"
        holder.tvAirline.text = booking.airline
        holder.tvStatus.text = booking.status
        if (booking.status.equals("Đã hủy", ignoreCase = true)) {
            holder.tvStatus.setBackgroundColor(Color.RED)
        } else {
            holder.tvStatus.setBackgroundColor(Color.GREEN)
        }
    }
    override fun getItemCount() = bookingList.size
}