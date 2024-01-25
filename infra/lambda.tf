locals {
  path_to_jar = "../jFinancas Alexa Skill/target/alexa-jfinancas-skill-1.0.0-jar-with-dependencies.jar"
}

resource "aws_lambda_function" "alexa_jfinancas_skill" {
  function_name    = "alexa_jfinancas_skill"
  role             = aws_iam_role.jfin_skill_role.arn
  filename         = local.path_to_jar
  source_code_hash = filebase64sha256(local.path_to_jar)
  handler          = "br.com.steffanmartins.alexajfinancasskill.AlexaHandler"
  runtime          = "java21"
  memory_size      = 512
  timeout          = 30
}

resource "aws_lambda_permission" "alexa_jfinancas_skill_trigger" {
  statement_id       = "AllowExecutionFromAlexa"
  action             = "lambda:InvokeFunction"
  function_name      = aws_lambda_function.alexa_jfinancas_skill.function_name
  principal          = "alexa-appkit.amazon.com"
  event_source_token = "amzn1.ask.skill.987654321" #TODO colocar application id
}