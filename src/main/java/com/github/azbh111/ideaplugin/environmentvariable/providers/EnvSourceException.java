package com.github.azbh111.ideaplugin.environmentvariable.providers;

public class EnvSourceException extends RuntimeException {
    public EnvSourceException(String message) {
        super(message);
    }

    public EnvSourceException(Throwable cause) {
        super(cause);
    }
}