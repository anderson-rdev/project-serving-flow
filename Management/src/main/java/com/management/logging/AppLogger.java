package com.management.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private static AppLogger instance;

    private AppLogger() {
    }

    public static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    public Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    // Metodo para padronizar os logs
    public void info(Class<?> clazz, String message) {
        getLogger(clazz).info("[APP] {}", message);
    }

    public void error(Class<?> clazz, String message, Throwable t) {
        getLogger(clazz).info("[ERROR] {}", message, t);
    }

    public void warn(Class<?> clazz, String message) {
        getLogger(clazz).warn("[WARN] {}", message);
    }

    public void debug(Class<?> clazz, String message) {
        getLogger(clazz).debug("[DEBUG] {}", message);
    }
}
