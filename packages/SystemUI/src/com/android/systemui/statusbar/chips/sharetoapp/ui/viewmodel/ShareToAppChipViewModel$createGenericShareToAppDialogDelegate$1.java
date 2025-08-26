package com.android.systemui.statusbar.chips.sharetoapp.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1 extends FunctionReferenceImpl implements Function0 {
    public ShareToAppChipViewModel$createGenericShareToAppDialogDelegate$1(Object obj) {
        super(0, obj, ShareToAppChipViewModel.class, "stopProjectingFromDialog", "stopProjectingFromDialog()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ShareToAppChipViewModel.access$stopProjectingFromDialog((ShareToAppChipViewModel) this.receiver);
        return Unit.INSTANCE;
    }
}
