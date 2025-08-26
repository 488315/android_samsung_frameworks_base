package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.satellite.data.prod.DeviceBasedSatelliteRepositoryImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceBasedSatelliteRepositoryImpl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                DeviceBasedSatelliteRepositoryImpl.Companion companion = DeviceBasedSatelliteRepositoryImpl.Companion;
                return "Satellite manager is null";
            case 1:
                DeviceBasedSatelliteRepositoryImpl.Companion companion2 = DeviceBasedSatelliteRepositoryImpl.Companion;
                return "Waiting " + logMessage.getLong1() + " ms before checking for satellite support";
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Checked for system support. support=", logMessage.getStr1());
            case 3:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onSatelliteModemStateChanged: state=");
            case 4:
                return "registerForCommunicationAccessStateChanged";
            case 5:
                return "unRegisterForCommunicationAccessStateChanged";
            case 6:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("onSatelliteCommunicationAccessAllowedStateChanged: ", logMessage.getBool1());
            case 7:
                return "registerForProvisionStateChanged";
            case 8:
                return "Registered for signal strength successfully";
            case 9:
                return "Unregistered for signal strength successfully";
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onNtnSignalStrengthChanged: level=");
        }
    }
}
