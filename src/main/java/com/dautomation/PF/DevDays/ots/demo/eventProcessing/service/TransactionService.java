package com.dautomation.PF.DevDays.ots.demo.eventProcessing.service;

import com.dautomation.PF.DevDays.ots.demo.outbox.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionService {

  private final KafkaTemplate<String, Transaction> kafka;

  @RetryableTopic(
      // careful Spring Boot Retry Topic are {topic}-DELAY and DLT-Topic is {topic}-DLT
      // within PF use DltAndRetryNameProvider to provide the correct names
      attempts = "3",
      dltStrategy = DltStrategy.FAIL_ON_ERROR,
      autoStartDltHandler = "false",
      autoCreateTopics = "false"
  )
  @KafkaListener(
      topics = "${kafka.topic.name:demo-topic}",
      groupId = "${kafka.topic.groupId:demo-group}",
      concurrency = "1"
  )
  public void handle(ConsumerRecord<String, Transaction> record) {
    Transaction transaction = record.value();

    //TODO add handling
  }
}
