package com.android.systemui.qs.panels.data.repository;

import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LargeTileSpanRepository$span$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LargeTileSpanRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LargeTileSpanRepository$span$1(LargeTileSpanRepository largeTileSpanRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = largeTileSpanRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LargeTileSpanRepository$span$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LargeTileSpanRepository$span$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Integer(this.this$0.resources.getConfiguration().fontScale >= 2.0f ? this.this$0.resources.getInteger(R.integer.quick_settings_infinite_grid_tile_max_width) : 2);
    }
}
