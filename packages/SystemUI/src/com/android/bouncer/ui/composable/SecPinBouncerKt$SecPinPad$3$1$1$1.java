package com.android.bouncer.ui.composable;

import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class SecPinBouncerKt$SecPinPad$3$1$1$1 extends FunctionReferenceImpl implements Function1 {
    public SecPinBouncerKt$SecPinPad$3$1$1$1(Object obj) {
        super(1, obj, PinBouncerViewModel.class, "onPinButtonClicked", "onPinButtonClicked(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((PinBouncerViewModel) this.receiver).onPinButtonClicked(((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
