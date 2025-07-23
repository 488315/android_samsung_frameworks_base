package com.android.systemui.keyguard.ui;

import android.view.animation.Interpolator;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.keyguard.logging.KeyguardTransitionAnimationLogger;
import com.android.keyguard.logging.KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardTransitionAnimationFlow {
    public final KeyguardTransitionAnimationLogger logger;
    public final KeyguardTransitionInteractor transitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FlowBuilder {
        public final Edge edge;
        public final long transitionDuration;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        public /* synthetic */ FlowBuilder(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, long j, Edge edge, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, edge);
        }

        public static final Float access$sharedFlowWithState_74qcysc$stepToValue(float f, float f2, Function0 function0, Function1 function1, Interpolator interpolator, TransitionStep transitionStep) {
            Float valueOf;
            float f3 = (transitionStep.value - f) * f2;
            int i = WhenMappings.$EnumSwitchMapping$0[transitionStep.transitionState.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    if (f3 >= 1.0f) {
                        valueOf = Float.valueOf(1.0f);
                    } else if (f3 >= 0.0f) {
                        valueOf = Float.valueOf(f3);
                    }
                }
                valueOf = null;
            } else {
                if (function0 != null) {
                    function0.invoke();
                }
                valueOf = Float.valueOf(Math.max(0.0f, Math.min(1.0f, f3)));
            }
            if (valueOf != null) {
                return (Float) function1.mo779invoke(Float.valueOf(interpolator.getInterpolation(valueOf.floatValue())));
            }
            return null;
        }

        /* renamed from: sharedFlow-74qcysc$default, reason: not valid java name */
        public static KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long j3;
            if ((i & 1) != 0) {
                j = flowBuilder.transitionDuration;
            }
            long j4 = j;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                j3 = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                j3 = j2;
            }
            return new KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1(flowBuilder.m2601sharedFlowWithState74qcysc(j4, function1, j3, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str));
        }

        /* renamed from: sharedFlowWithState-74qcysc$default, reason: not valid java name */
        public static /* synthetic */ Flow m2600sharedFlowWithState74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long j3;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                j3 = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                j3 = j2;
            }
            return flowBuilder.m2601sharedFlowWithState74qcysc(j, function1, j3, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str);
        }

        public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo(final float f) {
            Duration.Companion companion = Duration.Companion;
            return m2599sharedFlow74qcysc$default(this, DurationKt.toDuration(1, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ((Float) obj).floatValue();
                    return Float.valueOf(f);
                }
            }, 0L, null, new KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda1(f), new KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda1(f), null, null, 204);
        }

        public final FlowBuilder setupWithoutSceneContainer(Edge.StateToState stateToState) {
            return KeyguardTransitionAnimationFlow.this.m2598setupVtjQ1oo(this.transitionDuration, stateToState);
        }

        /* renamed from: sharedFlowWithState-74qcysc, reason: not valid java name */
        public final Flow m2601sharedFlowWithState74qcysc(long j, final Function1 function1, long j2, final Function0 function0, final Function0 function02, final Function0 function03, final Interpolator interpolator, final String str) {
            Duration.Companion companion = Duration.Companion;
            if (j <= 0) {
                throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("duration must be a positive number: ", Duration.m3446toStringimpl(j)));
            }
            long m3441plusLRDsOJo = Duration.m3441plusLRDsOJo(j2, j);
            long j3 = this.transitionDuration;
            if (Duration.m3435compareToLRDsOJo(m3441plusLRDsOJo, j3) > 0) {
                String m3446toStringimpl = Duration.m3446toStringimpl(j2);
                String m3446toStringimpl2 = Duration.m3446toStringimpl(j);
                throw new IllegalArgumentException(TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("startTime(", m3446toStringimpl, ") + duration(", m3446toStringimpl2, ") must be <= transitionDuration("), Duration.m3446toStringimpl(j3), ")"));
            }
            final float m3436divLRDsOJo = (float) Duration.m3436divLRDsOJo(j2, j3);
            final float m3436divLRDsOJo2 = (float) Duration.m3436divLRDsOJo(j3, j);
            KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = KeyguardTransitionAnimationFlow.this;
            KeyguardTransitionAnimationLogger keyguardTransitionAnimationLogger = keyguardTransitionAnimationFlow.logger;
            keyguardTransitionAnimationLogger.getClass();
            if (str != null) {
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 = new KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = keyguardTransitionAnimationLogger.buffer;
                LogMessage obtain = logBuffer.obtain("KeyguardTransitionAnimationLog", logLevel, keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = String.valueOf(m3436divLRDsOJo);
                logBuffer.commit(obtain);
            }
            final Flow transition = keyguardTransitionAnimationFlow.transitionInteractor.transition(this.edge);
            final KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow2 = KeyguardTransitionAnimationFlow.this;
            return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$mapNotNull$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$mapNotNull$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ float $chunks$inlined;
                    public final /* synthetic */ Interpolator $interpolator$inlined;
                    public final /* synthetic */ String $name$inlined;
                    public final /* synthetic */ Function0 $onCancel$inlined;
                    public final /* synthetic */ Function0 $onFinish$inlined;
                    public final /* synthetic */ Function0 $onStart$inlined;
                    public final /* synthetic */ Function1 $onStep$inlined;
                    public final /* synthetic */ float $start$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardTransitionAnimationFlow this$0;

                    /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, Function0 function0, Function0 function02, float f, float f2, Function0 function03, Function1 function1, Interpolator interpolator, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, String str) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$onCancel$inlined = function0;
                        this.$onFinish$inlined = function02;
                        this.$start$inlined = f;
                        this.$chunks$inlined = f2;
                        this.$onStart$inlined = function03;
                        this.$onStep$inlined = function1;
                        this.$interpolator$inlined = interpolator;
                        this.this$0 = keyguardTransitionAnimationFlow;
                        this.$name$inlined = str;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                        /*
                            Method dump skipped, instructions count: 237
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState74qcysc$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function02, function03, m3436divLRDsOJo, m3436divLRDsOJo2, function0, function1, interpolator, keyguardTransitionAnimationFlow2, str), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
        }

        private FlowBuilder(long j, Edge edge) {
            this.transitionDuration = j;
            this.edge = edge;
        }
    }

    public KeyguardTransitionAnimationFlow(KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionAnimationLogger keyguardTransitionAnimationLogger) {
        this.transitionInteractor = keyguardTransitionInteractor;
        this.logger = keyguardTransitionAnimationLogger;
    }

    /* renamed from: setup-VtjQ1oo, reason: not valid java name */
    public final FlowBuilder m2598setupVtjQ1oo(long j, Edge edge) {
        return new FlowBuilder(this, j, edge, null);
    }
}
