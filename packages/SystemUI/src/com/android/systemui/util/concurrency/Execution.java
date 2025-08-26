package com.android.systemui.util.concurrency;

/* loaded from: classes3.dex */
public interface Execution {
    void assertIsMainThread();

    boolean isMainThread();
}
