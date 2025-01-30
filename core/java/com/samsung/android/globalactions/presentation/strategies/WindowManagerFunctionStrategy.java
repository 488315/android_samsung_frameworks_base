package com.samsung.android.globalactions.presentation.strategies;

/* loaded from: classes5.dex */
public interface WindowManagerFunctionStrategy {
    public static final String REBOOT = "REBOOT";
    public static final String SHUTDOWN = "SHUTDOWN";

    void onReboot();

    void onShutdown();
}
