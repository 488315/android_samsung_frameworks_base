package com.android.keyguard.logging;

import com.android.systemui.log.LogBuffer;

/* loaded from: classes.dex */
public class BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public BiometricMessageDeferralLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }
}
