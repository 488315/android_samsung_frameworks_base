package com.android.systemui.controls.controller;

import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
