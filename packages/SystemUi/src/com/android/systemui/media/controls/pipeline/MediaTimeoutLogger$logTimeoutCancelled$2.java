package com.android.systemui.media.controls.pipeline;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final class MediaTimeoutLogger$logTimeoutCancelled$2 extends Lambda implements Function1 {
    public static final MediaTimeoutLogger$logTimeoutCancelled$2 INSTANCE = new MediaTimeoutLogger$logTimeoutCancelled$2();

    public MediaTimeoutLogger$logTimeoutCancelled$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return FontProvider$$ExternalSyntheticOutline0.m32m("timeout cancelled for ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
    }
}
