package com.android.systemui.util;

import android.media.AudioManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public /* synthetic */ class RingerModeTrackerImpl$ringerMode$1 extends FunctionReferenceImpl implements Function0 {
    public RingerModeTrackerImpl$ringerMode$1(Object obj) {
        super(0, obj, AudioManager.class, "getRingerMode", "getRingerMode()I", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return Integer.valueOf(((AudioManager) this.receiver).getRingerMode());
    }
}
