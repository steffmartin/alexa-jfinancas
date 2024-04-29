package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class PrevisaoVencidaOuNaoBaixadaIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "PrevisaoVencidaOuNaoBaixada"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val previsao = intentRequest!!.intent.slots["previsao"]?.value

        input!!.responseBuilder
            .withSpeech("Você quer saber se tem $previsao vencida ou que esqueceu de baixar. Ainda não consigo olhar para seu histórico.")
            .withShouldEndSession(false)
            .build()
    }

}
