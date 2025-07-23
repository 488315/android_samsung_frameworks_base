package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl$startTileCollection$1;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ RemovedTilesInteractorImpl$startTileCollection$1.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemovedTilesInteractorImpl$startTileCollection$1$1$emit$1(RemovedTilesInteractorImpl$startTileCollection$1.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((UserInfo) null, (Continuation) this);
    }
}
