package com.android.systemui.privacy.logging;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class PrivacyLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Privacy Dot ", logMessage.getStr1());
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Privacy dialog shown. Contents: ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Profiles changed: ", logMessage.getStr1());
            case 3:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Start settings activity from dialog for packageName=", logMessage.getStr1(), ", userId=", " ");
            case 4:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "StatusBar applied alpha=");
            case 5:
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "App Op: ", " for ", str1, "(");
                sbM.append(int2);
                sbM.append("), active=");
                sbM.append(bool1);
                return sbM.toString();
            case 6:
                boolean bool12 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                StringBuilder sbM2 = EmergencyButtonController$$ExternalSyntheticOutline0.m("Status bar icons visible: camera=", ", microphone=", ", location=", bool12, bool2);
                sbM2.append(bool3);
                return sbM2.toString();
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Updating items scheduled for ", logMessage.getStr1());
            case 8:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Holding items: ", logMessage.getStr1());
            case 9:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Chip visible: ", logMessage.getBool1());
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Perm group usage: ", logMessage.getStr1());
            case 11:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Retrieved list to process: ", logMessage.getStr1());
            case 12:
                return logMessage.getBool1() ? "Showing chip: Keyguard dismissed" : "Hiding chip: Keyguard visible";
            case 13:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Chip view is ", logMessage.getBool1() ? "created" : "removed", ", chip width=");
            default:
                return "Privacy dialog dismissed";
        }
    }
}
