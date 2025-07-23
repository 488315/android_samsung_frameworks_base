package com.android.systemui.statusbar.notification.stack;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotificationStackScrollLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("addTransientRowToNssl from onViewRemovedInternal: childKey: ", logMessage.getStr1());
            case 1:
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str1 = logMessage.getStr1();
                StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("handleEmptySpaceClick: statusBarState: ", int1, " isTouchAClick: ", bool1, " isTouchBelowNotification: ");
                m.append(bool2);
                m.append(" motionEvent: ");
                m.append(str1);
                return m.toString();
            case 2:
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("updateSensitivenessWithAnimation from NSSL: shouldAnimate=", " isSensitive(hideSensitive)=", " isSensitiveContentProtectionActive=", bool12, bool22), logMessage.getBool3(), " isAnyProfilePublic=", logMessage.getBool4());
            case 3:
                String str12 = logMessage.getStr1();
                boolean bool13 = logMessage.getBool1();
                boolean bool23 = logMessage.getBool2();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("HUN animation skipped for unexpected hun state: key: ", str12, " expected: ", " actual: ", bool13);
                m2.append(bool23);
                return m2.toString();
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("heads up animation skipped: key: ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
            case 5:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("heads up animation added: ", logMessage.getStr1(), " with type ", logMessage.getStr2());
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("addTransientChildToContainer from onViewRemovedInternal: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
            case 7:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("addTransientRowTo unhandled ViewGroup from onViewRemovedInternal: childKey: ", logMessage.getStr1(), " -- ViewGroup: ", logMessage.getStr2());
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("transientNotificationRowTraversalCleaned: key: ", logMessage.getStr1(), " reason: ", logMessage.getStr2());
        }
    }
}
