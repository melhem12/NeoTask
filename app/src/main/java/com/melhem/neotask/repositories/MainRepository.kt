package com.melhem.neotask.repositories

import com.melhem.neotask.service.RetrofitService


    class MainRepository constructor(private val retrofitService: RetrofitService) {

        fun getAllPortFolios() = retrofitService.getAllPortFolios()
        fun getAllOptions() = retrofitService.getAllOptions()
        fun getAllHistorical() = retrofitService.getAllHistorical()
    }
