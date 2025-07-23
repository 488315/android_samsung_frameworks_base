package com.samsung.android.smartthingsmediasdk.mediasdk.base.debug;

import android.os.Build;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DLog {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "smartthings-mediasdk";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String formatMessage(String str, String str2, String str3) {
            StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str, "] ", str2, "() - ");
            m.append(str3);
            return m.toString();
        }

        public static void i(String str, String str2, String str3) {
            Log.i(DLog.TAG, formatMessage(str, str2, str3));
        }

        private Companion() {
        }
    }

    static {
        "user".equals(Build.TYPE);
    }
}
