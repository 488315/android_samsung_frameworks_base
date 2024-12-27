package com.android.systemui.media.controls.domain.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

public abstract class LegacyMediaDataFilterImplKt {
    public static final long SMARTSPACE_MAX_AGE = SystemProperties.getLong("debug.sysui.smartspace_max_age", TimeUnit.MINUTES.toMillis(30));

    public static /* synthetic */ void getSMARTSPACE_MAX_AGE$annotations() {
    }
}
