resource "aws_lambda_function" "alexa_jfinancas_skill" {
  function_name = "alexa_jfinancas_skill"
  role          = aws_iam_role.jfin_skill_role.arn
  filename      = "../jFinancas Alexa Skill/target/alexa-jfinancas-skill-1.0.0.jar"
  handler       = "br.com.steffanmartins.alexajfinancasskill.AlexaHandler"
  runtime       = "java21"
  memory_size   = 512
  timeout       = 30
  snap_start {
    apply_on = "PublishedVersions"
  }
}

resource "aws_lambda_permission" "alexa_jfinancas_skill_trigger" {
  statement_id  = "AllowExecutionFromAlexa"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.alexa_jfinancas_skill.function_name
  principal     = "alexa-appkit.amazon.com"
}