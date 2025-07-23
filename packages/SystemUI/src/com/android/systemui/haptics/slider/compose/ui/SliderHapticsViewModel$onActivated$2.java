package com.android.systemui.haptics.slider.compose.ui;

import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.Interaction;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.haptics.slider.SliderEventType;
import com.android.systemui.haptics.slider.SliderStateTracker;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SliderHapticsViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SliderHapticsViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SliderHapticsViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SliderHapticsViewModel sliderHapticsViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = sliderHapticsViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    SliderHapticsViewModel sliderHapticsViewModel = this.this$0;
                    sliderHapticsViewModel.sliderTracker = new SliderStateTracker(sliderHapticsViewModel.sliderHapticFeedbackProvider, sliderHapticsViewModel.sliderStateProducer, coroutineScope, sliderHapticsViewModel.sliderTrackerConfig);
                    SliderStateTracker sliderStateTracker = this.this$0.sliderTracker;
                    if (sliderStateTracker != null) {
                        sliderStateTracker.startTracking();
                    }
                    this.label = 1;
                    if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable th) {
                SliderStateTracker sliderStateTracker2 = this.this$0.sliderTracker;
                if (sliderStateTracker2 != null) {
                    StandaloneCoroutine standaloneCoroutine = sliderStateTracker2.job;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(ExceptionsKt.CancellationException("Stopped tracking slider state", null));
                    }
                    sliderStateTracker2.job = null;
                    sliderStateTracker2.resetState();
                }
                SliderHapticsViewModel sliderHapticsViewModel2 = this.this$0;
                sliderHapticsViewModel2.sliderTracker = null;
                sliderHapticsViewModel2.velocityTracker.resetTracking();
                throw th;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SliderHapticsViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(SliderHapticsViewModel sliderHapticsViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = sliderHapticsViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SharedFlowImpl interactions = this.this$0.interactionSource.getInteractions();
                final SliderHapticsViewModel sliderHapticsViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel.onActivated.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Interaction) obj2) instanceof DragInteraction$Start) {
                            SliderEventType sliderEventType = SliderEventType.STARTED_TRACKING_TOUCH;
                            SliderHapticsViewModel sliderHapticsViewModel2 = SliderHapticsViewModel.this;
                            sliderHapticsViewModel2.currentSliderEventType = sliderEventType;
                            sliderHapticsViewModel2.sliderStateProducer.onStartTracking(true);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                interactions.getClass();
                if (SharedFlowImpl.collect$suspendImpl(interactions, flowCollector, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderHapticsViewModel$onActivated$2(SliderHapticsViewModel sliderHapticsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sliderHapticsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliderHapticsViewModel$onActivated$2 sliderHapticsViewModel$onActivated$2 = new SliderHapticsViewModel$onActivated$2(this.this$0, continuation);
        sliderHapticsViewModel$onActivated$2.L$0 = obj;
        return sliderHapticsViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliderHapticsViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            SliderHapticsViewModel sliderHapticsViewModel = this.this$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(sliderHapticsViewModel, null), 6);
            sliderHapticsViewModel.getClass();
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 6);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
