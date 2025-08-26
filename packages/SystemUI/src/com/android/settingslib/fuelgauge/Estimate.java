package com.android.settingslib.fuelgauge;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Estimate {
    public final long estimateMillis;

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

    public Estimate(long j, boolean z, long j2) {
        this.estimateMillis = j;
    }
}
