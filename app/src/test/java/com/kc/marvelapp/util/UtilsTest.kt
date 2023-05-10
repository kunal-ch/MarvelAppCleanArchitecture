package com.kc.marvelapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilsTest {
    @Test
    fun getDateValid(){
        val input = "2013-10-24T14:32:08-0400"
        val output = "October 25, 2013"
        val result = Utils.getDate(input)
        assertThat(result).isEqualTo(output)
    }

    @Test
    fun getDateInvalid(){
        val input = "2014-10-24T14:32:08-0400"
        val output = "October 25, 2013"
        val result = Utils.getDate(input)
        assertThat(result).isNotEqualTo(output)
    }

    @Test
    fun getDateEmpty(){
        val input = ""
        val result = Utils.getDate(input)
        assertThat(result).isEmpty()
    }

    @Test
    fun getDateInvalidInput(){
        val input = "12"
        val output = "October 25, 2013"
        val result = Utils.getDate(input)
        assertThat(result).isNotEqualTo(output)
    }
}