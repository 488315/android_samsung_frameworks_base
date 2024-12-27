package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1;
import com.android.systemui.util.kotlin.Quint;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

final class FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 extends ContinuationImpl {
    Object L$0;
    boolean Z$0;
    boolean Z$1;
    boolean Z$2;
    boolean Z$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2 this$0;

    public FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1(FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2 anonymousClass2, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Quint) null, (Continuation) this);
    }
}
