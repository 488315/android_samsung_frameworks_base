package com.android.systemui.statusbar.notification.stack;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationChildrenContainerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotificationChildrenContainerLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = NotificationChildrenContainerLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("removeTransientRow: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
            default:
                int i2 = NotificationChildrenContainerLogger.$r8$clinit;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("addTransientRow: childKey: ", str1, " -- containerKey: ", str2, " -- index: ");
                m.append(int1);
                return m.toString();
        }
    }
}
