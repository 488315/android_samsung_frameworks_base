package com.android.server.devicestate;

import java.util.function.Consumer;

public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ OverrideRequestController f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.cancelRequest((OverrideRequest) obj);
    }
}
