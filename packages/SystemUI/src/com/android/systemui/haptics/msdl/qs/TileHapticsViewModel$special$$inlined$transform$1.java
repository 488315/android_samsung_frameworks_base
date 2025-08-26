package com.android.systemui.haptics.msdl.qs;

import com.android.systemui.haptics.msdl.qs.TileHapticsViewModel;
import com.android.systemui.util.kotlin.WithPrev;
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
public final class TileHapticsViewModel$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileHapticsViewModel this$0;

    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ TileHapticsViewModel this$0;

        /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02001 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C02001(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector, TileHapticsViewModel tileHapticsViewModel) {
            this.this$0 = tileHapticsViewModel;
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object emit(Object obj, Continuation continuation) {
            C02001 c02001;
            if (continuation instanceof C02001) {
                c02001 = (C02001) continuation;
                int i = c02001.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    c02001.label = i - Integer.MIN_VALUE;
                } else {
                    c02001 = new C02001(continuation);
                }
            }
            Object obj2 = c02001.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = c02001.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj2);
                WithPrev withPrev = (WithPrev) obj;
                int iIntValue = ((Number) withPrev.component1()).intValue();
                int iIntValue2 = ((Number) withPrev.component2()).intValue();
                TileHapticsViewModel tileHapticsViewModel = this.this$0;
                TileHapticsViewModel.TileHapticsState tileHapticsState = (tileHapticsViewModel.tileAnimationState.getValue() == TileHapticsViewModel.TileAnimationState.IDLE && tileHapticsViewModel.tileInteractionState.getValue() == TileHapticsViewModel.TileInteractionState.CLICKED) ? (iIntValue == 1 && iIntValue2 == 2) ? TileHapticsViewModel.TileHapticsState.TOGGLE_ON : (iIntValue == 2 && iIntValue2 == 1) ? TileHapticsViewModel.TileHapticsState.TOGGLE_OFF : TileHapticsViewModel.TileHapticsState.NO_HAPTICS : TileHapticsViewModel.TileHapticsState.NO_HAPTICS;
                c02001.label = 1;
                if (this.$$this$flow.emit(tileHapticsState, c02001) == coroutineSingletons) {
                    return coroutineSingletons;
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
    public TileHapticsViewModel$special$$inlined$transform$1(Flow flow, Continuation continuation, TileHapticsViewModel tileHapticsViewModel) {
        super(2, continuation);
        this.$this_transform = flow;
        this.this$0 = tileHapticsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileHapticsViewModel$special$$inlined$transform$1 tileHapticsViewModel$special$$inlined$transform$1 = new TileHapticsViewModel$special$$inlined$transform$1(this.$this_transform, continuation, this.this$0);
        tileHapticsViewModel$special$$inlined$transform$1.L$0 = obj;
        return tileHapticsViewModel$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileHapticsViewModel$special$$inlined$transform$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector, this.this$0);
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
