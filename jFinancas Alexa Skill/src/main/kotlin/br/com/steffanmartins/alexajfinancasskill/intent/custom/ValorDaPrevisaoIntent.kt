package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class ValorDaPrevisaoIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "ValorDaPrevisao"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val categoria = intentRequest!!.intent.slots["categoria"]?.value

        input!!.responseBuilder
            .withSpeech("Você quer saber o valor da $categoria. Por enquanto ficarei te devendo esta informação.")
            .withShouldEndSession(false)
            .build()
    }

}
