package br.com.victorcs.weatherview.core.extensions

import br.com.victorcs.weatherview.ROUND_ONE

fun Boolean?.orFalse() = this == true

val Double.kelvinToCelsius: String
    get() = String.format(ROUND_ONE, this - 273.15)
