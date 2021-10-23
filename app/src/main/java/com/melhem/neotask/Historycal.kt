package com.melhem.neotask

import android.content.ContentValues
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.appbar.AppBarLayout
import com.melhem.neotask.Models.History
import com.melhem.neotask.Models.Portfolio

import com.melhem.neotask.databinding.ActivityHistorycalBinding
import com.melhem.neotask.repositories.MainRepository
import com.melhem.neotask.service.RetrofitService
import com.melhem.neotask.view_model.MainViewModel
import com.melhem.neotask.view_model.MyViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.ArrayList


class Historycal : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding :ActivityHistorycalBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorycalBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val id = intent.extras?.get("id")
        val name = intent.extras?.get("name")
        val created = intent.extras?.get("created")
        val investmentType = intent.extras?.get("investment_type")
        val modifiedRiskScore = intent.extras?.get("modified_risk_score")
actionBar?.title=name.toString()
        supportActionBar?.title=name.toString()

       var a= created.toString().substring(0,10)
        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(a , firstApiFormat)

        binding.createdTxt.text=date.dayOfMonth.toString()+" "+date.month.toString()

        binding.idtxt.text=id.toString()
        binding.investmentTypeTxt.text=investmentType.toString().toUpperCase()
        binding.riskScoreTxt.text=modifiedRiskScore.toString()
        viewModel = ViewModelProvider(this, MyViewModelFactory(
            MainRepository(
                retrofitService
            )
        )
        ).get(MainViewModel::class.java)

        viewModel.historyList.observe(this, Observer {
            Log.d(ContentValues.TAG, "onCreate: $it")
            setXAxis(it.size)
            setYAxis()
            initData(it)
        })
        viewModel.errorMessageHistory.observe(this, Observer {

        })
        viewModel.getAllHistorical()

    }
    private fun setXAxis( max:Int) {
        val xAxis: XAxis = binding.linechart.xAxis
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = max.toFloat()-1
        xAxis.labelCount = 0
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
    }
    private fun setYAxis() {
        val axisLeft: YAxis = binding.linechart.getAxisLeft()
        axisLeft.axisMaximum = 20000f
        axisLeft.axisMinimum = 10000f
        binding.linechart.getAxisRight().setEnabled(false)
    }
    private fun initData(data :List<History>) {
        val y = FloatArray(data.size)
        val bv = FloatArray(data.size)
       var i:Int=0
         for (h :History in data){
          y[i]=h.smartWealthValue.toFloat()
             bv[i]=h.benchmarkValue.toFloat()
            i++
         }

        binding.linechart.setExtraOffsets(24f, 24f, 24f, 0f)
        setChartData(y,Color.GREEN,bv,Color.BLACK)

    }

    private fun setChartData(ys1:FloatArray,color:Int,ys2:FloatArray,color2:Int) {

        val yVals1 :  List<Entry>
        yVals1= ArrayList<Entry>()

        for (i in ys1.indices) {
            yVals1.add(Entry(i.toFloat(), ys1[i]))
        }
        val set1 = LineDataSet(yVals1, null)
        //set1.setDrawFilled(t++++++++++rue)
      //  set1.fillColor = Color.WHITE
        set1.color=color



        val yVals2 :  List<Entry>
        yVals2= ArrayList<Entry>()

        for (i in ys2.indices) {
            yVals2.add(Entry(i.toFloat(), ys2[i]))
        }
        val set2 = LineDataSet(yVals2, null)
        set2.fillColor = Color.WHITE
        set2.color=color2


        val lineData = LineData(set1,set2)
        binding.linechart.data=lineData
        binding.linechart.invalidate()

    }

}