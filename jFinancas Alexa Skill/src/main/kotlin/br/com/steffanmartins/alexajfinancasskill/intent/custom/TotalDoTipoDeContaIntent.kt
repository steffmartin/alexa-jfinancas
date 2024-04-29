package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class TotalDoTipoDeContaIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "TotalDoTipoDeConta"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val tipo = intentRequest!!.intent.slots["tipo"]?.value

        input!!.responseBuilder
            .withSpeech("Você quer saber o total da categoria $tipo. Ainda não consigo te responder essa.")
            .withShouldEndSession(false)
            .build()
    }

}
