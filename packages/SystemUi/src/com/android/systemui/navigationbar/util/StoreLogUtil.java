package com.android.systemui.navigationbar.util;

import com.android.systemui.basic.util.LogWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StoreLogUtil {
    public boolean allowLogging;
    public int lastDepth;
    public final LogWrapper logWrapper;
    public final boolean loggingStarted = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public StoreLogUtil(LogWrapper logWrapper) {
        this.logWrapper = logWrapper;
    }

    public final void printLog(int i, String str) {
        this.lastDepth = i;
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("--");
        }
        sb.append(str);
        this.logWrapper.m98d("Store", sb.toString());
    }
}
