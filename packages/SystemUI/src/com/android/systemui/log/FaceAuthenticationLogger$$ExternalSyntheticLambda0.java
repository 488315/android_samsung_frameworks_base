package com.android.systemui.log;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FaceAuthenticationLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("skipping detection request because it is not supported, faceManager isNull: ", ", sensorPropertiesInternal isNullOrEmpty: ", ", supportsFaceDetection: ", bool1, bool2);
                m.append(bool3);
                return m.toString();
            case 1:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("canFaceAuthRun value changed to ", logMessage.getBool1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Running authenticate for ", logMessage.getStr1());
            case 3:
                return "Face authenticated successfully: userId: " + logMessage.getInt1() + ", isStrongBiometric: " + logMessage.getBool1();
            case 4:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Waiting to process request: reason: ", logMessage.getStr1(), ", canRunAuth: ", ", canRunDetect: ", logMessage.getBool1()), logMessage.getBool2(), ", cancelInProgress: ", logMessage.getBool3());
            case 5:
                return "Skipping running detection: isAuthRunning: " + logMessage.getBool1() + ", detectCancellationNotNull: " + logMessage.getBool2();
            case 6:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Received authentication error: errorCode: ", ", errString: ", logMessage.getStr1(), ", isLockoutError: "), logMessage.getBool1(), ", isCancellationError: ", logMessage.getBool2());
            case 7:
                return FakeFeatures$$ExternalSyntheticOutline0.m("Queueing ", logMessage.getStr1(), " request for face auth, fallbackToDetection: ", logMessage.getBool1());
            case 8:
                boolean bool12 = logMessage.getBool1();
                boolean bool22 = logMessage.getBool2();
                boolean bool32 = logMessage.getBool3();
                String str1 = logMessage.getStr1();
                StringBuilder m2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("Cancel signal was not received, running timeout handler to reset state. State before reset: isAuthRunning: ", ", isLockedOut: ", ", cancellationInProgress: ", bool12, bool22);
                m2.append(bool32);
                m2.append(", faceAuthRequestedWhileCancellation: ");
                m2.append(str1);
                return m2.toString();
            case 9:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Attempting face auth again because of HW error: retry attempt ");
            case 10:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Ignoring trigger because ", logMessage.getStr2(), ", Trigger reason: ", logMessage.getStr1());
            case 11:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Received face hardware error: ", logMessage.getStr1(), " , code: ");
            case 12:
                String str12 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder m3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clearing pending auth: ", str12, ", fallbackToDetection: ", str2, ", reason: ");
                m3.append(str3);
                return m3.toString();
            case 13:
                return FakeFeatures$$ExternalSyntheticOutline0.m("Processing face auth request: ", logMessage.getStr1(), ", fallbackToDetect: ", logMessage.getBool1());
            default:
                return "addlockoutResetCallback done";
        }
    }
}
