package ami.todolist_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Terapkan aturan CORS ini hanya untuk endpoint di bawah /api/
                .allowedOrigins("*") // Izinkan request dari origin manapun. Untuk produksi, ini harus diganti dengan domain frontend yang spesifik.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Izinkan metode HTTP yang umum digunakan.
                .allowedHeaders("*") // Izinkan semua header dalam request.
                .allowCredentials(false); // Set ke false jika tidak menggunakan cookies/authentication berbasis session.
    }
}