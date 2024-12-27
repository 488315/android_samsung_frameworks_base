package com.android.systemui.media.controls.domain.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class MediaTimeoutListenerKt {
    public static final long PAUSED_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout", TimeUnit.MINUTES.toMillis(10));
    public static final long RESUME_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout_resume", TimeUnit.DAYS.toMillis(2));

    public static /* synthetic */ void getPAUSED_MEDIA_TIMEOUT$annotations() {
    }

    public static /* synthetic */ void getRESUME_MEDIA_TIMEOUT$annotations() {
    }
}
