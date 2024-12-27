package com.android.server.audio;

import android.media.AudioSystem;

import com.android.server.utils.EventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioServiceEvents$ForceUseEvent extends EventLogger.Event {
    public final int mConfig;
    public final String mReason;
    public final int mUsage;

    public AudioServiceEvents$ForceUseEvent(int i, int i2, String str) {
        this.mUsage = i;
        this.mConfig = i2;
        this.mReason = str;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        return "setForceUse("
                + AudioSystem.forceUseUsageToString(this.mUsage)
                + ", "
                + AudioSystem.forceUseConfigToString(this.mConfig)
                + ") due to "
                + this.mReason;
    }
}
