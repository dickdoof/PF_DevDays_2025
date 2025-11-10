package com.dautomation.PF.DevDays.ots.demo.outbox.models;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Transaction {

  private String transactionId;

  private BigDecimal amount;

  private String currency;

  private String accountId;

  private String description;
}
