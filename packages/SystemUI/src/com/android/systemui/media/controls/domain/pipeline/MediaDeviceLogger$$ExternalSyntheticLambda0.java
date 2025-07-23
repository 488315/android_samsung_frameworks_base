package com.android.systemui.media.controls.domain.pipeline;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaDeviceLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MediaDeviceLogger.$r8$clinit;
                return logMessage.getStr1() + ", reason = " + logMessage.getInt1() + ", broadcastId = " + logMessage.getInt2();
            case 1:
                int i2 = MediaDeviceLogger.$r8$clinit;
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onBroadcastMetadataChanged, broadcastId = ", ", metadata = ", logMessage.getStr1());
            case 2:
                int i3 = MediaDeviceLogger.$r8$clinit;
                return logMessage.getStr1() + ", reason = " + logMessage.getInt1();
            case 3:
                int i4 = MediaDeviceLogger.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("New device name ", logMessage.getStr1());
            case 4:
                int i5 = MediaDeviceLogger.$r8$clinit;
                return MotionLayout$$ExternalSyntheticOutline0.m("Remote device: ", logMessage.getStr1(), " or ", logMessage.getStr2(), " or unknown");
            default:
                int i6 = MediaDeviceLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Local device: ", logMessage.getStr1(), " or ", logMessage.getStr2());
        }
    }
}
