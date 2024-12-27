package com.android.keyguard.punchhole;

import android.os.SystemProperties;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VIDirectorFactory {
    public static final Companion Companion = new Companion(null);
    public static final String vendorName = SystemProperties.get("ro.product.vendor.name").toLowerCase();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
