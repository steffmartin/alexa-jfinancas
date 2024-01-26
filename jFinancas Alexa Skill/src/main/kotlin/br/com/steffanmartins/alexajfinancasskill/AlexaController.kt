package br.com.steffanmartins.alexajfinancasskill

import com.amazon.speech.json.SpeechletRequestEnvelope
import com.amazon.speech.speechlet.*
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.Reprompt

class AlexaController : SpeechletV2 {
    override fun onSessionStarted(requestEnvelope: SpeechletRequestEnvelope<SessionStartedRequest>?) {
        println("foi chamado onSessionStarted")
    }

    override fun onLaunch(requestEnvelope: SpeechletRequestEnvelope<LaunchRequest>?): SpeechletResponse {
        println("foi chamado onLaunch com o payload: $requestEnvelope")
        return SpeechletResponse.newAskResponse(
            PlainTextOutputSpeech().apply { text = "Ok, o que você gostaria de saber?" },
            Reprompt().apply { outputSpeech = PlainTextOutputSpeech().apply { text = "Tente me perguntar o saldo de uma conta." } }
        )
    }

    override fun onIntent(requestEnvelope: SpeechletRequestEnvelope<IntentRequest>?): SpeechletResponse {
        println("foi chamado onIntent com o payload: $requestEnvelope")
        val conta = requestEnvelope!!.request.intent.slots["conta"]?.value

        return SpeechletResponse.newAskResponse(
            PlainTextOutputSpeech().apply { text = "Você quer saber o saldo da conta $conta. Bom, eu ainda não sei. Posso ajudar em algo mais?" },
            Reprompt().apply { outputSpeech = PlainTextOutputSpeech().apply { text = "Tente me perguntar o saldo de outra conta." } }
        )
    }

    override fun onSessionEnded(requestEnvelope: SpeechletRequestEnvelope<SessionEndedRequest>?) {
        println("foi chamado onSessionEnded com o payload: $requestEnvelope")
    }
}