package br.com.steffanmartins.alexajfinancasskill.exception

import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.HandlerInput

class GenericExceptionHandler : ExceptionHandler {
    override fun canHandle(input: HandlerInput?, e: Throwable?) = true

    override fun handle(input: HandlerInput?, e: Throwable?) = run {
        val speechText = "Dei uma bugada, tente novamente."
        input!!.responseBuilder
            .withSpeech(speechText)
            .build()
    }
}