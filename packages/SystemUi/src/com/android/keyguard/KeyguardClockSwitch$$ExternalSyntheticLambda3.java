package com.android.keyguard;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardClockSwitch$$ExternalSyntheticLambda3 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        int i = KeyguardClockSwitch.$r8$clinit;
        return "updateClockViews; useLargeClock=" + logMessage.getBool1() + "; animate=" + logMessage.getBool2() + "; mChildrenAreLaidOut=" + logMessage.getBool3();
    }
}
