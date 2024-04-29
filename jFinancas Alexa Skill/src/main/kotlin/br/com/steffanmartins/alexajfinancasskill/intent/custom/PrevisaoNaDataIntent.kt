package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class PrevisaoNaDataIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "PrevisaoNaData"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val previsao = intentRequest!!.intent.slots["previsao"]?.value
        val data = intentRequest!!.intent.slots["data"]?.value
        //https://developer.amazon.com/en-US/docs/alexa/custom-skills/slot-type-reference.html#date

        input!!.responseBuilder
            .withSpeech("Você quer saber se tem $previsao em $data. Ainda não consigo consultar as previsões em uma data específica.")
            .withShouldEndSession(false)
            .build()
    }

}
