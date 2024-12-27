package com.android.systemui.media.controls.domain.pipeline;

import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
