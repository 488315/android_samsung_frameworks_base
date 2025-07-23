package com.android.systemui.util;

import android.app.ActivityThread;
import android.os.Process;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
