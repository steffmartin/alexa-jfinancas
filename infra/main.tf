terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.32.0"
    }
  }
}

provider "aws" {
  default_tags {
    tags = {
      #      "awsApplication" = ""
      "Project" = "Alexa jFinanças"
    }
  }
}