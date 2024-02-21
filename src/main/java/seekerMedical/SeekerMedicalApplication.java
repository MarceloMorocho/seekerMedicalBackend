package seekerMedical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SeekerMedicalApplication {
	public static void main(String[] args) {
		SpringApplication.run(SeekerMedicalApplication.class, args);
	}
	@EnableWebMvc
	public class WebConfig implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:8100") // Reemplaza con la URL de tu aplicación Ionic
					.allowedMethods("GET", "POST");
		}
	}
}