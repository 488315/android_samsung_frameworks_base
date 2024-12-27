package com.android.systemui.media.controls.domain.pipeline;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class MediaTimeoutLogger$logTimeoutCancelled$2 extends Lambda implements Function1 {
    public static final MediaTimeoutLogger$logTimeoutCancelled$2 INSTANCE = new MediaTimeoutLogger$logTimeoutCancelled$2();

    public MediaTimeoutLogger$logTimeoutCancelled$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return FontProvider$$ExternalSyntheticOutline0.m("timeout cancelled for ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
    }
}
