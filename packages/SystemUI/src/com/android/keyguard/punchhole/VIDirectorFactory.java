package com.android.keyguard.punchhole;

import android.os.SystemProperties;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class VIDirectorFactory {
    public static final Companion Companion = new Companion(null);
    public static final String vendorName = SystemProperties.get("ro.product.vendor.name").toLowerCase(Locale.ROOT);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
