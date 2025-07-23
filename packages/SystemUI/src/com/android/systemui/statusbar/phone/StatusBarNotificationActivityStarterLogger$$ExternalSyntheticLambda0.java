package com.android.systemui.statusbar.phone;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarNotificationActivityStarterLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No Fullscreen intent: suppressed by VR mode: ", logMessage.getStr1());
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Sending contentIntentFailed: ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onNotificationClicked called for non-clickable notification! ", logMessage.getStr1());
            case 3:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("(1/5) onNotificationClicked: ", str1, " isHeadsUpState: ", " isKeyguardVisible: ", bool1), logMessage.getBool2(), " isPanelExpanded: ", logMessage.getBool3());
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Closing remote input for ", logMessage.getStr1());
            case 5:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Notification ", logMessage.getStr1(), " has fullScreenIntent; sending fullScreenIntent ", logMessage.getStr2());
            case 6:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("(2/5) handleNotificationClickAfterKeyguardDismissed: ", logMessage.getStr1());
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("(3/5) handleNotificationClickAfterPanelCollapsed: ", logMessage.getStr1());
            case 8:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Expanding bubble for ", logMessage.getStr1(), " (rather than firing intent)");
            case 9:
                String str2 = logMessage.getStr2();
                String str12 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("(5/5) Started intent ", str2, " for notification ", str12, " with result code ");
                m.append(int1);
                return m.toString();
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("(4/5) startNotificationIntent: ", logMessage.getStr1());
        }
    }
}
