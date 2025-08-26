package com.android.systemui.shade;

import com.android.systemui.common.ui.view.ChoreographerUtils;
import com.android.systemui.common.ui.view.ChoreographerUtilsImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShadeDisplayChangeLatencyTracker this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1(ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeDisplayChangeLatencyTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeDisplayChangeLatencyTracker$waitUntilNextDoFrameDone$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ShadeDisplayChangeLatencyTracker shadeDisplayChangeLatencyTracker = this.this$0;
            ChoreographerUtils choreographerUtils = shadeDisplayChangeLatencyTracker.choreographerUtils;
            this.label = 1;
            if (((ChoreographerUtilsImpl) choreographerUtils).waitUntilNextDoFrameDone(shadeDisplayChangeLatencyTracker.shadeRootView, this) == coroutineSingletons) {
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
