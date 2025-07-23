package com.android.systemui.haptics.msdl.qs;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileHapticsViewModel$special$$inlined$transform$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileHapticsViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;
        public final /* synthetic */ TileHapticsViewModel this$0;

        /* renamed from: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C01061 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C01061(Continuation continuation) {
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

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
            /*
                r6 = this;
                boolean r0 = r8 instanceof com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1.AnonymousClass1.C01061
                if (r0 == 0) goto L13
                r0 = r8
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1$1 r0 = (com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1.AnonymousClass1.C01061) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1$1 r0 = new com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1$1$1
                r0.<init>(r8)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r8)
                goto L7d
            L27:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L2f:
                kotlin.ResultKt.throwOnFailure(r8)
                com.android.systemui.util.kotlin.WithPrev r7 = (com.android.systemui.util.kotlin.WithPrev) r7
                java.lang.Object r8 = r7.component1()
                java.lang.Number r8 = (java.lang.Number) r8
                int r8 = r8.intValue()
                java.lang.Object r7 = r7.component2()
                java.lang.Number r7 = (java.lang.Number) r7
                int r7 = r7.intValue()
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel r2 = r6.this$0
                kotlinx.coroutines.flow.StateFlowImpl r4 = r2.tileAnimationState
                java.lang.Object r4 = r4.getValue()
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileAnimationState r5 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileAnimationState.IDLE
                if (r4 != r5) goto L70
                kotlinx.coroutines.flow.StateFlowImpl r2 = r2.tileInteractionState
                java.lang.Object r2 = r2.getValue()
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileInteractionState r4 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileInteractionState.CLICKED
                if (r2 != r4) goto L70
                r2 = 2
                if (r8 != r3) goto L66
                if (r7 != r2) goto L66
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileHapticsState r7 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileHapticsState.TOGGLE_ON
                goto L72
            L66:
                if (r8 != r2) goto L6d
                if (r7 != r3) goto L6d
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileHapticsState r7 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileHapticsState.TOGGLE_OFF
                goto L72
            L6d:
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileHapticsState r7 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileHapticsState.NO_HAPTICS
                goto L72
            L70:
                com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$TileHapticsState r7 = com.android.systemui.haptics.msdl.qs.TileHapticsViewModel.TileHapticsState.NO_HAPTICS
            L72:
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r6 = r6.$$this$flow
                java.lang.Object r6 = r6.emit(r7, r0)
                if (r6 != r1) goto L7d
                return r1
            L7d:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.msdl.qs.TileHapticsViewModel$special$$inlined$transform$1.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
