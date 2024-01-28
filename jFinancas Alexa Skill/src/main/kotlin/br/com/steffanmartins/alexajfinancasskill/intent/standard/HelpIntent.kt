package br.com.steffanmartins.alexajfinancasskill.intent.standard

import br.com.steffanmartins.alexajfinancasskill.extension.ssmlExcited
import br.com.steffanmartins.alexajfinancasskill.extension.nameEquals
import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest

class HelpIntent : IntentRequestHandler {
    override fun canHandle(input: HandlerInput?, intentRequest: IntentRequest?) =
        intentRequest.nameEquals("AMAZON.HelpIntent")

    override fun handle(input: HandlerInput?, intentRequest: IntentRequest?) = run {
        val intro =
            "Eu posso consultar o saldo atual de suas contas e também consigo lhe falar sobre receitas e despesas a vencer"
        val exemploUm = "Qual é o saldo da minha poupança?"
        val exemploDois = "Tenho alguma conta vencendo hoje?"

        input!!.responseBuilder
            .withSpeech("$intro. Tente me perguntar: ${exemploUm.ssmlExcited()}, ou então: ${exemploDois.ssmlExcited()}.")
            .withShouldEndSession(false)
            .build()
    }

}
