resource "aws_iam_user" "jfin_sync_user" {
  name = "jFinancasAlexaSync"
}

resource "aws_iam_policy" "jfin_sync_policy" {
  name        = "policy_jfin_sync"
  description = "Permissões para que o jFinanças Alexa Sync consiga realizar a sincronização de dados"

  policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "dynamodb:BatchWriteItem",
        "dynamodb:Query"
      ],
      "Resource": [
        "${aws_dynamodb_table.alexa_jfinancas_saldo.arn}",
        "${aws_dynamodb_table.alexa_jfinancas_previsoes.arn}"
      ]
    }
  ]
}
EOF
}

resource "aws_iam_user_policy_attachment" "jfin_sync_attach_policy" {
  user       = aws_iam_user.jfin_sync_user.name
  policy_arn = aws_iam_policy.jfin_sync_policy.arn
}

resource "aws_iam_access_key" "jfin_sync_access_key" {
  user = aws_iam_user.jfin_sync_user.name
}