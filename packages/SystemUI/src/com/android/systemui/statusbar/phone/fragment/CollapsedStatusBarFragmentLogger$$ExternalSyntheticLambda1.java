package com.android.systemui.statusbar.phone.fragment;

import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class CollapsedStatusBarFragmentLogger$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        boolean bool1 = logMessage.getBool1();
        boolean bool2 = logMessage.getBool2();
        boolean bool3 = logMessage.getBool3();
        String str = logMessage.getInt1() == 1 ? "true" : "false";
        boolean bool4 = logMessage.getBool4();
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("New visibilities calculated internally. showClock=", " showNotificationIcons=", " showPrimaryOngoingActivityChip=", bool1, bool2);
        sbM.append(bool3);
        sbM.append(" showSecondaryOngoingActivityChip=");
        sbM.append(str);
        sbM.append("showSystemInfo=");
        sbM.append(bool4);
        return sbM.toString();
    }
}
