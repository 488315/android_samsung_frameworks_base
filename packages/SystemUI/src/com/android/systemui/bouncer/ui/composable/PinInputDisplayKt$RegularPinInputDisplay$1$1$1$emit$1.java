package com.android.systemui.bouncer.ui.composable;

import com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$RegularPinInputDisplay$1$1;
import com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PinInputDisplayKt$RegularPinInputDisplay$1$1.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinInputDisplayKt$RegularPinInputDisplay$1$1$1$emit$1(PinInputDisplayKt$RegularPinInputDisplay$1$1.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((PinInputViewModel) null, (Continuation) this);
    }
}
