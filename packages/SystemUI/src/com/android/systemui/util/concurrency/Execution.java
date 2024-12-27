package com.android.systemui.util.concurrency;

public interface Execution {
    void assertIsMainThread();

    boolean isMainThread();
}
