package com.android.systemui.audio.soundcraft.interfaces.wearable.requester;

import android.os.Handler;
import android.os.Looper;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class BudsPluginServiceRequester$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = BudsPluginServiceRequester.$r8$clinit;
        return new Handler(Looper.getMainLooper());
    }
}
