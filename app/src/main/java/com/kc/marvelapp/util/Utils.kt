package com.kc.marvelapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object Utils {
    fun getDate(input: String, inDateFormat: String = Constant.UTC_DATE_FORMAT, outDateFormat: String = Constant.CHAR_DATE_FORMAT): String{
        var result = ""
        try {
            val inputDateFormat = SimpleDateFormat(inDateFormat, Locale.getDefault())
            val date = inputDateFormat.parse(input)
            val reqDateFormat = SimpleDateFormat(outDateFormat, Locale.getDefault())
            result = date?.let { reqDateFormat.format(it) } ?: run { "" }
        } catch (e: ParseException){
            e.printStackTrace()
        }
        return result
    }
}