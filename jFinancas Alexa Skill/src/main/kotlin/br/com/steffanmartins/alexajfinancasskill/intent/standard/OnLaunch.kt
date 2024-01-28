package br.com.steffanmartins.alexajfinancasskill.intent.standard

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler
import com.amazon.ask.model.LaunchRequest

class OnLaunch : LaunchRequestHandler {
    override fun canHandle(input: HandlerInput?, lauchRequest: LaunchRequest?) = true

    override fun handle(input: HandlerInput?, lauchRequest: LaunchRequest?) = input!!.responseBuilder
        .withSpeech("Ok, o que você gostaria de saber?")
        .withShouldEndSession(false)
        .build()

}