package com.tekprosoft.busride.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tekprosoft.busride.R
import com.tekprosoft.busride.model.Bus
import com.tekprosoft.busride.model.BusSearchResponse


class BusListFragment : Fragment() {

    private var busData: BusSearchResponse? = null
    private var listener: OnBusListFragmentInteractionListener? = null

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BusListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = BusListFragmentArgs.fromBundle(it)
            busData = args.busData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bus_list, container, false)
        mRecyclerView = view.findViewById(R.id.bus_list_rv)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = BusListAdapter(busData?.data!!, listener!!)
        mRecyclerView.adapter = mAdapter

        val mRouteLabel: TextView = view.findViewById(R.id.label_route)
        mRouteLabel.text = busData?.origin + " to " + busData?.dest

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBusListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnBusListFragmentInteractionListener {
        fun onBusListFragmentInteraction(item: Bus)
    }

}
