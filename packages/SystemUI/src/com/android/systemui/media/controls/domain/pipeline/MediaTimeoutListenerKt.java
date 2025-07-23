package com.android.systemui.media.controls.domain.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaTimeoutListenerKt {
    public static final long PAUSED_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout", TimeUnit.MINUTES.toMillis(10));
    public static final long RESUME_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout_resume", TimeUnit.DAYS.toMillis(2));

    public static /* synthetic */ void getPAUSED_MEDIA_TIMEOUT$annotations() {
    }

    public static /* synthetic */ void getRESUME_MEDIA_TIMEOUT$annotations() {
    }
}
