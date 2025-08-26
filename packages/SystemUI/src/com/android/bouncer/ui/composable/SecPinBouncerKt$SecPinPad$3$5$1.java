package com.android.bouncer.ui.composable;

import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class SecPinBouncerKt$SecPinPad$3$5$1 extends FunctionReferenceImpl implements Function1 {
    public SecPinBouncerKt$SecPinPad$3$5$1(Object obj) {
        super(1, obj, PinBouncerViewModel.class, "onPinButtonClicked", "onPinButtonClicked(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((PinBouncerViewModel) this.receiver).onPinButtonClicked(((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
