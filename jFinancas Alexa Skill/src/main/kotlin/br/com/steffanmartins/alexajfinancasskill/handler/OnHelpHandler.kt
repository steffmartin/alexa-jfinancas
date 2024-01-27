package br.com.steffanmartins.alexajfinancasskill.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class OnHelpHandler : IntentRequestHandler {
    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest!!.intent.name.equals("AMAZON.HelpIntent")

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val speechText =
            "Você pode me perguntar qual é o saldo de uma conta, ou então, se tem alguma conta para pagar hoje."
        input!!.responseBuilder
            .withSpeech(speechText)
            .withSimpleCard("Dicas", speechText)
            .withReprompt(speechText)
            .build()
    }

}