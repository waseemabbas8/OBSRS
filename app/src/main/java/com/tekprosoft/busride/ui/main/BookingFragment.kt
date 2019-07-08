package com.tekprosoft.busride.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tekprosoft.busride.R
import com.tekprosoft.busride.model.Seat
import com.tekprosoft.busride.util.AppConstants
import com.tekprosoft.busride.util.Helpers

class BookingFragment : Fragment() {

    private lateinit var mName: EditText
    private lateinit var mPhone: EditText
    private lateinit var mEmail: EditText
    private lateinit var mSubmit: Button

    private lateinit var viewModel: BookingViewModel

    private lateinit var seatObj: Seat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = BookingFragmentArgs.fromBundle(it)
            seatObj = args.seatObj
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.booking_fragment, container, false)
        mName = view.findViewById(R.id.person_phone)
        mPhone = view.findViewById(R.id.person_phone)
        mEmail = view.findViewById(R.id.person_email)
        mSubmit = view.findViewById(R.id.submit)

        mSubmit.setOnClickListener {
            sendUserInfo()
        }

        return view
    }

    private fun sendUserInfo() {
        val name = mName.text.toString()
        val phone = mPhone.text.toString()
        val email = mEmail.text.toString()
        if (name.isEmpty()){
            mName.error = "Please Enter a Name"
            return
        }

        if (phone.isEmpty() or !Helpers.isNumber(phone)){
            mPhone.error = "Please Enter Valid Phone Number"
            return
        }

        if (email.isEmpty() or !Helpers.isEmailValid(email)){
            mEmail.error = "Please Enter Valid Email"
            return
        }

        mSubmit.text = getString(R.string.please_wait)
        mSubmit.isEnabled = false

        viewModel.confirmBooking(name, phone, email, seatObj.id).observe(this, Observer {

            resetButton()

            if (it.body != null){
                val result = it.body!!
                if (result.status == AppConstants.STATUS_DATA_FOUND){
                    val msg = "Your seat No. " + result.data[0].seatNumber + " against bus No. " + result.data[0].busNumber + "has been confirmed"+
                            " for " + result.data[0].ticketPrice + " PKR"
                    Helpers.showInfoDialog(context!!, msg)
                }else{
                    Helpers.showInfoDialog(context!!, result.message)
                }
            }else{
                Toast.makeText(context, it.error!!.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun resetButton(){
        mSubmit.text = getString(R.string.confirm_booking)
        mSubmit.isEnabled = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingViewModel::class.java)
    }

}
