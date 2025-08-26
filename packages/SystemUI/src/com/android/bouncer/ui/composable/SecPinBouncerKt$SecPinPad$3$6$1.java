package com.android.bouncer.ui.composable;

import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class SecPinBouncerKt$SecPinPad$3$6$1 extends FunctionReferenceImpl implements Function0 {
    public SecPinBouncerKt$SecPinPad$3$6$1(Object obj) {
        super(0, obj, PinBouncerViewModel.class, "onAuthenticateButtonClicked", "onAuthenticateButtonClicked()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((PinBouncerViewModel) this.receiver).onAuthenticateButtonClicked();
        return Unit.INSTANCE;
    }
}
