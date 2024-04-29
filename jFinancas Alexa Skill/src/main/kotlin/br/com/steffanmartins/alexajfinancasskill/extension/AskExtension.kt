package br.com.steffanmartins.alexajfinancasskill.extension

import com.amazon.ask.model.IntentRequest

infix fun IntentRequest?.nameEqualsTo(intentName: String) = this?.intent?.name.equals(intentName)

fun String.ssmlExcited(intensity: String = "medium"): String =
    "<amazon:emotion name='excited' intensity='$intensity'>$this</amazon:emotion>"