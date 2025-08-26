package com.android.systemui.statusbar.pipeline.satellite.ui.viewmodel;

import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DeviceBasedSatelliteViewModelImpl$carrierText$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "Updating carrier text. shouldShow=" + logMessage.getBool1() + " connectionState=" + logMessage.getStr1();
    }
}
