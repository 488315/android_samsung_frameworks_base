package com.android.systemui.navigationbar.util;

import com.android.systemui.basic.util.LogWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StoreLogUtil {
    public boolean allowLogging;
    public int lastDepth;
    public final LogWrapper logWrapper;
    public final boolean loggingStarted = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.logWrapper.d("Store", sb.toString());
    }
}
