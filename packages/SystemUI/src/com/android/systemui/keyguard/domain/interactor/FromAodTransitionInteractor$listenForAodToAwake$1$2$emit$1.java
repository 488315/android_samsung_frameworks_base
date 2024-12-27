package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.util.kotlin.Sextuple;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    boolean Z$1;
    boolean Z$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ FromAodTransitionInteractor$listenForAodToAwake$1$2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromAodTransitionInteractor$listenForAodToAwake$1$2$emit$1(FromAodTransitionInteractor$listenForAodToAwake$1$2 fromAodTransitionInteractor$listenForAodToAwake$1$2, Continuation continuation) {
        super(continuation);
        this.this$0 = fromAodTransitionInteractor$listenForAodToAwake$1$2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Sextuple) null, (Continuation) this);
    }
}
