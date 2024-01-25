package br.com.steffanmartins.alexajfinancasskill

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler

class AlexaHandler : SpeechletRequestStreamHandler(AlexaController(), setOf("APPLICATION_ID"))
