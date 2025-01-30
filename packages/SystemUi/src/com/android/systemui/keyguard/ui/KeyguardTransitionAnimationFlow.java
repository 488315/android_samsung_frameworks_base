package com.android.systemui.keyguard.ui;

import android.view.animation.Interpolator;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.time.Duration;
import kotlin.time.DurationJvmKt;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTransitionAnimationFlow {
    public final long transitionDuration;
    public final Flow transitionFlow;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TransitionState.values().length];
            try {
                iArr[TransitionState.STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionState.RUNNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TransitionState.CANCELED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TransitionState.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ KeyguardTransitionAnimationFlow(long j, Flow flow, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, flow);
    }

    public static final Float access$createFlow_53AowQI$stepToValue(float f, float f2, Ref$BooleanRef ref$BooleanRef, Function0 function0, Function1 function1, Interpolator interpolator, TransitionStep transitionStep) {
        Float valueOf;
        float f3 = (transitionStep.value - f) * f2;
        int i = WhenMappings.$EnumSwitchMapping$0[transitionStep.transitionState.ordinal()];
        if (i != 1) {
            if (i == 2 && !ref$BooleanRef.element) {
                if (f3 >= 1.0f) {
                    ref$BooleanRef.element = true;
                    valueOf = Float.valueOf(1.0f);
                } else if (f3 >= 0.0f) {
                    valueOf = Float.valueOf(f3);
                }
            }
            valueOf = null;
        } else {
            ref$BooleanRef.element = false;
            if (function0 != null) {
                function0.invoke();
            }
            valueOf = Float.valueOf(Math.max(0.0f, Math.min(1.0f, f3)));
        }
        if (valueOf != null) {
            return Float.valueOf(((Number) function1.invoke(Float.valueOf(interpolator.getInterpolation(valueOf.floatValue())))).floatValue());
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x016a  */
    /* renamed from: createFlow-53AowQI$default, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 m1579createFlow53AowQI$default(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, long j, final Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, int i) {
        long j3;
        int i2;
        Function0 function04;
        long m2860addValuesMixedRangesUwyO8pc;
        long j4;
        if ((i & 4) != 0) {
            Duration.Companion companion = Duration.Companion;
            j3 = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
        } else {
            j3 = j2;
        }
        final Function0 function05 = (i & 8) != 0 ? null : function0;
        Function0 function06 = (i & 16) != 0 ? null : function02;
        final Function0 function07 = (i & 32) != 0 ? null : function03;
        final Interpolator interpolator2 = (i & 64) != 0 ? Interpolators.LINEAR : interpolator;
        keyguardTransitionAnimationFlow.getClass();
        Duration.Companion companion2 = Duration.Companion;
        if (!(j > 0)) {
            throw new IllegalArgumentException("duration must be a positive number: ".concat(Duration.m2867toStringimpl(j)));
        }
        if (Duration.m2864isInfiniteimpl(j3)) {
            if (!(!Duration.m2864isInfiniteimpl(j)) && (j ^ j3) < 0) {
                throw new IllegalArgumentException("Summing infinite durations of different signs yields an undefined result.");
            }
            m2860addValuesMixedRangesUwyO8pc = j3;
        } else {
            if (!Duration.m2864isInfiniteimpl(j)) {
                int i3 = ((int) j3) & 1;
                if (i3 == (((int) j) & 1)) {
                    long j5 = (j3 >> 1) + (j >> 1);
                    if (i3 == 0) {
                        function04 = function06;
                        if (new LongRange(-4611686018426999999L, 4611686018426999999L).contains(j5)) {
                            m2860addValuesMixedRangesUwyO8pc = j5 << 1;
                            int i4 = DurationJvmKt.$r8$clinit;
                        } else {
                            m2860addValuesMixedRangesUwyO8pc = DurationKt.durationOfMillis(j5 / 1000000);
                        }
                        i2 = 1;
                    } else {
                        function04 = function06;
                        if (new LongRange(-4611686018426L, 4611686018426L).contains(j5)) {
                            i2 = 1;
                            m2860addValuesMixedRangesUwyO8pc = (j5 * 1000000) << 1;
                            int i5 = DurationJvmKt.$r8$clinit;
                        } else {
                            i2 = 1;
                            m2860addValuesMixedRangesUwyO8pc = DurationKt.durationOfMillis(RangesKt___RangesKt.coerceIn(j5));
                        }
                    }
                } else {
                    i2 = 1;
                    function04 = function06;
                    m2860addValuesMixedRangesUwyO8pc = i3 == 1 ? Duration.m2860addValuesMixedRangesUwyO8pc(j3 >> 1, j >> 1) : Duration.m2860addValuesMixedRangesUwyO8pc(j >> 1, j3 >> 1);
                }
                j4 = keyguardTransitionAnimationFlow.transitionDuration;
                if (Duration.m2862compareToLRDsOJo(m2860addValuesMixedRangesUwyO8pc, j4) <= 0) {
                    throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0866xb1ce8deb.m87m("startTime(", Duration.m2867toStringimpl(j3), ") + duration(", Duration.m2867toStringimpl(j), ") must be <= transitionDuration("), Duration.m2867toStringimpl(j4), ")"));
                }
                DurationUnit durationUnit = ((((int) j3) & i2) == 0 ? i2 : 0) != 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
                int i6 = ((int) j4) & i2;
                DurationUnit durationUnit2 = i6 == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
                if (durationUnit.compareTo(durationUnit2) < 0) {
                    durationUnit = durationUnit2;
                }
                final float m2865toDoubleimpl = (float) (Duration.m2865toDoubleimpl(j3, durationUnit) / Duration.m2865toDoubleimpl(j4, durationUnit));
                DurationUnit durationUnit3 = i6 == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
                DurationUnit durationUnit4 = (((int) j) & 1) == 0 ? DurationUnit.NANOSECONDS : DurationUnit.MILLISECONDS;
                if (durationUnit3.compareTo(durationUnit4) < 0) {
                    durationUnit3 = durationUnit4;
                }
                final float m2865toDoubleimpl2 = (float) (Duration.m2865toDoubleimpl(j4, durationUnit3) / Duration.m2865toDoubleimpl(j, durationUnit3));
                final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                ref$BooleanRef.element = true;
                final Flow flow = keyguardTransitionAnimationFlow.transitionFlow;
                final Function0 function08 = function04;
                return new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$createFlow-53AowQI$$inlined$map$1

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$createFlow-53AowQI$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ float $chunks$inlined;
                        public final /* synthetic */ Interpolator $interpolator$inlined;
                        public final /* synthetic */ Ref$BooleanRef $isComplete$inlined;
                        public final /* synthetic */ Function0 $onCancel$inlined;
                        public final /* synthetic */ Function0 $onFinish$inlined;
                        public final /* synthetic */ Function0 $onStart$inlined;
                        public final /* synthetic */ Function1 $onStep$inlined;
                        public final /* synthetic */ float $start$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        @DebugMetadata(m276c = "com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$createFlow-53AowQI$$inlined$map$1$2", m277f = "KeyguardTransitionAnimationFlow.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                        /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$createFlow-53AowQI$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, Function0 function0, Function0 function02, float f, float f2, Ref$BooleanRef ref$BooleanRef, Function0 function03, Function1 function1, Interpolator interpolator) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$onCancel$inlined = function0;
                            this.$onFinish$inlined = function02;
                            this.$start$inlined = f;
                            this.$chunks$inlined = f2;
                            this.$isComplete$inlined = ref$BooleanRef;
                            this.$onStart$inlined = function03;
                            this.$onStep$inlined = function1;
                            this.$interpolator$inlined = interpolator;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            int i;
                            Float access$createFlow_53AowQI$stepToValue;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i2 = anonymousClass1.label;
                                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                    anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    i = anonymousClass1.label;
                                    if (i != 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        TransitionStep transitionStep = (TransitionStep) obj;
                                        int i3 = KeyguardTransitionAnimationFlow.WhenMappings.$EnumSwitchMapping$0[transitionStep.transitionState.ordinal()];
                                        if (i3 == 1) {
                                            access$createFlow_53AowQI$stepToValue = KeyguardTransitionAnimationFlow.access$createFlow_53AowQI$stepToValue(this.$start$inlined, this.$chunks$inlined, this.$isComplete$inlined, this.$onStart$inlined, this.$onStep$inlined, this.$interpolator$inlined, transitionStep);
                                        } else if (i3 != 2) {
                                            access$createFlow_53AowQI$stepToValue = null;
                                            if (i3 == 3) {
                                                Function0 function0 = this.$onCancel$inlined;
                                                if (function0 != null) {
                                                    access$createFlow_53AowQI$stepToValue = (Float) function0.invoke();
                                                }
                                            } else {
                                                if (i3 != 4) {
                                                    throw new NoWhenBranchMatchedException();
                                                }
                                                Function0 function02 = this.$onFinish$inlined;
                                                if (function02 != null) {
                                                    access$createFlow_53AowQI$stepToValue = (Float) function02.invoke();
                                                }
                                            }
                                        } else {
                                            access$createFlow_53AowQI$stepToValue = KeyguardTransitionAnimationFlow.access$createFlow_53AowQI$stepToValue(this.$start$inlined, this.$chunks$inlined, this.$isComplete$inlined, this.$onStart$inlined, this.$onStep$inlined, this.$interpolator$inlined, transitionStep);
                                        }
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(access$createFlow_53AowQI$stepToValue, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }
                            anonymousClass1 = new AnonymousClass1(continuation);
                            Object obj22 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function08, function07, m2865toDoubleimpl, m2865toDoubleimpl2, ref$BooleanRef, function05, function1, interpolator2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                });
            }
            m2860addValuesMixedRangesUwyO8pc = j;
        }
        i2 = 1;
        function04 = function06;
        j4 = keyguardTransitionAnimationFlow.transitionDuration;
        if (Duration.m2862compareToLRDsOJo(m2860addValuesMixedRangesUwyO8pc, j4) <= 0) {
        }
    }

    private KeyguardTransitionAnimationFlow(long j, Flow flow) {
        this.transitionDuration = j;
        this.transitionFlow = flow;
    }
}
