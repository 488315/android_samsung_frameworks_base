package com.android.systemui.statusbar.policy.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ZenModeInteractor$buildActiveZenModes$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ZenModeInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ZenModeInteractor$buildActiveZenModes$1(ZenModeInteractor zenModeInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = zenModeInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        ZenModeInteractor zenModeInteractor = this.this$0;
        int i = ZenModeInteractor.$r8$clinit;
        return zenModeInteractor.buildActiveZenModes(null, this);
    }
}
