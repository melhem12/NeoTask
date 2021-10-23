package com.melhem.neotask

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.melhem.neotask.adapters.MainAdapter
import com.melhem.neotask.databinding.ActivityMainBinding
import com.melhem.neotask.repositories.MainRepository
import com.melhem.neotask.service.RetrofitService
import com.melhem.neotask.utils.OnItemClick
import com.melhem.neotask.view_model.MainViewModel
import com.melhem.neotask.view_model.MyViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.title="Home"
        supportActionBar?.title="Home"
        viewModel = ViewModelProvider(this, MyViewModelFactory(
            MainRepository(
                retrofitService
            )
        )).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

        viewModel.portfolioList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setPortFolioList(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllPortFolios()

    }
}