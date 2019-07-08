package com.tekprosoft.busride.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tekprosoft.busride.R
import com.tekprosoft.busride.api.ResponseBody
import com.tekprosoft.busride.data.SampleData
import com.tekprosoft.busride.model.BusSearchResponse
import com.tekprosoft.busride.util.AppConstants.Companion.STATUS_DATA_FOUND
import com.tekprosoft.busride.util.DatePickerFragment
import com.tekprosoft.busride.util.Helpers


class BusSearchFragment : Fragment() {

    private lateinit var mOrigin: Spinner
    private lateinit var mDestination: Spinner
    private lateinit var mDate: EditText
    private lateinit var mSearchBus: Button

    private var listener: OnBusSearchFragmentInteractionListener? = null

    private lateinit var viewModel: BusSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bus_search_fragment, container, false)
        mOrigin = view.findViewById(R.id.origin)
        mDestination = view.findViewById(R.id.destination)
        mDate = view.findViewById<EditText>(R.id.date)
        mSearchBus = view.findViewById(R.id.search)

        initCities()
        mDate.setOnClickListener {
            showDatePicker()
        }
        mSearchBus.setOnClickListener { searchBuses() }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBusSearchFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun showDatePicker() {
        val fragment = DatePickerFragment(mDate)
        fragment.show(activity!!.supportFragmentManager,"Pick a Date")
    }

    private fun initCities() {
        val mAdapter = ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item, SampleData.getCities())
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mOrigin.adapter = mAdapter
        mDestination.adapter = mAdapter
    }

    private fun searchBuses() {
        if (mDate.text.toString().isEmpty()){
            mDate.error = "Please Select a Date"
            return
        }
        viewModel.searchBuses(mDate.text.toString(), mOrigin.selectedItem.toString(), mDestination.selectedItem.toString())

        mSearchBus.text = getString(R.string.searching)
        mSearchBus.isEnabled = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel(){
        val observer = Observer<ResponseBody<BusSearchResponse>> {
            resetSearchButton()

            if (it.body != null){
                if (it.body!!.status == STATUS_DATA_FOUND){
                    it.body!!.origin = mOrigin.selectedItem.toString()
                    it.body!!.dest = mDestination.selectedItem.toString()
                    listener?.onBusSearchFragmentInteraction(it.body!!)
                }else{
                    Helpers.showInfoDialog(context!!, it.body!!.message)
                }
            }else{
                Toast.makeText(context, it.error!!.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel = ViewModelProviders.of(this).get(BusSearchViewModel::class.java)
        viewModel.mBusSearchLiveData.observe(this, observer)
    }

    private fun resetSearchButton(){
        mSearchBus.text = getString(R.string.search_buses)
        mSearchBus.isEnabled = true
    }

    interface OnBusSearchFragmentInteractionListener {
        fun onBusSearchFragmentInteraction(busDat: BusSearchResponse)
    }

}
