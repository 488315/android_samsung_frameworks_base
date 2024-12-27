package com.android.systemui.haptics.slider;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class SliderTracker$startTracking$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SliderTracker this$0;

    /* renamed from: com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ SliderTracker this$0;

        public AnonymousClass1(SliderTracker sliderTracker) {
            this.this$0 = sliderTracker;
        }

        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(com.android.systemui.haptics.slider.SliderEvent r5, kotlin.coroutines.Continuation r6) {
            /*
                r4 = this;
                boolean r0 = r6 instanceof com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1$emit$1
                if (r0 == 0) goto L13
                r0 = r6
                com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1$emit$1 r0 = (com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1$emit$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1$emit$1 r0 = new com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1$emit$1
                r0.<init>(r4, r6)
            L18:
                java.lang.Object r6 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L33
                if (r2 != r3) goto L2b
                java.lang.Object r4 = r0.L$0
                com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1 r4 = (com.android.systemui.haptics.slider.SliderTracker$startTracking$1.AnonymousClass1) r4
                kotlin.ResultKt.throwOnFailure(r6)
                goto L43
            L2b:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L33:
                kotlin.ResultKt.throwOnFailure(r6)
                r0.L$0 = r4
                r0.label = r3
                com.android.systemui.haptics.slider.SliderTracker r6 = r4.this$0
                kotlin.Unit r5 = r6.iterateState(r5)
                if (r5 != r1) goto L43
                return r1
            L43:
                com.android.systemui.haptics.slider.SliderTracker r4 = r4.this$0
                com.android.systemui.haptics.slider.SliderState r5 = r4.currentState
                r4.executeOnState(r5)
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.SliderTracker$startTracking$1.AnonymousClass1.emit(com.android.systemui.haptics.slider.SliderEvent, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    public SliderTracker$startTracking$1(SliderTracker sliderTracker, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sliderTracker;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SliderTracker$startTracking$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliderTracker$startTracking$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(((SliderStateProducer) this.this$0.eventProducer)._currentEvent);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
            this.label = 1;
            if (asStateFlow.$$delegate_0.collect(anonymousClass1, this) == coroutineSingletons) {
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
