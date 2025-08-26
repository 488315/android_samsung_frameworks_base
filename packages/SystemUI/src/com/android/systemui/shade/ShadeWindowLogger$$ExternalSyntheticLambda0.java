package com.android.systemui.shade;

import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeWindowLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ShadeWindowLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Updating shade, should be focusable : ", logMessage.getBool1());
            case 1:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Updating shade, should be visible and focusable: ", logMessage.getBool1());
            default:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Updating visibility, should be visible : ", logMessage.getBool1());
        }
    }
}
