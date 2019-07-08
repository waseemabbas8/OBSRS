package com.tekprosoft.busride.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tekprosoft.busride.R
import com.tekprosoft.busride.model.Seat
import com.tekprosoft.busride.util.AppConstants.Companion.SEAT_RESERVED

class SeatsAdapter(private val seats: List<Seat>, private val listener: BusDetailFragment.OnBusDetailFragmentInteractionListener)
    : RecyclerView.Adapter<SeatsAdapter.SeatVH>(){

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Seat
            listener.onBusDetailFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seat_list, parent, false)
        return SeatVH(view)
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    override fun onBindViewHolder(holder: SeatVH, position: Int) {
        val item = seats[position]
        holder.onBind(item)
        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun compareSeats(liveSeats: List<Seat>){
        for(seat in seats){
            for (liveSeat in liveSeats){
                if (seat.seatNumber == liveSeat.seatNumber){
                    seat.isReserved = liveSeat.isReserved
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class SeatVH(val view: View) : RecyclerView.ViewHolder(view) {

        private val mSeatNo: TextView = view.findViewById(R.id.seat_no)
        private val mSeatCardView: CardView = view.findViewById(R.id.seat_cv)

        fun onBind(item: Seat){
            mSeatNo.text = item.seatNumber
            if (item.isReserved == SEAT_RESERVED){
                mSeatCardView.setBackgroundColor(Color.GREEN)
            }else{
                mSeatCardView.setBackgroundColor(Color.WHITE)
            }
        }

    }

}