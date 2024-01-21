package br.com.steffanmartins.alexajfinancassync.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("custom-config.aws")
data class AwsProperties(
    val region: String,
    val accessKeyId: String,
    val secretAccessKey: String
)

@Configuration
class AwsBeans {

    @Bean
    fun dynamoDbClient(props: AwsProperties): DynamoDbClient = DynamoDbClient {
        region = props.region
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = props.accessKeyId
            secretAccessKey = props.secretAccessKey
        }
    }
}

