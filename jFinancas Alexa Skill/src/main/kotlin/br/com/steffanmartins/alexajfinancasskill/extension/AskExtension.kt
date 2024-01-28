package br.com.steffanmartins.alexajfinancasskill.extension

import com.amazon.ask.model.IntentRequest

fun IntentRequest?.nameEquals(intentName: String) = this?.intent?.name.equals(intentName)

fun String.ssmlExcited(intensity: String = "medium"): String =
    "<amazon:emotion name='excited' intensity='$intensity'>$this</amazon:emotion>"