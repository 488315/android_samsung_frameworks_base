package com.android.systemui.bouncer.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
final class BouncerMessageViewModel$defaultBouncerMessageInitializer$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$defaultBouncerMessageInitializer$1(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return BouncerMessageViewModel.access$defaultBouncerMessageInitializer(this.this$0, this);
    }
}
