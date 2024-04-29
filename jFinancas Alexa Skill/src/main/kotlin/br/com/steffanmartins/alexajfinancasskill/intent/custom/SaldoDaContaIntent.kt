package br.com.steffanmartins.alexajfinancasskill.intent.custom

import br.com.steffanmartins.alexajfinancasskill.extension.nameEqualsTo
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class SaldoDaContaIntent : IntentRequestHandler {

    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest nameEqualsTo "SaldoDaConta"

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val conta = intentRequest!!.intent.slots["conta"]?.value

        input!!.responseBuilder
            .withSpeech("Você quer saber o saldo da conta $conta. Ainda não tenho acesso a esta informação.")
            .withShouldEndSession(false)
            .build()
    }

}
