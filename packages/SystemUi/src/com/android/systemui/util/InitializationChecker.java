package com.android.systemui.util;

import android.app.ActivityThread;
import android.os.Process;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InitializationChecker {
    public final boolean instrumentationTest;

    public InitializationChecker(boolean z) {
        this.instrumentationTest = z;
    }

    public final boolean initializeComponents() {
        return !this.instrumentationTest && Process.myUserHandle().isSystem() && Intrinsics.areEqual(ActivityThread.currentProcessName(), ActivityThread.currentPackageName());
    }
}
