package com.android.server.timezonedetector;

public interface CallerIdentityInjector {
    public static final Real REAL = new Real();

    public final class Real implements CallerIdentityInjector {}
}
