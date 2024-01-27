package br.com.steffanmartins.alexajfinancasskill.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler
import com.amazon.ask.model.LaunchRequest

class OnLaunchHandler : LaunchRequestHandler {
    override fun canHandle(input: HandlerInput?, lauchRequest: LaunchRequest?) = true

    override fun handle(input: HandlerInput?, lauchRequest: LaunchRequest?) = run {
        val speechText = "Ok, o que você gostaria de saber?"
        input!!.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("Minhas Finanças", speechText)
            .withReprompt(speechText)
            .build()
    }

}