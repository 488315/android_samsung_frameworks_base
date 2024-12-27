package com.android.systemui.util.kotlin;

import android.util.Log;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LogKt {
    public static final void logD(String str, Function0 function0) {
        if (Log.isLoggable(str, 3)) {
            Log.d(str, (String) function0.invoke());
        }
    }

    public static final void logI(String str, Function0 function0) {
        if (Log.isLoggable(str, 4)) {
            Log.i(str, (String) function0.invoke());
        }
    }

    public static final void logV(String str, Function0 function0) {
    }
}
