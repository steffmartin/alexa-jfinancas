resource "aws_dynamodb_table" "alexa_jfinancas_saldo" {
  name = "AlexaJFinancasSaldo"

  attribute {
    name = "Usuario"
    type = "S"
  }

  attribute {
    name = "TipoEConta"
    type = "S"
  }

  hash_key  = "Usuario"
  range_key = "TipoEConta" # Ex: Investimentos#Poupança

  billing_mode = "PAY_PER_REQUEST"
  table_class  = "STANDARD_INFREQUENT_ACCESS"
}

resource "aws_dynamodb_table" "alexa_jfinancas_previsoes" {
  name = "AlexaJFinancasPrevisoes"

  attribute {
    name = "Usuario"
    type = "S"
  }

  attribute {
    name = "VencimentoETipo"
    type = "S"
  }

  hash_key  = "Usuario"
  range_key = "VencimentoETipo" # Ex: 2024-12-31#PAGAR#hashcode

  billing_mode = "PAY_PER_REQUEST"
  table_class  = "STANDARD_INFREQUENT_ACCESS"
}