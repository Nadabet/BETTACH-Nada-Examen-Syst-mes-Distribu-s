package ma.enset.conferenceservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI conferenceServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Conference Service API")
                        .description("API de gestion des Conférences et Reviews pour le système de gestion de conférences")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ENSET")
                                .email("contact@enset.ma"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
    
}
