package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.StateToValue;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;

        /* renamed from: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C03281 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C03281(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector) {
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C03281 c03281;
            if (continuation instanceof C03281) {
                c03281 = (C03281) continuation;
                int i = c03281.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c03281.label = i - Integer.MIN_VALUE;
                } else {
                    c03281 = new C03281(continuation);
                }
            }
            Object obj2 = c03281.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c03281.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                Pair pair = (Pair) obj;
                StateToValue stateToValue = (StateToValue) pair.component1();
                if (((WakefulnessModel) pair.component2()).lastSleepReason != WakeSleepReason.FOLD) {
                    c03281.label = 1;
                    if (this.$$this$flow.emit(stateToValue, c03281) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1(Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$this_transform = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1 goneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1 = new GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1(this.$this_transform, continuation);
        goneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1.L$0 = obj;
        return goneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
