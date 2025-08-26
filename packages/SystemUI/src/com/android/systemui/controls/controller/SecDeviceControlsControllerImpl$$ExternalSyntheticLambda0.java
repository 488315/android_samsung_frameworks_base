package com.android.systemui.controls.controller;

import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecDeviceControlsControllerImpl f$0;

    public /* synthetic */ SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0(SecDeviceControlsControllerImpl secDeviceControlsControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = secDeviceControlsControllerImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ControlsListingController controlsListingController = (ControlsListingController) obj;
        switch (this.$r8$classId) {
            case 0:
                SecDeviceControlsControllerImpl$listingCallback$1 secDeviceControlsControllerImpl$listingCallback$1 = this.f$0.listingCallback;
                ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) controlsListingController;
                controlsListingControllerImpl.getClass();
                controlsListingControllerImpl.addCallback((ControlsListingController.ControlsListingCallback) secDeviceControlsControllerImpl$listingCallback$1);
                break;
            case 1:
                ((ControlsListingControllerImpl) controlsListingController).removeCallback(this.f$0.listingCallback);
                break;
            default:
                ((ControlsListingControllerImpl) controlsListingController).removeCallback(this.f$0.listingCallback);
                break;
        }
        return Unit.INSTANCE;
    }
}
