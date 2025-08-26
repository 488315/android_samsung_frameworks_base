package com.android.keyguard.logging;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ScrimLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        String str = ScrimLogger.TAG;
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), ": ", logMessage.getStr2());
    }
}
