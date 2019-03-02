package com.hsyou.wagupost;

import com.hsyou.wagupost.event.PostEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PostEventListener {

    @Autowired
    private KafkaProvider kafkaProvider;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, classes = PostEvent.class)
    public void handle(PostEvent event) {

        kafkaProvider.sendPostMessage(event.getPostDTO());
    }
}
