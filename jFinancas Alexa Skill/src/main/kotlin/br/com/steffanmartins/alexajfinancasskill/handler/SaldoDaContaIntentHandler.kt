package br.com.steffanmartins.alexajfinancasskill.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class SaldoDaContaIntentHandler : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) = intentRequest!!.intent.name.equals("SaldoDaConta")

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?)= run {
        val conta = intentRequest!!.intent.slots["conta"]?.value
        val speechText = "Você quer saber o saldo da conta $conta. Bom, eu ainda não sei."
        input!!.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("Conta $conta", speechText)
            .withShouldEndSession(false)
            .build()
    }

}
