package com.android.systemui.media.controls.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaTimeoutListenerKt {
    public static final long PAUSED_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout", TimeUnit.MINUTES.toMillis(10));
    public static final long RESUME_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout_resume", TimeUnit.DAYS.toMillis(2));

    public static /* synthetic */ void getPAUSED_MEDIA_TIMEOUT$annotations() {
    }

    public static /* synthetic */ void getRESUME_MEDIA_TIMEOUT$annotations() {
    }
}
