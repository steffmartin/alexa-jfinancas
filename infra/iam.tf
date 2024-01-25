# jFinancas Alexa Sync:
resource "aws_iam_user" "jfin_sync_user" {
  name = "jFinancasAlexaSync"
}

resource "aws_iam_policy" "jfin_sync_policy" {
  name        = "policy_jfin_sync"
  description = "Permissões para que o jFinanças Alexa Sync consiga realizar a sincronização de dados"

  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "dynamodb:BatchWriteItem",
          "dynamodb:Query"
        ],
        "Resource" : [
          aws_dynamodb_table.alexa_jfinancas_saldo.arn,
          aws_dynamodb_table.alexa_jfinancas_previsoes.arn
        ]
      }
    ]
  })
}

resource "aws_iam_user_policy_attachment" "jfin_sync_attach_policy" {
  user       = aws_iam_user.jfin_sync_user.name
  policy_arn = aws_iam_policy.jfin_sync_policy.arn
}

resource "aws_iam_access_key" "jfin_sync_access_key" {
  user = aws_iam_user.jfin_sync_user.name
}

# jFinancas Alexa Skill:
resource "aws_iam_role" "jfin_skill_role" {
  name               = "jFinancasAlexaSkill"
  assume_role_policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Action" : "sts:AssumeRole",
        "Principal" : {
          "Service" : "lambda.amazonaws.com"
        },
        "Effect" : "Allow",
        "Sid" : ""
      }
    ]
  })
}

resource "aws_iam_policy" "jfin_skill_policy" {
  name        = "policy_jfin_skill"
  description = "Permissões para que o jFinanças Alexa Skill  consiga realizar a leitura de dados"

  policy = jsonencode({
    "Version" : "2012-10-17",
    "Statement" : [
      {
        "Effect" : "Allow",
        "Action" : [
          "dynamodb:BatchGetItem",
          "dynamodb:ConditionCheckItem",
          "dynamodb:GetItem",
          "dynamodb:PartiQLSelect",
          "dynamodb:Query",
          "dynamodb:Scan",
        ],
        "Resource" : [
          aws_dynamodb_table.alexa_jfinancas_saldo.arn,
          aws_dynamodb_table.alexa_jfinancas_previsoes.arn
        ]
      }
    ]
  })
}

resource "aws_iam_policy_attachment" "jfin_skill_attach_policy" {
  name       = "policy_attachment_jfin_skill"
  roles      = [aws_iam_role.jfin_skill_role.name]
  policy_arn = aws_iam_policy.jfin_skill_policy.arn
}