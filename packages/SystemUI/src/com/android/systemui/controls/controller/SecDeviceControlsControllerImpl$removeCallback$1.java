package com.android.systemui.controls.controller;

import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import java.util.function.Consumer;

public final class SecDeviceControlsControllerImpl$removeCallback$1 implements Consumer {
    public final /* synthetic */ SecDeviceControlsControllerImpl this$0;

    public SecDeviceControlsControllerImpl$removeCallback$1(SecDeviceControlsControllerImpl secDeviceControlsControllerImpl) {
        this.this$0 = secDeviceControlsControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ControlsListingControllerImpl) ((ControlsListingController) obj)).removeCallback(this.this$0.listingCallback);
    }
}
