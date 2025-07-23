package com.android.systemui.shade;

import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class GlanceableHubContainerController$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = GlanceableHubContainerController.$r8$clinit;
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("Lockscreen touch ignored: touchOnNotifications: ", ", touchOnUmo: ", ", touchOnSmartspace: ", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), ", glanceableHubV2: ", logMessage.getBool4());
            case 1:
                int i2 = GlanceableHubContainerController.$r8$clinit;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(int1, int2, "Touch started. x: ", ", y: ", ", hubShowing: ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool1, ", isDreaming: ", bool2, ", onLockscreen: ");
                m.append(bool3);
                return m.toString();
            case 2:
                int i3 = GlanceableHubContainerController.$r8$clinit;
                String str = logMessage.getBool1() ? "up" : "cancel";
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                boolean bool22 = logMessage.getBool2();
                boolean bool32 = logMessage.getBool3();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "Touch ended with ", str, ". x: ", ", y: ");
                m888m.append(int22);
                m888m.append(", shadeConsumingTouches: ");
                m888m.append(bool22);
                m888m.append(", anyBouncerShowing: ");
                m888m.append(bool32);
                return m888m.toString();
            case 3:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("New value for shadeShowingAndConsumingTouches: ", logMessage.getBool1());
            case 4:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("New value for anyBouncerShowing: ", logMessage.getBool1());
            default:
                String str1 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                boolean bool23 = logMessage.getBool2();
                boolean bool33 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Touch handler lifecycle changed to ", str1, ". hubShowing: ", ", shadeShowingAndConsumingTouches: ", bool12);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m2, bool23, ", anyBouncerShowing: ", bool33, ", inEditModeTransition: ");
                m2.append(bool4);
                return m2.toString();
        }
    }
}
