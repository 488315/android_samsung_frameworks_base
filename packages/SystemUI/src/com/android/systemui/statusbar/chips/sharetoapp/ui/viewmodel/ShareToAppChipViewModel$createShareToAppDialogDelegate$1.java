package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class ShareToAppChipViewModel$createShareToAppDialogDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public ShareToAppChipViewModel$createShareToAppDialogDelegate$1(Object obj) {
        super(0, obj, ShareToAppChipViewModel.class, "stopProjecting", "stopProjecting()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((ShareToAppChipViewModel) this.receiver).mediaProjectionChipInteractor.stopProjecting();
        return Unit.INSTANCE;
    }
}
