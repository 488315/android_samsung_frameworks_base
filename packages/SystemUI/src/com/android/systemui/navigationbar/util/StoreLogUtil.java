package com.android.systemui.navigationbar.util;

import com.android.systemui.basic.util.LogWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StoreLogUtil {
    public boolean allowLogging;
    public int lastDepth;
    public final LogWrapper logWrapper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
