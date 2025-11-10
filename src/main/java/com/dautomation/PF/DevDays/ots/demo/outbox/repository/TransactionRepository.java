package com.dautomation.PF.DevDays.ots.demo.outbox.repository;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;
import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;
import static org.hibernate.jpa.HibernateHints.HINT_READ_ONLY;

import com.dautomation.PF.DevDays.ots.demo.outbox.models.Transaction;
import jakarta.persistence.QueryHint;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.Repository;

public interface TransactionRepository extends Repository<Transaction, Long> {

  @QueryHints(value = {
      @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE),
      @QueryHint(name = HINT_CACHEABLE, value = "false"),
      @QueryHint(name = HINT_READ_ONLY, value = "true")
  })
  @Query(value = "select * from OTS.DemoTable", nativeQuery = true)
  Stream<Transaction> getAll();
}
