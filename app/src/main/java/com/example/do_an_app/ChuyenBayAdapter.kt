package com.example.do_an_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class ChuyenBayAdapter(
    private val items: List<ChuyenBay>,
    private val listener: Listener
) : RecyclerView.Adapter<ChuyenBayAdapter.FlightVH>() {

    interface Listener {
        fun onItemClick(flight: ChuyenBay)
    }

    inner class FlightVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRoute: TextView = itemView.findViewById(R.id.tvFlightRoute)
        val tvDate: TextView = itemView.findViewById(R.id.tvFlightDate)
        val tvPrice: TextView = itemView.findViewById(R.id.tvFlightPrice)
        val tvClass: TextView = itemView.findViewById(R.id.tvFlightClass)
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_flight_deal, parent, false)
        return FlightVH(v)
    }

    override fun onBindViewHolder(holder: FlightVH, position: Int) {
        val p = items[position]

        holder.tvRoute.text = "${p.departureAirport} ✈ ${p.arrivalAirport}"
        holder.tvDate.text = p.date

        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        holder.tvPrice.text = formatter.format(p.price)

        holder.tvClass.text = "1 khách - Economy"
        holder.itemView.contentDescription = "Chuyến bay ${p.airline} từ ${p.departureAirport} đến ${p.arrivalAirport} giá ${holder.tvPrice.text}"
    }

    override fun getItemCount(): Int = items.size
}