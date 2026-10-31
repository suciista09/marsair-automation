package com.marsair.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads settings from config.properties. Any value can be overridden
 * from the command line, e.g. -Dbase.url=https://... -Dheadless=true
 */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load config.properties", e);
        }
    }

    private Config() {
    }

    public static String get(String key) {
        return System.getProperty(key, PROPS.getProperty(key));
    }

    public static String baseUrl() {
        return get("base.url");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int timeoutSeconds() {
        return Integer.parseInt(get("timeout.seconds"));
    }
}
