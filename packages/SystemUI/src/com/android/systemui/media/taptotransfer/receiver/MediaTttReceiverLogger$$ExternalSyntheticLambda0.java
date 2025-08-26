package com.android.systemui.media.taptotransfer.receiver;

import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaTttReceiverLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MediaTttReceiverLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "ripple animation for view with id=", " is started, animation type=", logMessage.getStr1());
            default:
                int i2 = MediaTttReceiverLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "ripple animation for view with id=", " is ended, animation type=", logMessage.getStr1());
        }
    }
}
