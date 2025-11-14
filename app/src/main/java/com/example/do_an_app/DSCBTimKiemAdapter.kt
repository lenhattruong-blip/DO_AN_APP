package com.example.do_an_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class DSCBTimKiemAdapter(
    private val items: List<ChuyenBay>,
    private val listener: Listener
) : RecyclerView.Adapter<DSCBTimKiemAdapter.FlightResultVH>() {

    interface Listener {
        fun onItemClick(flight: ChuyenBay)
    }

    inner class FlightResultVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAirline: TextView = itemView.findViewById(R.id.tvAirline)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvDepartureTime: TextView = itemView.findViewById(R.id.tvDepartureTime)
        val tvDepartureCode: TextView = itemView.findViewById(R.id.tvDepartureCode)
        val tvArrivalCode: TextView = itemView.findViewById(R.id.tvArrivalCode)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chuyenbaytimkiem, parent, false)
        return FlightResultVH(v)
    }

    override fun onBindViewHolder(holder: FlightResultVH, position: Int) {
        val p = items[position]

        holder.tvAirline.text = p.airline
        holder.tvDepartureTime.text = p.departureTime
        holder.tvDepartureCode.text = p.departureAirport
        holder.tvArrivalCode.text = p.arrivalAirport

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        holder.tvPrice.text = formatter.format(p.price)

        holder.itemView.contentDescription = "Chuyến bay ${p.airline} lúc ${p.departureTime} giá ${holder.tvPrice.text}"
    }

    override fun getItemCount(): Int = items.size
}