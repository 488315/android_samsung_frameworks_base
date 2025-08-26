package com.android.systemui.haptics.qs;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSLongPressEffect$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        int i = QSLongPressEffect.$r8$clinit;
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        String str3 = logMessage.getStr3();
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[long-press effect on ", str1, " tile] ", str2, " on state: ");
        sbM.append(str3);
        return sbM.toString();
    }
}
