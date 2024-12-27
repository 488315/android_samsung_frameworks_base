package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Quint;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1 this$0;

    public FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1$emit$1(FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1 fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1, Continuation continuation) {
        super(continuation);
        this.this$0 = fromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$1$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Quint) null, (Continuation) this);
    }
}
