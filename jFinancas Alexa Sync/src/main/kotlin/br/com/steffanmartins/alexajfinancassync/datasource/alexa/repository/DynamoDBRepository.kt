package br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import org.springframework.stereotype.Repository

@Repository
class DynamoDBRepository(
    private val dynamoDbClient: DynamoDbClient
)