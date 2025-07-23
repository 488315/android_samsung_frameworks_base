package com.android.keyguard.punchhole;

import android.os.SystemProperties;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VIDirectorFactory {
    public static final Companion Companion = new Companion(null);
    public static final String vendorName = SystemProperties.get("ro.product.vendor.name").toLowerCase(Locale.ROOT);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
