package com.masprogtech.config;



import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API - Icesmart")
                        .version("v1")
                        .description("API para vendas de gelo, vai ser consumida por um app")
                        .termsOfService("https://about-mauro.netlify.app/")
                        .license(
                                new License()
                                        .name("GNU GENERAL PUBLIC LICENSE")
                                        .url("https://www.gnu.org/licenses/gpl-3.0.pt-br.html")
                        )
                        .contact(new Contact().name("Mauro Manuel").url("https://www.linkedin.com/in/mauro-manuel-522947b2/"))
                );
    }
}
