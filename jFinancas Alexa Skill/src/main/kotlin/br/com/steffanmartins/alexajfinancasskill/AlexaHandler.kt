package br.com.steffanmartins.alexajfinancasskill

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler

//TODO colocar app id
class AlexaHandler : SpeechletRequestStreamHandler(AlexaController(), setOf("amzn1.ask.skill.987654321"))
