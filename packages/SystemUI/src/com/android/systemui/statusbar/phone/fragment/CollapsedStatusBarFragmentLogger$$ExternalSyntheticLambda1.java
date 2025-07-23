package com.android.systemui.statusbar.phone.fragment;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CollapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        boolean bool1 = logMessage.getBool1();
        boolean bool2 = logMessage.getBool2();
        boolean bool3 = logMessage.getBool3();
        String str = logMessage.getInt1() == 1 ? "true" : "false";
        boolean bool4 = logMessage.getBool4();
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("New visibilities calculated internally. showClock=", " showNotificationIcons=", " showPrimaryOngoingActivityChip=", bool1, bool2);
        m.append(bool3);
        m.append(" showSecondaryOngoingActivityChip=");
        m.append(str);
        m.append("showSystemInfo=");
        m.append(bool4);
        return m.toString();
    }
}
