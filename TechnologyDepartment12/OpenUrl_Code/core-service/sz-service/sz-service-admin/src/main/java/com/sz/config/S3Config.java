package com.sz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * 亚马逊对象存储
 */
@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        String accessKey = "REDACTED";
        String secretKey = "REDACTED";
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);

        String region = "ap-northeast-1";
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }
}
