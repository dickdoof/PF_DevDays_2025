package com.dautomation.PF.DevDays.ots.demo.outbox.models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionV1 extends Transaction {

  private Long category;

  public TransactionV1(String transactionId, BigDecimal amount, String currency,
      String accountId, String description) {
    super(transactionId, amount, currency, accountId, description);
  }
}
