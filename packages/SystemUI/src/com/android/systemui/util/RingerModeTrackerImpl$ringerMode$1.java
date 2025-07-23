package com.android.systemui.util;

import android.media.AudioManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class RingerModeTrackerImpl$ringerMode$1 extends FunctionReferenceImpl implements Function0 {
    public RingerModeTrackerImpl$ringerMode$1(Object obj) {
        super(0, obj, AudioManager.class, "getRingerMode", "getRingerMode()I", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Integer invoke() {
        return Integer.valueOf(((AudioManager) this.receiver).getRingerMode());
    }
}
