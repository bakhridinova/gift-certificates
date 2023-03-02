package com.epam.esm.repository.impl;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class AbstractIntegrationTest {
    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");

    static {
        container.withInitScript("data.sql");
        container.start();
        System.setProperty("db.url", container.getJdbcUrl());
        System.setProperty("db.username", container.getUsername());
        System.setProperty("db.password", container.getPassword());
    }
}
