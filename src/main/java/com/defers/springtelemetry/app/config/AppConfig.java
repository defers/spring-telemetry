package com.defers.springtelemetry.app.config;

import com.defers.springtelemetry.domain.user.port.in.UserUseCase;
import com.defers.springtelemetry.domain.user.port.out.UserRepository;
import com.defers.springtelemetry.domain.user.usecase.UserService;
import com.defers.springtelemetry.infra.rest.UserRestApi;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AppConfig {

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
        return OtlpHttpSpanExporter.builder()
                .setEndpoint(url)
                .build();
    }

    @Bean
    WebClient webClient(@Value("${app.remote-api.url}") String url, WebClient.Builder builder) {
        return builder
                .baseUrl(url)
                .build();
    }

    // Если настроить таким образом webClient без существующего билдера
    // и использовать вместо javaagent spring micrometer для трейсинга opentelemetry,
    // то при отправке запроса в api сервиса в header не передается parent trace id - parenttrace.
    // При использовании javaagent такой проблемы нет.
//    @Bean
//    WebClient webClient(@Value("${app.remote-api.url}") String url) {
//        return WebClient.builder()
//                .baseUrl(url)
//                .build();
//    }

    @Bean
    UserRestApi userRestApi(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(UserRestApi.class);
    }

    @Bean
    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

}
