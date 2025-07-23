package com.android.systemui.media.muteawait;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaMuteAwaitLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Muted device added: address=", str1, " name=", str2, " hasMediaUsage=");
                m.append(bool1);
                return m.toString();
            default:
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Muted device removed: address=", str12, " name=", str22, " hasMediaUsage="), logMessage.getBool1(), " isMostRecentDevice=", logMessage.getBool2());
        }
    }
}
