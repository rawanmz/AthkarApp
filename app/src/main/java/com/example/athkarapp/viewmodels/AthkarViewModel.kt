package com.example.athkarapp.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.athkarapp.model.Athkar
import com.example.athkarapp.model.ResponseAthakr
import com.google.gson.Gson

class AthkarViewModel : ViewModel() {

    val athkarList = mutableStateOf<ResponseAthakr?>(null)



    fun <T> loadJsonData(context: Context, fileName: String, responseClass: Class<T>): T {
        val jsonString = context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }

        return Gson().fromJson(jsonString, responseClass)
    }


    fun setAthkarList(list: List<Athkar>) {

        athkarList.value = ResponseAthakr(list)
    }


}