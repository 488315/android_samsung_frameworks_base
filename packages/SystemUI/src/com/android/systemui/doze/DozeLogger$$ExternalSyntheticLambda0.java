package com.android.systemui.doze;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class DozeLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DozeLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Doze state sent to all DozeMachineParts stateSent=", logMessage.getStr1());
            case 1:
                return "Pulse start, reason=".concat(DozeLog.reasonToString(logMessage.getInt1()));
            case 2:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Screen on, pulsing=", logMessage.getBool1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Pulse dropped, why=", logMessage.getStr1());
            case 4:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Keyguard visibility change, isVisible=", logMessage.getBool1());
            case 5:
                return "Sensor triggered, type=".concat(DozeLog.reasonToString(logMessage.getInt1()));
            case 6:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dozing=", logMessage.getBool1());
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Doze immediately ended due to ", logMessage.getStr1());
            case 8:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Screen off, why=");
            case 9:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Delaying display state change to: ", logMessage.getStr1(), " due to UDFPS activity");
            case 10:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Keyguard bouncer changed, showing=", logMessage.getBool1());
            case 11:
                return "Pulse finish";
            case 12:
                return "Doze screen brightness " + (logMessage.getBool1() ? "set" : "requested") + " (float), brightness=" + logMessage.getDouble1();
            case 13:
                return "Emergency call";
            case 14:
                return "Pending unschedule time tick, isPending=" + logMessage.getBool1() + ", isTimeTickScheduled:" + logMessage.getBool2();
            case 15:
                return "Display wakefulness changed, isAwake=" + logMessage.getBool1() + ", reason=" + DozeLog.reasonToString(logMessage.getInt1());
            case 16:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Prox changed while pulsing. setIgnoreTouchWhilePulsing=", logMessage.getBool1());
            case 17:
                return "Unregister sensor. Success=" + logMessage.getBool1() + " sensor=" + logMessage.getStr1();
            case 18:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Pulse touch modified by prox, disabled=", logMessage.getBool1());
            case 19:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Missed AOD time tick by ", logMessage.getStr1());
            case 20:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("Fling expand=", " aboveThreshold=", " thresholdNeeded=", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), " screenOnFromTouch=", logMessage.getBool4());
            case 21:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("PickupWakeup withinVibrationThreshold=", logMessage.getBool1());
            case 22:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dozing changed dozing=", logMessage.getBool1());
            case 23:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "SensorEvent [", "] dropped, reason=", logMessage.getStr1());
            case 24:
                return "Register sensor. Success=" + logMessage.getBool1() + " sensor=" + logMessage.getStr1();
            case 25:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skipping sensor registration because its already registered. sensor=", logMessage.getStr1());
            case 26:
                String strReasonToString = DozeLog.reasonToString(logMessage.getInt1());
                boolean bool1 = logMessage.getBool1();
                long long1 = logMessage.getLong1();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Proximity result reason=", strReasonToString, " near=", " millis=", bool1);
                sbM.append(long1);
                return sbM.toString();
            case 27:
                return "Power save active=" + logMessage.getBool1() + " nextState=" + logMessage.getStr1();
            case 28:
                return "Always on (AOD) suppressed changed, suppressed=" + logMessage.getBool1() + " nextState=" + logMessage.getStr1();
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Pulse dropped, cannot pulse from=", logMessage.getStr1(), " state=", logMessage.getStr2());
        }
    }
}
