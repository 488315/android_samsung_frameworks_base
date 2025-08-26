package com.android.systemui.volume.util;

import android.os.HandlerExecutor;
import android.os.HandlerThread;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeExecutor$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        VolumeExecutor volumeExecutor = VolumeExecutor.INSTANCE;
        HandlerThread handlerThread = new HandlerThread("VolumeExecutor");
        handlerThread.start();
        return new HandlerExecutor(handlerThread.getThreadHandler());
    }
}
