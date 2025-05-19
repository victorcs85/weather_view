package br.com.victorcs.weatherview.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"
private const val PT = "pt"
private const val BR = "BR"
private const val AMERICA_SAO_PAULO = "America/Sao_Paulo"
private const val MILLI_SECONDS = 1000

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale(PT, BR))
    sdf.timeZone = TimeZone.getTimeZone(AMERICA_SAO_PAULO)
    return sdf.format(Date(this * MILLI_SECONDS))
}

fun Double.toCelsius() = this - 273.15