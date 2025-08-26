package com.android.systemui.log;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenDecorationsLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ScreenDecorationsLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Hwc layer present camera protection bounds: ", logMessage.getStr1());
            case 1:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Resolution changed, deferring size change to ", logMessage.getStr2(), ", staying at ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Face scanning overlay present camera protection bounds: ", logMessage.getStr1());
            case 3:
                return "Face scanning animation: widthMeasureSpec: " + logMessage.getLong1() + " measuredWidth: " + logMessage.getInt1() + ", heightMeasureSpec: " + logMessage.getLong2() + " measuredHeight: " + logMessage.getInt2();
            case 4:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Reinflating view: Face sensor location: ", logMessage.getStr1(), ", faceScanningHeight: ");
            case 5:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "DisplayCutoutView id=", " present, camera protection bounds: ", logMessage.getStr1());
            case 6:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                String str2 = logMessage.getStr2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("cameraProtectionShownOrHidden showAnimationNow: ", str1, ", isFaceDetectionRunning: ", ", isBiometricPromptShowing: ", bool1);
                sbM.append(bool2);
                sbM.append(", faceAuthenticated: ");
                sbM.append(str2);
                sbM.append(", isCameraActive: ");
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, bool3, ", currentState: ", bool4);
            case 7:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Bounding rect ", logMessage.getStr1(), " : ", logMessage.getStr2());
            case 8:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "Rotation changed from ", " to ");
            case 9:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "UserSwitched newUserId=", ". Updating color inversion setting");
            default:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt2(), logMessage.getInt2(), "Rotation changed, deferring ", ", staying at ");
        }
    }
}
