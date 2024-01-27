package br.com.steffanmartins.alexajfinancasskill

import br.com.steffanmartins.alexajfinancasskill.exception.GenericExceptionHandler
import br.com.steffanmartins.alexajfinancasskill.handler.OnCloseHandler
import br.com.steffanmartins.alexajfinancasskill.handler.OnHelpHandler
import br.com.steffanmartins.alexajfinancasskill.handler.OnLaunchHandler
import br.com.steffanmartins.alexajfinancasskill.handler.SaldoDaContaIntentHandler
import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.builder.SkillBuilder

class AlexaHandler : SkillStreamHandler(skillConfig) {
    companion object {
        val skillConfig = SkillBuilder()
            .addRequestHandlers(
                OnLaunchHandler(),
                OnHelpHandler(),
                SaldoDaContaIntentHandler(),
                OnCloseHandler()
            )
            .addExceptionHandler(GenericExceptionHandler())
            .build()
    }
}
