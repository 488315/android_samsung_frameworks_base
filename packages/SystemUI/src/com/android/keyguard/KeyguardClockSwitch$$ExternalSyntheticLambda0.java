package com.android.keyguard;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardClockSwitch$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        int i = KeyguardClockSwitch.$r8$clinit;
        return "updateClockViews; useLargeClock=" + logMessage.getBool1() + "; animate=" + logMessage.getBool2() + "; mChildrenAreLaidOut=" + logMessage.getBool3();
    }
}
