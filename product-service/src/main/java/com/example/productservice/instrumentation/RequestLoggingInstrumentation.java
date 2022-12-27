package com.example.productservice.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public class RequestLoggingInstrumentation extends SimpleInstrumentation {
    private final Clock clock = Clock.systemDefaultZone();

    private static String CORRELATION_ID = "correlation_id";

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        Instant startTime = Instant.now(clock);

        log.info("Query: {} with variables: {}", parameters.getQuery(), parameters.getVariables());

        return SimpleInstrumentationContext.whenCompleted((executionResult, throwable) -> {
            Duration duration = Duration.between(startTime, Instant.now(clock));
            if (throwable == null) {
                log.info("Completed successfully in: {}", duration);
            } else {
                log.warn("Failed in: {}", duration, throwable);
            }
        });
    }
}
