#O valor será gravado no terraform.tfstate
output "jfin_sync_access_key_id" {
  value     = aws_iam_access_key.jfin_sync_access_key.id
  sensitive = true
}

#O valor será gravado no terraform.tfstate
output "jfin_sync_secret_access_key" {
  value     = aws_iam_access_key.jfin_sync_access_key.secret
  sensitive = true
}