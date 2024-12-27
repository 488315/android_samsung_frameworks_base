package com.android.systemui.media.controls.ui.controller;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaViewLogger$logMediaLocation$2 extends Lambda implements Function1 {
    public static final MediaViewLogger$logMediaLocation$2 INSTANCE = new MediaViewLogger$logMediaLocation$2();

    public MediaViewLogger$logMediaLocation$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        String str1 = logMessage.getStr1();
        int int1 = logMessage.getInt1();
        int int2 = logMessage.getInt2();
        StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "location (", str1, "): ", " -> ");
        m.append(int2);
        return m.toString();
    }
}
