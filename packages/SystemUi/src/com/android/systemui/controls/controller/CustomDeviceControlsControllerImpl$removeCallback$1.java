package com.android.systemui.controls.controller;

import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomDeviceControlsControllerImpl$removeCallback$1 implements Consumer {
    public final /* synthetic */ CustomDeviceControlsControllerImpl this$0;

    public CustomDeviceControlsControllerImpl$removeCallback$1(CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl) {
        this.this$0 = customDeviceControlsControllerImpl;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((ControlsListingControllerImpl) ((ControlsListingController) obj)).removeCallback(this.this$0.listingCallback);
    }
}
