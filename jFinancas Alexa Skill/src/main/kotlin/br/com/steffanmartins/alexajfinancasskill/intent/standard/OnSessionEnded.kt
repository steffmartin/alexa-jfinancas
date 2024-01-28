package br.com.steffanmartins.alexajfinancasskill.intent.standard

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler
import com.amazon.ask.model.SessionEndedRequest

class OnSessionEnded : SessionEndedRequestHandler {

    override fun canHandle(input: HandlerInput?, sessionEndedRequest: SessionEndedRequest?) = true

    override fun handle(input: HandlerInput?, sessionEndedRequest: SessionEndedRequest?) = run {
        input!!.responseBuilder.withShouldEndSession(true).build()
    }

}