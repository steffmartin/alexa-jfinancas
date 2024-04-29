package br.com.steffanmartins.alexajfinancasskill

import br.com.steffanmartins.alexajfinancasskill.intent.custom.*
import br.com.steffanmartins.alexajfinancasskill.intent.exception.ErrorIntent
import br.com.steffanmartins.alexajfinancasskill.intent.standard.CancelIntent
import br.com.steffanmartins.alexajfinancasskill.intent.standard.HelpIntent
import br.com.steffanmartins.alexajfinancasskill.intent.standard.OnLaunch
import br.com.steffanmartins.alexajfinancasskill.intent.standard.OnSessionEnded
import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.builder.SkillBuilder

fun main(args: Array<String>) {}

class JFinancasAlexaSkillApplication : SkillStreamHandler(skillConfig) {
    companion object {
        val skillConfig = SkillBuilder()
            .addRequestHandlers(
                OnLaunch(),
                SaldoDaContaIntent(),
                TotalDoTipoDeContaIntent(),
                PrevisaoNaDataIntent(),
                PrevisaoVencidaOuNaoBaixadaIntent(),
                ValorDaPrevisaoIntent(),
                DataDaPrevisaoIntent(),
                CancelIntent(),
                HelpIntent(),
                OnSessionEnded()
            )
            .addExceptionHandler(ErrorIntent())
            .build()
    }
}