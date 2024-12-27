package com.android.systemui.media.taptotransfer.sender;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
