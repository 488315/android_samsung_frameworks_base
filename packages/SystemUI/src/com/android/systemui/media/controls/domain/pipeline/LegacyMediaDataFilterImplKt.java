package com.android.systemui.media.controls.domain.pipeline;

import android.os.SystemProperties;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class LegacyMediaDataFilterImplKt {
    public static final long SMARTSPACE_MAX_AGE = SystemProperties.getLong("debug.sysui.smartspace_max_age", TimeUnit.MINUTES.toMillis(30));

    public static /* synthetic */ void getSMARTSPACE_MAX_AGE$annotations() {
    }
}
