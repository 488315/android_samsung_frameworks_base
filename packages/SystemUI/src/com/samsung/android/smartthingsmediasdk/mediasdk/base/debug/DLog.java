package com.samsung.android.smartthingsmediasdk.mediasdk.base.debug;

import android.os.Build;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class DLog {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = "smartthings-mediasdk";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String formatMessage(String str, String str2, String str3) {
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str, "] ", str2, "() - ");
            sbM.append(str3);
            return sbM.toString();
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
