package com.android.systemui.haptics.slider;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public abstract class SliderTracker {
    public SliderState currentState;
    public final SliderEventProducer eventProducer;
    public StandaloneCoroutine job;
    public final CoroutineScope scope;
    public final SliderStateListener sliderListener;

    /* renamed from: com.android.systemui.haptics.slider.SliderTracker$startTracking$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.haptics.slider.SliderTracker$startTracking$1$1, reason: invalid class name and collision with other inner class name */
        public final class C02011 implements FlowCollector {
            public final /* synthetic */ SliderTracker this$0;

            public C02011(SliderTracker sliderTracker) {
                this.this$0 = sliderTracker;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(SliderEvent sliderEvent, Continuation continuation) {
                SliderTracker$startTracking$1$1$emit$1 sliderTracker$startTracking$1$1$emit$1;
                if (continuation instanceof SliderTracker$startTracking$1$1$emit$1) {
                    sliderTracker$startTracking$1$1$emit$1 = (SliderTracker$startTracking$1$1$emit$1) continuation;
                    int i = sliderTracker$startTracking$1$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        sliderTracker$startTracking$1$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        sliderTracker$startTracking$1$1$emit$1 = new SliderTracker$startTracking$1$1$emit$1(this, continuation);
                    }
                }
                Object obj = sliderTracker$startTracking$1$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = sliderTracker$startTracking$1$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    sliderTracker$startTracking$1$1$emit$1.L$0 = this;
                    sliderTracker$startTracking$1$1$emit$1.label = 1;
                    if (this.this$0.iterateState(sliderEvent) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    this = (C02011) sliderTracker$startTracking$1$1$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                SliderTracker sliderTracker = this.this$0;
                sliderTracker.executeOnState(sliderTracker.currentState);
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SliderTracker.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(((SliderStateProducer) SliderTracker.this.eventProducer)._currentEvent);
                C02011 c02011 = new C02011(SliderTracker.this);
                this.label = 1;
                if (readonlyStateFlowAsStateFlow.$$delegate_0.collect(c02011, this) == coroutineSingletons) {
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

    public /* synthetic */ SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer, DefaultConstructorMarker defaultConstructorMarker) {
        this(coroutineScope, sliderStateListener, sliderEventProducer);
    }

    public abstract void executeOnState(SliderState sliderState);

    public abstract Unit iterateState(SliderEvent sliderEvent);

    public final void startTracking() {
        this.job = CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }

    private SliderTracker(CoroutineScope coroutineScope, SliderStateListener sliderStateListener, SliderEventProducer sliderEventProducer) {
        this.scope = coroutineScope;
        this.sliderListener = sliderStateListener;
        this.eventProducer = sliderEventProducer;
        this.currentState = SliderState.IDLE;
    }
}
