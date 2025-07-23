package com.android.systemui.keyguard.domain.interactor.scenetransition;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ LockscreenSceneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenSceneTransitionInteractor$finishReversedTransitionTo$1(LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = lockscreenSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor = this.this$0;
        int i = LockscreenSceneTransitionInteractor.$r8$clinit;
        return lockscreenSceneTransitionInteractor.finishReversedTransitionTo$1(null, this);
    }
}
