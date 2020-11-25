package com.uhaldejm.myalbum.util

import java.util.*

object StringUtils {
    fun capitalizeFirstLetter(input: String): String {
        val str = input.toLowerCase(Locale.getDefault())
        return str[0].toUpperCase() + str.substring(1)
    }
}