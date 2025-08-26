package com.android.keyguard.logging;

import android.os.PowerManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "startListeningForFace(): ", ", reason: ", str1, " ");
                sbM.append(str2);
                return sbM.toString();
            case 1:
                return "sendPrimaryBouncerChanged primaryBouncerIsOrWillBeShowing=" + logMessage.getBool1() + " primaryBouncerFullyShown=" + logMessage.getBool2();
            case 2:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "handleFaceLockoutReset: ");
            case 3:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Fingerprint disabled by DPM for userId: ");
            case 4:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onUdfpsPointerDown, sensorId: ");
            case 5:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "userUnlocked userId: ");
            case 6:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("handlePhoneStateChanged(", logMessage.getStr1(), ")");
            case 7:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Retrying face after HW unavailable, attempt ");
            case 8:
                int int2 = logMessage.getInt2();
                int int12 = logMessage.getInt1();
                String str12 = logMessage.getStr1();
                StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(int2, int12, "Fingerprint scheduling retry auth after ", " ms due to(", ") -> ");
                sbM2.append(str12);
                return sbM2.toString();
            case 9:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("TaskStackChanged for ACTIVITY_TYPE_ASSISTANT, assistant visible: ", logMessage.getBool1());
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("handleTimeFormatUpdate timeFormat=", logMessage.getStr1());
            case 11:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Retrying fingerprint attempt: ");
            case 12:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "No Profile Owner or Device Owner supervision app found for User ");
            case 13:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "userCurrentlySwitching: ", logMessage.getStr1(), ", userId: ");
            case 14:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Fingerprint error received: ", logMessage.getStr1(), " msgId= ");
            case 15:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("allowFingerprintOnCurrentOccludingActivityChanged: ", logMessage.getBool1());
            case 16:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skip requesting active unlock from wake reason that doesn't trigger face auth reason=", PowerManager.wakeReasonToString(logMessage.getInt1()));
            case 17:
                int int13 = logMessage.getInt1();
                String str13 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                StringBuilder sbM3 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int13, "action SERVICE_PROVIDERS_UPDATED subId=", " spn=", str13, " plmn=");
                sbM3.append(str22);
                return sbM3.toString();
            case 18:
                return "userStopped userId: " + logMessage.getInt1() + " isUnlocked: " + logMessage.getBool1();
            case 19:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("notifying about enrollments changed: ", logMessage.getStr1());
            case 20:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("received broadcast ", logMessage.getStr1());
            case 21:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skip updating face listening state on wakeup from ", logMessage.getStr1());
            case 22:
                boolean bool1 = logMessage.getBool1();
                int int14 = logMessage.getInt1();
                long long1 = logMessage.getLong1();
                String str14 = logMessage.getStr1();
                int int22 = logMessage.getInt2();
                long long2 = logMessage.getLong2();
                StringBuilder sbM4 = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("handleBatteryUpdate: isNotNull: ", int14, " BatteryStatus{status= ", bool1, ", level=");
                sbM4.append(long1);
                sbM4.append(", plugged=");
                sbM4.append(str14);
                sbM4.append(", chargingStatus=");
                sbM4.append(int22);
                sbM4.append(", maxChargingWattage= ");
                return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(long2, "}", sbM4);
            case 23:
                return "onAuthInterruptDetected(" + logMessage.getBool1() + ")";
            case 24:
                String str15 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                boolean bool12 = logMessage.getBool1();
                StringBuilder sbM5 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("reportUserRequestedUnlock origin=", str15, " reason=", str23, " dismissKeyguard=");
                sbM5.append(bool12);
                return sbM5.toString();
            case 25:
                return "userUnlockedInitialState userId: " + logMessage.getInt1() + " isUnlocked: " + logMessage.getBool1();
            case 26:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("DEVICE_PROVISIONED state = ", logMessage.getBool1());
            case 27:
                return "handlePrimaryBouncerChanged primaryBouncerIsOrWillBeShowing=" + logMessage.getBool1() + " primaryBouncerFullyShown=" + logMessage.getBool2();
            case 28:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("initiate active unlock triggerReason=", logMessage.getStr1());
            default:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Updating mAssistantVisible to new value: ", logMessage.getBool1());
        }
    }
}
