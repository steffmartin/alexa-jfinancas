package br.com.steffanmartins.alexajfinancasskill.intent.exception

import com.amazon.ask.dispatcher.exception.ExceptionHandler
import com.amazon.ask.dispatcher.request.handler.HandlerInput

class ErrorIntent : ExceptionHandler {
    override fun canHandle(input: HandlerInput?, e: Throwable?) = true

    override fun handle(input: HandlerInput?, e: Throwable?) = input!!.responseBuilder
        .withSpeech("Houve um erro, tente novamente.")
        .build()
}