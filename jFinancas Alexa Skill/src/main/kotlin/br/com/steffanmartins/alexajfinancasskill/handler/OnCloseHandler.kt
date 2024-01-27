package br.com.steffanmartins.alexajfinancasskill.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.SessionEndedRequestHandler
import com.amazon.ask.model.SessionEndedRequest

class OnCloseHandler : SessionEndedRequestHandler {

    override fun canHandle(input: HandlerInput?, sessionEndedRequest: SessionEndedRequest?) = true

    override fun handle(input: HandlerInput?, sessionEndedRequest: SessionEndedRequest?) = run {
        // any cleanup logic goes here
        input!!.responseBuilder.build()
    }

}