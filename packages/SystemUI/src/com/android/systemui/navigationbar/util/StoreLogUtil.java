package com.android.systemui.navigationbar.util;

import com.android.systemui.basic.util.LogWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class StoreLogUtil {
    public boolean allowLogging;
    public int lastDepth;
    public final LogWrapper logWrapper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        this.logWrapper.d("Store", sb.toString());
    }
}
