package com.android.systemui.qs.pipeline.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class QQSTilesInteractorImpl$createTile$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ QQSTilesInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QQSTilesInteractorImpl$createTile$1(QQSTilesInteractorImpl qQSTilesInteractorImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = qQSTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return QQSTilesInteractorImpl.access$createTile(this.this$0, null, this);
    }
}
