package com.android.systemui.statusbar.chips.ui.viewmodel;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingActivityChipsViewModel$incomingChipBundle$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Chips: ScreenRecord=", str1, " > ShareToApp=", str2, " > CastToOther="), logMessage.getStr3(), "...");
            case 1:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("... > Call=", logMessage.getStr1(), " > Notifs=", logMessage.getStr2());
            default:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isScreenReasonablyLarge: ", logMessage.getBool1());
        }
    }
}
