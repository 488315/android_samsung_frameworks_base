package com.android.systemui.unfold;

import com.android.app.tracing.coroutines.TrackTracer;
import com.android.systemui.unfold.data.repository.FoldStateRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UnfoldTraceLogger$start$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UnfoldTraceLogger this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnfoldTraceLogger$start$2(UnfoldTraceLogger unfoldTraceLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = unfoldTraceLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UnfoldTraceLogger$start$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UnfoldTraceLogger$start$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow hingeAngle = ((FoldStateRepositoryImpl) this.this$0.foldStateRepository).getHingeAngle();
            AnonymousClass1 anonymousClass1 = new FlowCollector() { // from class: com.android.systemui.unfold.UnfoldTraceLogger$start$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    float floatValue = ((Number) obj2).floatValue();
                    TrackTracer.Companion.getClass();
                    TrackTracer.Companion.instantForGroup((int) floatValue, "unfold", "hingeAngle");
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (hingeAngle.collect(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
