package by.rublevskaya.hotelapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hotelApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("property View API")
                        .description("API for hotel management")
                        .version("1.0.0"));
    }
}