package com.android.systemui.haptics.slider.compose.ui;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.DragInteraction$Start;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderDragVelocityProvider;
import com.android.systemui.haptics.slider.SliderEvent;
import com.android.systemui.haptics.slider.SliderEventType;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackProvider;
import com.android.systemui.haptics.slider.SliderStateProducer;
import com.android.systemui.haptics.slider.SliderStateTracker;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class SliderHapticsViewModel extends ExclusiveActivatable {
    public final SliderHapticsViewModel$dragVelocityProvider$1 dragVelocityProvider;
    public final InteractionSource interactionSource;
    public final long maxVelocity;
    public final Orientation orientation;
    public final SliderHapticFeedbackProvider sliderHapticFeedbackProvider;
    public final ClosedFloatingPointRange sliderRange;
    public final SliderStateProducer sliderStateProducer;
    public SliderStateTracker sliderTracker;
    public final SeekableSliderTrackerConfig sliderTrackerConfig;
    public float startingProgress;
    public SliderEventType currentSliderEventType = SliderEventType.NOTHING;
    public final VelocityTracker velocityTracker = new VelocityTracker();

    public interface Factory {
        SliderHapticsViewModel create(InteractionSource interactionSource, ClosedFloatingPointRange closedFloatingPointRange, Orientation orientation, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig);
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SliderEventType.values().length];
            try {
                iArr[SliderEventType.NOTHING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SliderEventType.STARTED_TRACKING_TOUCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SliderEventType.PROGRESS_CHANGE_BY_USER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SliderEventType.STARTED_TRACKING_PROGRAM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SliderEventType.PROGRESS_CHANGE_BY_PROGRAM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Orientation.values().length];
            try {
                iArr2[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* renamed from: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SliderHapticsViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

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

        /* renamed from: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C02022 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SliderHapticsViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02022(SliderHapticsViewModel sliderHapticsViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = sliderHapticsViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02022(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02022) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                                SliderHapticsViewModel sliderHapticsViewModel2 = sliderHapticsViewModel;
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = SliderHapticsViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                SliderHapticsViewModel sliderHapticsViewModel = SliderHapticsViewModel.this;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(sliderHapticsViewModel, null), 6);
                sliderHapticsViewModel.getClass();
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C02022(SliderHapticsViewModel.this, null), 6);
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

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.haptics.slider.SliderDragVelocityProvider, com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$dragVelocityProvider$1] */
    public SliderHapticsViewModel(InteractionSource interactionSource, ClosedFloatingPointRange closedFloatingPointRange, Orientation orientation, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock) {
        this.interactionSource = interactionSource;
        this.sliderRange = closedFloatingPointRange;
        this.orientation = orientation;
        this.sliderTrackerConfig = seekableSliderTrackerConfig;
        float f = sliderHapticFeedbackConfig.maxVelocityToScale;
        this.maxVelocity = VelocityKt.Velocity(f, f);
        ?? r6 = new SliderDragVelocityProvider() { // from class: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$dragVelocityProvider$1

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Orientation.values().length];
                    try {
                        iArr[Orientation.Horizontal.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Orientation.Vertical.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // com.android.systemui.haptics.slider.SliderDragVelocityProvider
            public final float getTrackedVelocity() {
                float fM880getXimpl;
                SliderHapticsViewModel sliderHapticsViewModel = this.this$0;
                int i = WhenMappings.$EnumSwitchMapping$0[sliderHapticsViewModel.orientation.ordinal()];
                if (i == 1) {
                    fM880getXimpl = Velocity.m880getXimpl(sliderHapticsViewModel.velocityTracker.m602calculateVelocityAH228Gc(sliderHapticsViewModel.maxVelocity));
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    fM880getXimpl = Velocity.m881getYimpl(sliderHapticsViewModel.velocityTracker.m602calculateVelocityAH228Gc(sliderHapticsViewModel.maxVelocity));
                }
                return Math.abs(fM880getXimpl);
            }
        };
        this.dragVelocityProvider = r6;
        this.sliderStateProducer = new SliderStateProducer();
        this.sliderHapticFeedbackProvider = new SliderHapticFeedbackProvider(vibratorHelper, mSDLPlayer, r6, sliderHapticFeedbackConfig, systemClock);
    }

    public final void addVelocityDataPoint(float f) {
        long jFloatToRawIntBits;
        float fNormalize = normalize(f);
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = WhenMappings.$EnumSwitchMapping$1[this.orientation.ordinal()];
        if (i == 1) {
            jFloatToRawIntBits = (Float.floatToRawIntBits(fNormalize - this.startingProgress) << 32) | (4294967295L & Float.floatToRawIntBits(0.0f));
            Offset.Companion companion = Offset.Companion;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            float f2 = fNormalize - this.startingProgress;
            jFloatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (4294967295L & Float.floatToRawIntBits(f2));
            Offset.Companion companion2 = Offset.Companion;
        }
        this.velocityTracker.m601addPositionUv8p0NA(jCurrentTimeMillis, jFloatToRawIntBits);
    }

    public final float normalize(float f) {
        ClosedFloatingPointRange closedFloatingPointRange = this.sliderRange;
        float fFloatValue = f - Float.valueOf(((ClosedFloatRange) closedFloatingPointRange)._start).floatValue();
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange;
        return RangesKt___RangesKt.coerceIn(fFloatValue / (closedFloatRange._endInclusive - closedFloatRange._start), 0.0f, 1.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    public final void onValueChange(float f) {
        float fNormalize = normalize(f);
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentSliderEventType.ordinal()];
        SliderStateProducer sliderStateProducer = this.sliderStateProducer;
        if (i == 1) {
            this.currentSliderEventType = SliderEventType.STARTED_TRACKING_PROGRAM;
            this.startingProgress = fNormalize;
            sliderStateProducer.getClass();
            sliderStateProducer._currentEvent.updateState(null, new SliderEvent(SliderEventType.NOTHING, fNormalize));
            sliderStateProducer.onStartTracking(false);
            return;
        }
        if (i == 2) {
            this.startingProgress = fNormalize;
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_USER;
            sliderStateProducer.onProgressChanged(fNormalize, true);
            return;
        }
        if (i == 3) {
            addVelocityDataPoint(f);
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_USER;
            sliderStateProducer.onProgressChanged(fNormalize, true);
        } else if (i == 4) {
            this.startingProgress = fNormalize;
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_PROGRAM;
            sliderStateProducer.onProgressChanged(fNormalize, false);
        } else {
            if (i != 5) {
                return;
            }
            addVelocityDataPoint(f);
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_PROGRAM;
            sliderStateProducer.onProgressChanged(fNormalize, false);
        }
    }

    public final void onValueChangeEnded() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentSliderEventType.ordinal()];
        SliderStateProducer sliderStateProducer = this.sliderStateProducer;
        if (i == 2 || i == 3) {
            sliderStateProducer.onStopTracking(true);
        } else if (i == 4 || i == 5) {
            sliderStateProducer.onStopTracking(false);
        }
        this.currentSliderEventType = SliderEventType.NOTHING;
        this.velocityTracker.resetTracking();
    }
}
