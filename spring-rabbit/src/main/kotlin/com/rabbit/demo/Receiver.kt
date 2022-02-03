package com.rabbit.demo

import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver {
    fun receiveMessage(message: String) {
        println("Received Message: $message")
    }
}
