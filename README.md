
<p align="center">
  <img src="https://www.postfinance.ch/content/dam/pfch/image/about/media/pf_logo_new.jpg" alt="PostFinance" width="250"/>
</p>

<h1 align="center">🚀 Event-Driven Outbox Pattern with Oracle, JPA Stream & Kafka</h1>

<p align="center">
  <b>Part of the PostFinance Dev-Days 2025 Keynote</b><br/>
  by <b>Daniel Vöckler</b> — Enterprise technologist committed to building resilient platforms and high-performing teams.
Turning complex systems into elegant, scalable solutions.
</p>

---


A Spring Boot 3.5 / Java 21 reference implementation of an event-driven Outbox pattern using
Oracle Database, JPA Stream, KafkaTemplate, and @RetryableTopic.

💡 This project shows how to emit database events in near real time and process them safely and efficiently with Spring for Kafka 3.x.

---

## 🧩 Overview

Problem:
Traditional batch integrations (scheduler chains, JDBC polling) are slow and brittle.
We needed to publish database changes to Kafka in real time — without two-phase commits.

Solution:
We apply the Outbox Pattern:

- Business transaction plus Outbox entry in the same database transaction.
- JPA Stream continuously reads unprocessed rows (approximately 1000× faster than JDBC).
- Events are sent to Kafka every few hundred milliseconds via KafkaTemplate.
- Consumers use @RetryableTopic for automatic retries and Dead Letter Topic handling.

---

## ⚙️ Architecture

The architecture follows an event-driven design:

- An Oracle OLTP database writes business data and emits an Outbox event in the same transaction.
- The OutboxPollService streams new entries via JPA Stream and publishes them to Kafka.
- Downstream microservices consume, enrich, and republish data using KafkaListener and @RetryableTopic.
- Each service is small, independent, and idempotent.

---

## 🧱 Outbox Pattern Highlights

- Atomicity: Business change and event creation happen in the same database transaction.
- Performance: JPA Stream provides non-blocking cursor access – up to 1000× faster than traditional Paging fetch.
- Efficiency: Uses Java 21 Virtual Threads for lightweight concurrent streaming.
- Resilience: No two-phase commit needed; replay and recovery are straightforward.
- Observability: Integrated with Micrometer and Prometheus for metrics and tracing.

---

## 🧠 Event Processing with Spring Boot 3.5 and Kafka

- Built with Spring Boot 3.5 and Spring for Kafka 3.x.
- Each microservice consumes events via KafkaListener.
- The @RetryableTopic annotation handles transient failures automatically with exponential backoff.
- Failed messages are routed to dedicated Dead Letter Topics for inspection and replay.
- Observability and metrics are exposed via Micrometer.

---

## 🧾 JSON Subtype (Content Type, Versioning, and Schema)

Purpose:
To make payloads self-describing and version-safe across services and environments, we standardize the JSON versioning.

Conventions:
- Versioning: increase the vN component when introducing breaking changes to the event contract.
- Schema Registry: register a JSON Schema for each media type and version; use backward compatibility by default.
- Subject naming: use topic-value or record name strategies consistently so all services resolve the same schema.

Benefits:
- Self-describing events with explicit version information.
- Safer evolution of event contracts over time.
- Easier debugging and interoperability with REST tools and schema registries.

---

## 📊 Performance

- Outbox read: via JPA Stream — approximately 1000× faster than Paging.
- Service latency: under 3 ms on average per microservice.
- End‑to‑end latency: approximately 1 second from database commit to customer view.
- Throughput: approximately 2.5 million events per hour (about 694 events per second).
- Incidents since go-live: 0.

---

## 🤝 Contributions

Pull requests and issues are welcome.
If you extend or tune the Outbox pattern, please open an issue to share your findings.
