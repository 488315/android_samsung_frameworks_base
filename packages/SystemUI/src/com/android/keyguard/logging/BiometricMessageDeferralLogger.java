package com.android.keyguard.logging;

import com.android.systemui.log.LogBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BiometricMessageDeferralLogger {
    public final LogBuffer logBuffer;
    public final String tag;

    public BiometricMessageDeferralLogger(LogBuffer logBuffer, String str) {
        this.logBuffer = logBuffer;
        this.tag = str;
    }
}
