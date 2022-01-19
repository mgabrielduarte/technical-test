package com.backendtest.inditex.zararetail.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.url("http://localhost:5000");

        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("SimilarProducts").description(
                        "This API returns a list of similar products, according to a productId")
                        .version("1.0"))
                .servers(Arrays.asList(server));
    }
}
