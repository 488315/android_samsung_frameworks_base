package com.android.systemui.util;

import android.app.ActivityThread;
import android.os.Process;
import kotlin.jvm.internal.Intrinsics;

public final class InitializationChecker {
    public static final int $stable = 0;
    private final boolean instrumentationTest;

    public InitializationChecker(boolean z) {
        this.instrumentationTest = z;
    }

    public final boolean initializeComponents() {
        return !this.instrumentationTest && Process.myUserHandle().isSystem() && Intrinsics.areEqual(ActivityThread.currentProcessName(), ActivityThread.currentPackageName());
    }
}
