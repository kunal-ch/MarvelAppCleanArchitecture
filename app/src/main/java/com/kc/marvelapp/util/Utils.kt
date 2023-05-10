package com.kc.marvelapp.util

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun getDate(input: String): String{
        var result = ""
        try {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            val date = inputDateFormat.parse(input)
            val reqDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
            result = date?.let { reqDateFormat.format(it) } ?: run { "" }
        } catch (e: ParseException){
            e.printStackTrace()
        }
        return result
    }
}