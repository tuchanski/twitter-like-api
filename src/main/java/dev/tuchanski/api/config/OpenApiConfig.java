package dev.tuchanski.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Twitter-like API",
                version = "v1.0.0",
                description = "A learning-oriented REST API inspired by Twitter/X, providing features such as user management, tweets, likes, and follow system.",
                contact = @Contact(
                        name = "Guilherme Tuchanski",
                        email = "guilherme.tuchanski@gmail.com",
                        url = "https://tuchanski.dev"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Environment")
        }
)
public class OpenApiConfig {
}
