package com.example.androidappwireframe.view.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import com.example.androidappwireframe.R
import com.example.androidappwireframe.databinding.ActivityRegisterBinding
import com.example.androidappwireframe.databinding.DatepickerBinding
import java.util.*
import android.widget.EditText
import com.example.androidappwireframe.util.Constants
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.example.androidappwireframe.model.entities.PinCode
import com.example.androidappwireframe.viewmodel.PinCodeViewModel
import org.w3c.dom.Text


class RegisterActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var mbinding:ActivityRegisterBinding
    lateinit var dialog:Dialog
    var spinner_val:String="Not selected"
    lateinit var pinCodeViewModel:PinCodeViewModel
    var state:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding= ActivityRegisterBinding.inflate(layoutInflater)
        dialog=Dialog(this)
        setContentView(mbinding.root)
        supportActionBar?.setTitle("Register")

        populateGenderDropDown()
        mbinding.etDob.setOnClickListener(this)
        touchEventListener()
        mbinding.buttoncheck.setOnClickListener(this)
        mbinding.register.setOnClickListener(this)

    }

    // dropdown for gender
    fun populateGenderDropDown(){
       if(mbinding!=null){
           mbinding.spinnerId.onItemSelectedListener=(object:AdapterView.OnItemSelectedListener{
               override fun onItemSelected(
                   parent: AdapterView<*>?,
                   view: View?,
                   position: Int,
                   id: Long
               ) {
                   spinner_val=Constants.GenderList()[position]


               }

               override fun onNothingSelected(parent: AdapterView<*>?) {

               }

           })

           val spin_adapter: ArrayAdapter<String> = ArrayAdapter<String>(
               this@RegisterActivity,
               android.R.layout.simple_spinner_item,
               Constants.GenderList()
           )

           mbinding.spinnerId.adapter=spin_adapter
       }
    }

    // unfocus on touching outside edittext
    fun touchEventListener(){
        if(mbinding!=null){
            mbinding.edPincode.setOnFocusChangeListener( object: View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                  if(!hasFocus ){
                    if(mbinding.edPincode.text.length==6){
                      mbinding.buttoncheck.isEnabled=true
                    }
                      else{
                        mbinding.buttoncheck.isEnabled=false
                      }
                  }
                }

            });
        }
    }



    override fun onClick(v: View?) {
        if(v!=null){
            when(v.id){
                R.id.et_dob->{
                    setDateOfBirth()
                    return
                }
                R.id.buttoncheck->{
                    callApi()
                    return
                }
                R.id.register->{
                    register()
                    return
                }

            }
        }
    }


    fun register(){
        val mobile=mbinding.etMobile.getText().toString()
        val fullname=mbinding.etFullname.getText().toString()
        val gender=mbinding.spinnerId.selectedItem.toString()
        val dob=mbinding.etDob.getText().toString()
        val add1=mbinding.edAddress1.getText().toString()
        val pin=mbinding.edPincode.getText().toString()


                  if(TextUtils.isEmpty(mobile) ||
                    TextUtils.isEmpty(fullname) ||
                    TextUtils.isEmpty(gender) ||
                    TextUtils.isEmpty((dob)) ||
                    TextUtils.isEmpty((add1)) ||
                    TextUtils.isEmpty((pin))
                ){

                      Toast.makeText(this@RegisterActivity,"Please make sure all field are filled properly!",Toast.LENGTH_LONG).show()


                 }else{

                     if(state.length>1){
                         val intent=Intent(this@RegisterActivity,WeatherTodayActivity::class.java)
                         intent.putExtra("state",state)
                         startActivity(intent)
                     }else{
                         Toast.makeText(this@RegisterActivity,"Please press check after entering pin!",Toast.LENGTH_LONG).show()

                     }


                  }

    }


    fun callApi(){
       pinCodeViewModel=ViewModelProvider(this@RegisterActivity).get(PinCodeViewModel::class.java)
        val pin:String=mbinding.edPincode.getText().toString()
        if(!TextUtils.isEmpty(pin) && pin.length==6){
           pinCodeViewModel.getDataFromApi(pin)
        }else{
            Toast.makeText(this@RegisterActivity,"Make sure pin is 6 digits!",Toast.LENGTH_SHORT).show()
        }

        getApiResult()

    }

    fun getApiResult(){
    pinCodeViewModel.response.observe(this,{
        response->
        response.let {
            populateToUI(it)
        }
    })

        pinCodeViewModel.load.observe(this,{
                response->
            response.let {
                Log.i("Pin",it.toString())
            }
        })

        pinCodeViewModel.loadError.observe(this,{
                response->
            response.let {
                Log.i("Pin",it.toString())
            }
        })

    }


    fun populateToUI(pinCodeData:PinCode.PinCodeData){
     Log.i("Pin",pinCodeData.toString())
      val data=pinCodeData[0].PostOffice[0]
        val district=data.District
         state=data.State
        val city=data.Block

        mbinding.district.text="District: $district"
        mbinding.state.text="State: $state"

    }


    //set calender
    fun setDateOfBirth(){

        val datePickerBinding:DatepickerBinding= DatepickerBinding.inflate(layoutInflater)
        dialog.setContentView(datePickerBinding.root)
       dialog.show()

        var myDate:String="Not selected!"

        val today= Calendar.getInstance()
            datePickerBinding.datepicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)){
                view,year,month,day->
                    val month=month+1
               myDate="$day/$month/$year"

        }


        datePickerBinding.doneDate.setOnClickListener{
            mbinding.etDob.setText(myDate.toString())
            dialog.dismiss()
        }




    }

    // event to unfocus editText
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}