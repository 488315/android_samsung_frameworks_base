package com.android.systemui.media.controls.domain.pipeline;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class MediaTimeoutLogger$logTimeout$2 extends Lambda implements Function1 {
    public static final MediaTimeoutLogger$logTimeout$2 INSTANCE = new MediaTimeoutLogger$logTimeout$2();

    public MediaTimeoutLogger$logTimeout$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("execute timeout for ", ((LogMessage) obj).getStr1());
    }
}
