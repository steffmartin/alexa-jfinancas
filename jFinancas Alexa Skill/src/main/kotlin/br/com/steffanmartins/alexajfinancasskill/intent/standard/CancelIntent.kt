package br.com.steffanmartins.alexajfinancasskill.intent.standard

import br.com.steffanmartins.alexajfinancasskill.extension.nameEquals
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class CancelIntent : IntentRequestHandler {
    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest.nameEquals("AMAZON.CancelIntent")

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = input!!.responseBuilder
        .withSpeech("Ok, cancelado.")
        .withShouldEndSession(false)
        .build()

}