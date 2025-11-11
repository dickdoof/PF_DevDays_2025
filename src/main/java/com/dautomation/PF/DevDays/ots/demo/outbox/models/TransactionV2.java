package com.dautomation.PF.DevDays.ots.demo.outbox.models;

import java.time.Instant;
import java.util.List;
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
public class TransactionV2 extends TransactionV1 {

  private List<String> hashTags; // new field in v2
  private Instant createdAt; // another new field
}