package com.tekprosoft.busride.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tekprosoft.busride.R
import com.tekprosoft.busride.model.Bus

class BusListAdapter(private val busList: List<Bus>, private val listener: BusListFragment.OnBusListFragmentInteractionListener?)
    : RecyclerView.Adapter<BusListAdapter.BusVH>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Bus
            listener?.onBusListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bus_list, parent, false)
        return BusVH(view)
    }

    override fun getItemCount(): Int {
        return busList.size
    }

    override fun onBindViewHolder(holder: BusVH, position: Int) {
        val item = busList[position]
        holder.onBind(item)

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }


    class BusVH(val view: View) : RecyclerView.ViewHolder(view) {
        private val mBusNo: TextView = view.findViewById(R.id.txt_bus_no)
        private val mBusCompany: TextView = view.findViewById(R.id.txt_company_name)
        private val mDepartureTime: TextView = view.findViewById(R.id.txt_departure)
        private val mTicketPrice: TextView = view.findViewById(R.id.txt_ticket_price)
        private val mTravellingHours: TextView = view.findViewById(R.id.txt_travelling_hours)

        fun onBind(item: Bus){
            mBusNo.text = item.busNumber
            mBusCompany.text = item.company
            mDepartureTime.text = "Departure: " + item.departureDate + "," + item.departureTime
            mTicketPrice.text = item.ticketPrice + " PKR"
            mTravellingHours.text = item.totalJourneyTime
        }
    }
}