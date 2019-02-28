package pl.training.bank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.training.bank.common.mapper.Mapper;
import pl.training.bank.common.mapper.ModelMapperAdapter;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public Mapper mapper() {
        return new ModelMapperAdapter();
    }

}
