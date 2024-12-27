package com.android.systemui.util;

import android.app.ActivityThread;
import android.os.Process;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
