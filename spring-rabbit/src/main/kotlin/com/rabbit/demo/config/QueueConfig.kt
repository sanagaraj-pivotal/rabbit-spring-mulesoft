package com.rabbit.demo.config

import com.rabbit.demo.Receiver
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class QueueConfig {
    val topicExchangeName = "spring-boot-exchange"
    val queueName = "spring-boot"

    @Bean
    fun queue(): Queue? {
        return Queue(queueName, false)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    fun binding(queue: Queue, topicExchange: TopicExchange) : Binding {
        return BindingBuilder.bind(queue).to(topicExchange).with("foo.bar.#")
    }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }

    @Bean
    fun container(
        connectionFactory: ConnectionFactory,
        listenerAdapter: MessageListenerAdapter,
        messageListenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer {

        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)

        return container
    }
}
