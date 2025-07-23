package com.android.keyguard.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.TrustGrantFlags;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "userRemoved userId: ");
            case 1:
                return "Cancellation signal is not null, high chance of bug in fp auth lifecycle management. FP state: " + logMessage.getInt1() + ", unlockPossible: " + logMessage.getBool1();
            case 2:
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onTrustChanged[user=", int1, "] wasTrusted=", bool1, " isNowTrusted=");
                m.append(bool2);
                return m.toString();
            case 3:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "stopListeningForFace(): currentFaceRunningState: ", ", reason: ", logMessage.getStr1());
            case 4:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "userSwitchComplete: ", logMessage.getStr1(), ", userId: ");
            case 5:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "MSG_BIOMETRIC_AUTHENTICATION_CONTINUE already queued up, ignoring updating FP listening state to ");
            case 6:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isUnlockWithFacePossible: ", logMessage.getBool1());
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("*** register callback for ", logMessage.getStr1());
            case 8:
                int int12 = logMessage.getInt1();
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                String str1 = logMessage.getStr1();
                StringBuilder m2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("trustUsuallyManaged changed for userId: ", int12, " old: ", bool12, ", new: ");
                m2.append(bool22);
                m2.append(" context: ");
                m2.append(str1);
                return m2.toString();
            case 9:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("keepUnlockedOnFold changed to: ", logMessage.getBool1());
            case 10:
                String str12 = logMessage.getStr1();
                str12.getClass();
                return str12;
            case 11:
                return "Cancellation signal is not null, high chance of bug in face auth lifecycle management. Face state: " + logMessage.getInt1() + ", unlockPossible: " + logMessage.getBool1();
            case 12:
                int int2 = logMessage.getInt2();
                boolean bool13 = logMessage.getBool1();
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(logMessage.getInt1());
                String str13 = logMessage.getStr1();
                StringBuilder m3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("trustGrantedWithFlags[user=", int2, "] newlyUnlocked=", bool13, " flags=");
                m3.append(trustGrantFlags);
                m3.append(" message=");
                m3.append(str13);
                return m3.toString();
            case 13:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "faceRunningState: ");
            case 14:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "fingerprintRunningState: ");
            case 15:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Face authenticated for wrong user: ");
            case 16:
                return "Fingerprint auth successful: userId: " + logMessage.getInt1() + ", isStrongBiometric: " + logMessage.getBool1();
            case 17:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Face authentication disabled by DPM for userId: ");
            case 18:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "handleFingerprintLockoutReset: ");
            case 19:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("#update secure=", " canDismissKeyguard=", " trusted=", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), " trustManaged=", logMessage.getBool4());
            case 20:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Face auth succeeded for user ");
            case 21:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("requestFaceAuth() reason=", logMessage.getStr1());
            case 22:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("*** unregister callback for ", logMessage.getStr1());
            case 23:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Fingerprint authenticated for wrong user: ");
            case 24:
                return MoveResult$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("keyguardShowingChanged(showing=", " occluded=", " visible=", logMessage.getBool1(), logMessage.getBool2()), logMessage.getBool3(), ")");
            case 25:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Face error received: ", logMessage.getStr1(), " msgId= ");
            case 26:
                return "reporting successful biometric unlock: isStrongBiometric: " + logMessage.getBool1() + ", userId: " + logMessage.getInt1();
            case 27:
                return "Fingerprint detected: userId: " + logMessage.getInt1() + ", isStrongBiometric: " + logMessage.getBool1();
            case 28:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onUdfpsPointerUp, sensorId: ");
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "fingerprint acquire message: ");
        }
    }
}
