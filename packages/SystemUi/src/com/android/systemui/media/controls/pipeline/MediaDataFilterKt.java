package com.android.systemui.media.controls.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaDataFilterKt {
    public static final long SMARTSPACE_MAX_AGE = SystemProperties.getLong("debug.sysui.smartspace_max_age", TimeUnit.MINUTES.toMillis(30));

    public static /* synthetic */ void getSMARTSPACE_MAX_AGE$annotations() {
    }
}
