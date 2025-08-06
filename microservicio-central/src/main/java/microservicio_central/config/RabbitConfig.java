package microservicio_central.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String COSECHAS_EXCHANGE = "cosechas";
    public static final String COLA_INVENTARIO = "cola_inventario";
    public static final String COLA_FACTURACION = "cola_facturacion";

    @Bean
    public TopicExchange cosechasExchange() {
        return new TopicExchange(COSECHAS_EXCHANGE, true, false);
    }

    @Bean
    public Queue colaInventario() {
        return QueueBuilder.durable(COLA_INVENTARIO).build();
    }

    @Bean
    public Queue colaFacturacion() {
        return QueueBuilder.durable(COLA_FACTURACION).build();
    }

    @Bean
    public Binding bindingInventario() {
        return BindingBuilder
                .bind(colaInventario())
                .to(cosechasExchange())
                .with("nueva");
    }

    @Bean
    public Binding bindingFacturacion() {
        return BindingBuilder
                .bind(colaFacturacion())
                .to(cosechasExchange())
                .with("nueva");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
