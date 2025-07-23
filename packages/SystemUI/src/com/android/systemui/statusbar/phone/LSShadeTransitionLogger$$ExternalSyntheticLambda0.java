package com.android.systemui.statusbar.phone;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class LSShadeTransitionLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LSShadeTransitionLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "Blocking swipe up on lockscreen panel";
            case 1:
                return "Notified that the keyguard is being hidden";
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Drag down succeeded on ", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Dragged down in locked down shade on ", logMessage.getStr1());
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Tried to drag down but can't drag down on ", logMessage.getStr1());
            case 5:
                return "Trying to go to locked shade ".concat(logMessage.getBool1() ? "from keyguard" : "not from keyguard");
            case 6:
                return "The shade was disabled when trying to go to the locked shade";
            case 7:
                return "Pulse height stuck and reset after shade was fully collapsed";
            case 8:
                return "Drag down amount stuck and reset after shade was fully collapsed";
            case 9:
                return "Going to the Locked Shade has been aborted";
            case 10:
                return "Pulse Expansion has started";
            case 11:
                return "The drag down amount has been reset to 0f.";
            case 12:
                return "Pulse Expansion is requested to cancel";
            case 13:
                return "Pulse Expansion is requested to finish";
            case 14:
                return ValueAnimator$$ExternalSyntheticOutline0.m("Default animation started to full shade with delay ", logMessage.getLong1());
            case 15:
                return "Pulse animation cancelled";
            case 16:
                return "drag down animation cancelled";
            case 17:
                return "The drag down was aborted and reset to 0f.";
            case 18:
                return "Drag down amount animating to " + logMessage.getDouble1();
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("The drag down has started on ", logMessage.getStr1());
        }
    }
}
