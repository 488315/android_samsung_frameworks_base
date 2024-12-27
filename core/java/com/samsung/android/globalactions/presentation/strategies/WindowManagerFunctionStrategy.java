package com.samsung.android.globalactions.presentation.strategies;

public interface WindowManagerFunctionStrategy {
    public static final String REBOOT = "REBOOT";
    public static final String SHUTDOWN = "SHUTDOWN";

    void onReboot();

    void onShutdown();
}
