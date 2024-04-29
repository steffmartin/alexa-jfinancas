package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class DataDaPrevisaoIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "DataDaPrevisao"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val categoria = intentRequest!!.intent.slots["categoria"]?.value

        input!!.responseBuilder
            .withSpeech("Você quer saber a data da $categoria. Em breve poderei lhe dizer.")
            .withShouldEndSession(false)
            .build()
    }

}
