package com.android.compose.animation.scene;

import com.android.compose.animation.scene.ContentKey;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PredictiveBackHandlerKt$startTransition$1<T extends ContentKey> extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    public PredictiveBackHandlerKt$startTransition$1(Continuation continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return PredictiveBackHandlerKt.access$startTransition(null, null, null, this);
    }
}
