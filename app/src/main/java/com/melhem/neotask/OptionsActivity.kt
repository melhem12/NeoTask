package com.melhem.neotask

import android.content.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.melhem.neotask.Models.Options
import com.melhem.neotask.adapters.OptionsAdapter
import com.melhem.neotask.databinding.ActivityMainBinding
import com.melhem.neotask.databinding.ActivityOptionsBinding
import com.melhem.neotask.repositories.MainRepository
import com.melhem.neotask.service.RetrofitService
import com.melhem.neotask.utils.OnItemClick
import com.melhem.neotask.view_model.MainViewModel
import com.melhem.neotask.view_model.MyViewModelFactory

class OptionsActivity : AppCompatActivity(), OnItemClick {

    private lateinit var binding: ActivityOptionsBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.cont.isClickable=false
        binding.cont.isEnabled=false
//LocalBroadcastManager.getInstance(this).registerReceiver(
//    selected, IntentFilter("ss"))
       val name = intent.extras?.get("name")
        val id = intent.extras?.get("id")

        val balance = intent.extras?.get("balance")
        val created = intent.extras?.get("created")

        val investmentType = intent.extras?.get("investment_type")
        val modifiedRiskScore = intent.extras?.get("modified_risk_score")


        viewModel = ViewModelProvider(this, MyViewModelFactory(
            MainRepository(
                retrofitService
            )
        )
        ).get(MainViewModel::class.java)


        viewModel.optionsList.observe(this, Observer {
            Log.d(ContentValues.TAG, "onCreate: $it")
           val o= OptionsAdapter(this,this)
            o.options= it as ArrayList<Options>
            binding.viewPager.adapter = o



        })

        viewModel.errorMessageOptions.observe(this, Observer {

        })
        viewModel.getAllOptions()

        binding.cont.setOnClickListener{
            val intent = Intent(this, Historycal::class.java)
            intent.putExtra("id",id.toString())
            intent.putExtra("created",created.toString())
            intent.putExtra("investment_type",investmentType.toString())
            intent.putExtra("name",name.toString())
            intent.putExtra("modified_risk_score",modifiedRiskScore.toString())
            startActivity(intent)
        }

    }
// private  val  selected= object  :BroadcastReceiver(){
//     override fun onReceive(context: Context?, intent: Intent?) {
//        var select= intent?.getBooleanExtra("sel",false)
//         if(select == true){
//             Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
//           binding.cont.isClickable= true
//             binding.cont.isEnabled=true

//         }
//     }
// }

    override fun onClick(value: Int?) {
        if (value != null) {
            if (value > -1) {
                binding.cont.isClickable = true
                binding.cont.isEnabled = true
                binding.cont.setBackgroundColor(Color.BLUE)
            }
        }

    }
}
