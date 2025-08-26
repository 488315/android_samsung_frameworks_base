package com.android.systemui.keyguard.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class KeyguardDismissActionInteractor$runDismissAction$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyguardDismissActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardDismissActionInteractor$runDismissAction$1(KeyguardDismissActionInteractor keyguardDismissActionInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = keyguardDismissActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return KeyguardDismissActionInteractor.access$runDismissAction(this.this$0, this);
    }
}
