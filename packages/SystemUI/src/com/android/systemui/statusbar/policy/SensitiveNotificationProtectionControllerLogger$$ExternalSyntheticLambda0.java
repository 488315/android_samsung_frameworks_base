package com.android.systemui.statusbar.policy;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class SensitiveNotificationProtectionControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "Projection ended - protection disabled";
            default:
                return "Projection started - protection enabled:" + logMessage.getBool1() + ", pkg=" + logMessage.getStr1();
        }
    }
}
