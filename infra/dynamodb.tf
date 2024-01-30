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
  range_key = "TipoEConta"

  billing_mode   = "PROVISIONED"
  write_capacity = 2
  read_capacity  = 2
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
  range_key = "VencimentoETipo"

  billing_mode   = "PROVISIONED"
  write_capacity = 2
  read_capacity  = 2
}