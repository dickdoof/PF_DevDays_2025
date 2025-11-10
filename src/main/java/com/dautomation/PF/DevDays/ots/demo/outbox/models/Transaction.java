package com.dautomation.PF.DevDays.ots.demo.outbox.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "version"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TransactionV1.class, name = "v1"),
    @JsonSubTypes.Type(value = TransactionV2.class, name = "v2")
})
public class Transaction {

  private String transactionId;

  private BigDecimal amount;

  private String currency;

  private String accountId;

  private String description;
}
