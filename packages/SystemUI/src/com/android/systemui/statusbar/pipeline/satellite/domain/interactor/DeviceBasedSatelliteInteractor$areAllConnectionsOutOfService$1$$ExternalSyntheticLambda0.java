package com.android.systemui.statusbar.pipeline.satellite.domain.interactor;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceBasedSatelliteInteractor$areAllConnectionsOutOfService$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Updating OOS status. allConnectionsOOs=" + logMessage.getBool1() + " deviceEmergencyOnly=" + logMessage.getBool2();
    }
}
