package com.android.systemui.statusbar.notification;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RemoteInputControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = RemoteInputControllerLogger.$r8$clinit;
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                String str3 = logMessage.getStr3();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("removeRemoteInput reason: ", str2, " entry: ", str1, ", style: ");
                m.append(str3);
                m.append(", remoteEditImeVisible: ");
                m.append(bool1);
                m.append(", remoteEditImeAnimatingAway: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool2, ", isRemoteInputActiveForEntry: ", bool3, ", isRemoteInputActive: ");
                m.append(bool4);
                return m.toString();
            case 1:
                int i2 = RemoteInputControllerLogger.$r8$clinit;
                String str22 = logMessage.getStr2();
                String str12 = logMessage.getStr1();
                return TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("removeRemoteInput[apply is skipped] reason: ", str22, "for entry: ", str12, ", style: "), logMessage.getStr3(), " ");
            default:
                int i3 = RemoteInputControllerLogger.$r8$clinit;
                String str23 = logMessage.getStr2();
                String str13 = logMessage.getStr1();
                String str32 = logMessage.getStr3();
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("addRemoteInput reason:", str23, " entry: ", str13, ", style:");
                m2.append(str32);
                m2.append(", isAlreadyActive: ");
                m2.append(bool12);
                m2.append(", isFound:");
                m2.append(bool22);
                return m2.toString();
        }
    }
}
