package com.android.systemui.util;

import android.media.AudioManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final /* synthetic */ class RingerModeTrackerImpl$ringerModeInternal$1 extends FunctionReferenceImpl implements Function0 {
    public RingerModeTrackerImpl$ringerModeInternal$1(Object obj) {
        super(0, obj, AudioManager.class, "getRingerModeInternal", "getRingerModeInternal()I", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Integer invoke() {
        return Integer.valueOf(((AudioManager) this.receiver).getRingerModeInternal());
    }
}
