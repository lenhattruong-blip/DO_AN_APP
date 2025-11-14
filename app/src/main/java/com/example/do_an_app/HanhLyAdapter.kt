package com.example.do_an_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class HanhLyAdapter(
    private val options: List<HanhLy>,
    private val listener: (HanhLy) -> Unit
) : RecyclerView.Adapter<HanhLyAdapter.BaggageViewHolder>() {

    private var selectedPosition = -1

    inner class BaggageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWeight: TextView = itemView.findViewById(R.id.tvBaggageWeight)
        val tvPrice: TextView = itemView.findViewById(R.id.tvBaggagePrice)
        val card: CardView = itemView.findViewById(R.id.cardBaggage)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedPosition)
                    selectedPosition = position
                    notifyItemChanged(selectedPosition)

                    listener(options[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaggageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hanhly, parent, false)
        return BaggageViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaggageViewHolder, position: Int) {
        val option = options[position]
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

        holder.tvWeight.text = "+${option.weight} kg"
        holder.tvPrice.text = formatter.format(option.price)
        if (position == selectedPosition) {
            holder.card.setCardBackgroundColor(Color.parseColor("#3498DB"))
            holder.tvWeight.setTextColor(Color.WHITE)
            holder.tvPrice.setTextColor(Color.WHITE)
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor("#E0E0E0"))
            holder.tvWeight.setTextColor(Color.BLACK)
            holder.tvPrice.setTextColor(Color.BLACK)
        }
    }
    fun setCurrentPrice(price: Double) {
        // Tìm vị trí (index) của giá tiền trong danh sách
        val index = options.indexOfFirst { it.price == price }
        if (index != -1) {
            selectedPosition = index
            notifyItemChanged(index) // Cập nhật màu cho item đó
        }
    }
    override fun getItemCount() = options.size
}