package com.android.systemui.doze;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.DevicePostureController;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DozeLogger$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ValueAnimator$$ExternalSyntheticOutline0.m("Doze aod dimming scrim opacity set, opacity=", logMessage.getLong1());
            case 1:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Posture changed, posture=", DevicePostureController.devicePostureToString(logMessage.getInt1()), " partUpdated=", logMessage.getStr1());
            case 2:
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Unregister sensor. reason=", str2, ". Success=", " sensor=", bool1);
                m.append(str1);
                return m.toString();
            case 3:
                return "Notification pulse";
            case 4:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                String str22 = logMessage.getStr2();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Pulse-", str12, " dozing=", " pulseReason=", bool12);
                m2.append(str22);
                return m2.toString();
            case 5:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Always-on state suppressed, suppressed state=", logMessage.getStr1(), " reason=", logMessage.getStr2());
            case 6:
                return "Doze car mode started";
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Doze state changed to ", logMessage.getStr1());
            case 8:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Display state ", logMessage.getBool1() ? "changed" : "requested", " to ", logMessage.getStr1());
            default:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Doze screen brightness ", logMessage.getBool1() ? "set" : "requested", " (int), brightness=");
        }
    }
}
