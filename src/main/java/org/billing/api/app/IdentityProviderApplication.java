package org.billing.api.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(
        exclude =
                {
                        MongoAutoConfiguration.class,
                        MongoDataAutoConfiguration.class
                }
)
@ComponentScan(value = {
        "oauth.security.config",
        "oauth.security.custom.config",
        "org.billing.api.service",
        "org.billing.api.controller",
        "org.billing.api.repository",
        "org.billing.api.datasource.config.util",
        "org.billing.api.datasource.config",
        "org.billing.api.utility",
        "oauth.security.utility",
})
// Enable Resource Server to validate tokens and get session details via token
@EnableResourceServer
// Enable Eureka Client
@EnableEurekaClient
public class IdentityProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityProviderApplication.class, args);
    }

}
