package com.tekprosoft.busride.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.tekprosoft.busride.R
import com.tekprosoft.busride.api.ResponseBody
import com.tekprosoft.busride.model.Bus
import com.tekprosoft.busride.model.Seat
import com.tekprosoft.busride.model.SeatsResponse
import com.tekprosoft.busride.util.AppConstants.Companion.STATUS_DATA_FOUND
import com.tekprosoft.busride.util.Helpers

class BusDetailFragment : Fragment() {

    private var busObj: Bus? = null

    private lateinit var mLeftRecyclerView: RecyclerView
    private lateinit var mRightRecyclerView: RecyclerView
    private lateinit var mBusLabel: TextView
    private lateinit var mProgressbar: ProgressBar

    private var leftAdapter: SeatsAdapter? = null
    private var rightAdapter: SeatsAdapter? = null

    private val leftItems = arrayListOf<Seat>()
    private val rightItems = arrayListOf<Seat>()

    private lateinit var viewModel: BusDetailViewModel
    private var listener: OnBusDetailFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = BusDetailFragmentArgs.fromBundle(it)
            busObj = args.bus
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBusDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bus_detail_fragment, container, false)
        mLeftRecyclerView = view.findViewById(R.id.seats_rv_left)
        mRightRecyclerView = view.findViewById(R.id.seats_rv_right)
        mBusLabel = view.findViewById(R.id.txt_bus_label)
        mProgressbar = view.findViewById(R.id.progress_bar)

        initSeatsData()

        initViewModel()

        viewModel.getSeatsData(busObj!!.id)

        return view
    }

    private fun initSeatsData() {
        mBusLabel.text = busObj?.busNumber + " - " + busObj?.company
    }

    private fun initViewModel(){
        val observer = Observer<ResponseBody<SeatsResponse>> {
            mProgressbar.visibility = View.GONE

            if (it.body != null){
                if (it.body!!.status == STATUS_DATA_FOUND){
                    var listSwitcher = 0

                    for (seat in it.body!!.data){
                        listSwitcher++
                        if (listSwitcher <= 2){
                            leftItems.add(seat)
                        }else{
                            rightItems.add(seat)
                        }

                        if (listSwitcher == 4){
                            listSwitcher = 0
                        }

                    }

                    if (leftAdapter == null){
                        leftAdapter = SeatsAdapter(leftItems, listener!!)
                        mLeftRecyclerView.adapter = leftAdapter
                    }else{
                        leftAdapter?.notifyDataSetChanged()
                    }

                    if (rightAdapter == null){
                        rightAdapter = SeatsAdapter(rightItems, listener!!)
                        mRightRecyclerView.adapter = rightAdapter
                    }else{
                        rightAdapter?.notifyDataSetChanged()
                    }

                }else{
                    Helpers.showInfoDialog(context!!, it.body!!.message)
                }
            }else{
                Toast.makeText(context, it.error!!.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel = ViewModelProviders.of(this).get(BusDetailViewModel::class.java)
        viewModel.seatsLiveData.observe(this, observer)
    }

    interface OnBusDetailFragmentInteractionListener {
        fun onBusDetailFragmentInteraction(item: Seat)
    }

}
