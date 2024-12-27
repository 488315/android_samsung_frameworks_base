package com.samsung.android.sume.core.functional;

@FunctionalInterface
public interface ExceptionHandler {
    boolean accept(Exception exc);
}
