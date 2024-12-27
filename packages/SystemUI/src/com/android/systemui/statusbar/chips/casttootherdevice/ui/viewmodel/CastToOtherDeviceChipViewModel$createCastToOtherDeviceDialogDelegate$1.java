package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class CastToOtherDeviceChipViewModel$createCastToOtherDeviceDialogDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public CastToOtherDeviceChipViewModel$createCastToOtherDeviceDialogDelegate$1(Object obj) {
        super(0, obj, CastToOtherDeviceChipViewModel.class, "stopProjecting", "stopProjecting()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((CastToOtherDeviceChipViewModel) this.receiver).mediaProjectionChipInteractor.stopProjecting();
        return Unit.INSTANCE;
    }
}
