package com.android.systemui.statusbar.chips.casttootherdevice.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
