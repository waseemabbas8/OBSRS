package com.tekprosoft.busride

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tekprosoft.busride.model.Bus
import com.tekprosoft.busride.model.BusSearchResponse
import com.tekprosoft.busride.model.Seat
import com.tekprosoft.busride.ui.main.*
import com.tekprosoft.busride.ui.main.BusSearchFragment.OnBusSearchFragmentInteractionListener
import com.tekprosoft.busride.util.AppConstants.Companion.SEAT_RESERVED

class MainActivity : AppCompatActivity() , OnBusSearchFragmentInteractionListener,
    BusListFragment.OnBusListFragmentInteractionListener, BusDetailFragment.OnBusDetailFragmentInteractionListener{

    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNavController = findNavController(R.id.nav_host)
    }

    override fun onBusSearchFragmentInteraction(busData: BusSearchResponse) {
        val action = BusSearchFragmentDirections.actionBusSearchDestToDestBusList(busData)
        mNavController.navigate(action)
    }

    override fun onBusListFragmentInteraction(item: Bus) {
        val action2 = BusListFragmentDirections.actionDestBusListToBustDetailDest(item)
        mNavController.navigate(action2)
    }

    override fun onBusDetailFragmentInteraction(item: Seat) {
        if(item.isReserved != SEAT_RESERVED){
            val action3 = BusDetailFragmentDirections.actionBustDetailDestToBookingDest(item)
            mNavController.navigate(action3)
        }else{
            Toast.makeText(this, "Sorry! the seat is already reserved", Toast.LENGTH_LONG).show()
        }
    }

}