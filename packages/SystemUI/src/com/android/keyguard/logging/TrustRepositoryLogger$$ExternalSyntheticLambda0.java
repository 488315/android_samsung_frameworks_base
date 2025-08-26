package com.android.keyguard.logging;

import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class TrustRepositoryLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = TrustRepositoryLogger.$r8$clinit;
                return "trustManagedModel emitted: userId: " + logMessage.getInt1() + ", isTrustManaged: " + logMessage.getBool1();
            case 1:
                int i2 = TrustRepositoryLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isCurrentUserActiveUnlockRunning emitted: ", logMessage.getBool1());
            case 2:
                int i3 = TrustRepositoryLogger.$r8$clinit;
                return "activeUnlockModel emitted: userId: " + logMessage.getInt1() + " isRunning: " + logMessage.getBool1();
            case 3:
                int i4 = TrustRepositoryLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isCurrentUserTrusted emitted: ", logMessage.getBool1());
            case 4:
                int i5 = TrustRepositoryLogger.$r8$clinit;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isTrustManaged emitted: ", logMessage.getBool1());
            case 5:
                int i6 = TrustRepositoryLogger.$r8$clinit;
                return "trustModel emitted: userId: " + logMessage.getInt1() + " isTrusted: " + logMessage.getBool1();
            case 6:
                int i7 = TrustRepositoryLogger.$r8$clinit;
                return "onTrustManagedChanged isTrustManaged: " + logMessage.getBool1() + " for user: " + logMessage.getInt1();
            default:
                int i8 = TrustRepositoryLogger.$r8$clinit;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("onTrustChanged enabled: ", ", newlyUnlocked: ", ", userId: ", bool1, bool2);
                ViewPager$$ExternalSyntheticOutline0.m(sbM, int1, ", flags: ", int2, ", grantMessages: ");
                sbM.append(str1);
                return sbM.toString();
        }
    }
}
