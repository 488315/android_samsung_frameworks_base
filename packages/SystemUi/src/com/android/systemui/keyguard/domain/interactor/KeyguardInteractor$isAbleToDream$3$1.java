package com.android.systemui.keyguard.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isAbleToDream$3$1", m277f = "KeyguardInteractor.kt", m278l = {120, 121}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardInteractor$isAbleToDream$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isAbleToDream;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$isAbleToDream$3$1(boolean z, Continuation<? super KeyguardInteractor$isAbleToDream$3$1> continuation) {
        super(2, continuation);
        this.$isAbleToDream = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardInteractor$isAbleToDream$3$1 keyguardInteractor$isAbleToDream$3$1 = new KeyguardInteractor$isAbleToDream$3$1(this.$isAbleToDream, continuation);
        keyguardInteractor$isAbleToDream$3$1.L$0 = obj;
        return keyguardInteractor$isAbleToDream$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardInteractor$isAbleToDream$3$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            this.L$0 = flowCollector;
            this.label = 1;
            if (DelayKt.delay(50L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Boolean valueOf = Boolean.valueOf(this.$isAbleToDream);
        this.L$0 = null;
        this.label = 2;
        if (flowCollector.emit(valueOf, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
