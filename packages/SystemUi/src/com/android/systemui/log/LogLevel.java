package com.android.systemui.log;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum LogLevel {
    VERBOSE(2, "V"),
    DEBUG(3, ImsProfile.TIMER_NAME_D),
    INFO(4, ImsProfile.TIMER_NAME_I),
    WARNING(5, "W"),
    ERROR(6, ImsProfile.TIMER_NAME_E),
    WTF(7, "WTF");

    private final int nativeLevel;
    private final String shortString;

    LogLevel(int i, String str) {
        this.nativeLevel = i;
        this.shortString = str;
    }

    public final int getNativeLevel() {
        return this.nativeLevel;
    }

    public final String getShortString() {
        return this.shortString;
    }
}
