package com.samsung.android.sdk.routines.automationservice.internal;

/* loaded from: classes4.dex */
public final class Log {
    public static final Log INSTANCE = new Log();

    private Log() {
    }

    public static void e(String str, String str2) {
        android.util.Log.e("Routine@AutomationService[1.1.6]: ".concat(str), str2);
    }

    public static void i(String str, String str2) {
        android.util.Log.i("Routine@AutomationService[1.1.6]: ".concat(str), str2);
    }
}
