package com.android.systemui.volume.panel.shared;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Set volume: stream=", logMessage.getStr1(), " volume=");
            case 1:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Volume update received: stream=", logMessage.getStr1(), " volume=");
            case 2:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Volume update received: token=", logMessage.getStr1(), " volume=");
            case 3:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Volume update received: audio-sharing volume=");
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("State changed: ", logMessage.getStr1());
            case 5:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Set volume: audio-sharing volume=");
            case 6:
                return logMessage.getStr1() + " isAvailable=" + logMessage.getBool1();
            case 7:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Global state changed: isVisible=", logMessage.getBool1());
            default:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Set volume: token=", logMessage.getStr1(), " volume=");
        }
    }
}
