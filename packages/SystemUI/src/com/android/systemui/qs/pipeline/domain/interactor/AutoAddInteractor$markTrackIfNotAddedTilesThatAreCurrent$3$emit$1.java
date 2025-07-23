package com.android.systemui.qs.pipeline.domain.interactor;

import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3$emit$1(AutoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3 autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3, Continuation continuation) {
        super(continuation);
        this.this$0 = autoAddInteractor$markTrackIfNotAddedTilesThatAreCurrent$3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((List) null, (Continuation) this);
    }
}
