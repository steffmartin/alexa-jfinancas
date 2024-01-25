package br.com.steffanmartins.alexajfinancasskill

import com.amazon.speech.json.SpeechletRequestEnvelope
import com.amazon.speech.speechlet.*
import com.amazon.speech.ui.PlainTextOutputSpeech

class AlexaController : SpeechletV2 {
    override fun onSessionStarted(requestEnvelope: SpeechletRequestEnvelope<SessionStartedRequest>?) {
        println("foi chamado onSessionStarted")
    }

    override fun onLaunch(requestEnvelope: SpeechletRequestEnvelope<LaunchRequest>?): SpeechletResponse {
        println("foi chamado onLaunch com o payload: $requestEnvelope")
        return SpeechletResponse.newTellResponse(PlainTextOutputSpeech().apply { text = "on launch." })
    }

    override fun onIntent(requestEnvelope: SpeechletRequestEnvelope<IntentRequest>?): SpeechletResponse {
        println("foi chamado onIntent com o payload: $requestEnvelope")
        return SpeechletResponse.newTellResponse(PlainTextOutputSpeech().apply { text = "on intent." })
    }

    override fun onSessionEnded(requestEnvelope: SpeechletRequestEnvelope<SessionEndedRequest>?) {
        println("foi chamado onSessionEnded com o payload: $requestEnvelope")
    }
}