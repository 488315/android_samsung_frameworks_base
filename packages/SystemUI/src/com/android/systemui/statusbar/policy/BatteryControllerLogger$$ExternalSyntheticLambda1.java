package com.android.systemui.statusbar.policy;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BatteryControllerLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = BatteryControllerLogger.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Received intent ", logMessage.getStr1());
            case 1:
                int i2 = BatteryControllerLogger.$r8$clinit;
                return "Detected test intent. Will not execute battery level callbacks.";
            case 2:
                int i3 = BatteryControllerLogger.$r8$clinit;
                return "Entering test mode for BATTERY_LEVEL_TEST intent";
            case 3:
                int i4 = BatteryControllerLogger.$r8$clinit;
                return FakeFeatures$$ExternalSyntheticOutline0.m("BatteryController INIT (", Integer.toHexString(logMessage.getInt1()), ") hasReceivedBattery=", logMessage.getBool1());
            case 4:
                int i5 = BatteryControllerLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("BatteryController CREATE (", Integer.toHexString(logMessage.getInt1()), ")");
            case 5:
                int i6 = BatteryControllerLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Sending onPowerSaveChanged callback with powerSave=", logMessage.getBool1());
            case 6:
                int i7 = BatteryControllerLogger.$r8$clinit;
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("Sending onBatteryLevelChanged callbacks with level=", int1, ", plugged=", bool1, ", charging=");
                m.append(bool2);
                return m.toString();
            default:
                int i8 = BatteryControllerLogger.$r8$clinit;
                return "Exiting test mode";
        }
    }
}
