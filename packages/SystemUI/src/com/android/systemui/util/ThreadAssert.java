package com.android.systemui.util;

public final class ThreadAssert {
    public final void isMainThread() {
        Assert.isMainThread();
    }

    public final void isNotMainThread() {
        Assert.isNotMainThread();
    }
}
