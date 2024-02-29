package com.defers.springtelemetry.app.config;

import com.defers.springtelemetry.domain.user.port.in.UserUseCase;
import com.defers.springtelemetry.domain.user.port.out.QueueMessageSender;
import com.defers.springtelemetry.domain.user.port.out.TopicMessageSender;
import com.defers.springtelemetry.domain.user.port.out.UserRepository;
import com.defers.springtelemetry.domain.user.usecase.UserService;
import com.defers.springtelemetry.infra.rest.UserRestApi;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ConfigurationPropertiesScan(value = "com.defers.springtelemetry")
public class AppConfig {

    @Bean
    public UserUseCase userUseCase(
            UserRepository userRepository,
            QueueMessageSender queueMessageSender,
            TopicMessageSender topicMessageSender) {
        return new UserService(userRepository, queueMessageSender, topicMessageSender);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    //    @Bean
    //    public OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
    //        return OtlpHttpSpanExporter.builder().setEndpoint(url).build();
    //    }

    @Bean
    public WebClient webClient(@Value("${app.remote-api.url}") String url, WebClient.Builder builder) {
        return builder.baseUrl(url).build();
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
    public UserRestApi userRestApi(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(
                        WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(UserRestApi.class);
    }

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
