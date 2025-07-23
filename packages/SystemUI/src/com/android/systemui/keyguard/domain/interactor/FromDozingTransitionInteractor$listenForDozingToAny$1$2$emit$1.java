package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor$listenForDozingToAny$1;
import kotlin.Triple;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    boolean Z$0;
    boolean Z$1;
    boolean Z$2;
    boolean Z$3;
    boolean Z$4;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDozingTransitionInteractor$listenForDozingToAny$1$2$emit$1(FromDozingTransitionInteractor$listenForDozingToAny$1.AnonymousClass2 anonymousClass2, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Triple) null, (Continuation) this);
    }
}
