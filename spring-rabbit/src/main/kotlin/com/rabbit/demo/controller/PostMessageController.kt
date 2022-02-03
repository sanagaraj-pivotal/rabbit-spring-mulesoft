package com.rabbit.demo.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostMessageController (
    val rabbitTemplate: RabbitTemplate
) {

    @PostMapping("/messages")
    fun postMessage(@RequestBody body:String): String {

        rabbitTemplate.convertAndSend(
            "spring-boot-exchange",
            "foo.bar.baz",
            body
        )

        return "Success ** $body"
    }
}
