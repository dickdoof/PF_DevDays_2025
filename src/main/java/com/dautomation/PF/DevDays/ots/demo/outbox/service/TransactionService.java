package com.dautomation.PF.DevDays.ots.demo.outbox.service;

import com.dautomation.PF.DevDays.ots.demo.outbox.models.Transaction;
import com.dautomation.PF.DevDays.ots.demo.outbox.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final EntityManager entityManager;

  @Transactional(readOnly = true)
  public void processBooks() {
    Stream<Transaction> transactionStream =
        transactionRepository.getAll();

    transactionStream.forEach(transaction -> {
      log.info("add handling here");
      //TODO some processing
      entityManager.detach(transaction);
    });
  }
}
