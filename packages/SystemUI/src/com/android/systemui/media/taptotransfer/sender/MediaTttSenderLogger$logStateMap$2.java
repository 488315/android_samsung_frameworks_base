package com.android.systemui.media.taptotransfer.sender;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

final class MediaTttSenderLogger$logStateMap$2 extends Lambda implements Function1 {
    public static final MediaTttSenderLogger$logStateMap$2 INSTANCE = new MediaTttSenderLogger$logStateMap$2();

    public MediaTttSenderLogger$logStateMap$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Current sender states: ", ((LogMessage) obj).getStr1());
    }
}
