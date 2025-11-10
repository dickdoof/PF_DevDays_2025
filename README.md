# 🚀 Event-Driven Outbox Pattern with Oracle, JPA Stream & Kafka

A **Spring Boot 3.5 / Java 21** reference implementation of an **event-driven Outbox pattern** using  
**Oracle Database + JPA Stream + KafkaTemplate + @RetryableTopic**.

> 💡 This repository demonstrates how to achieve *real-time* event emission from relational transactions  
> and process those events reliably with Spring for Kafka 3.x.

---

## 🧩 Overview

### Problem
Traditional batch-based integrations (e.g., scheduler chains, JDBC polling) are slow and brittle.  
We needed to publish database changes to Kafka **in real time** — without two-phase commits.

### Solution
We use the **Outbox pattern**:
- Business transaction + Outbox entry in the **same DB transaction**
- **JPA Stream** continuously reads unprocessed events (≈ 1000× faster than JDBC)
- Events are sent to **Kafka** every few hundred milliseconds
- Consumers handle enrichment and retries automatically with `@RetryableTopic`

---