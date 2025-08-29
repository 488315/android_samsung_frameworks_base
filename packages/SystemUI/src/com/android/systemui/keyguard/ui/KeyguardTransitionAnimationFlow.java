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
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
public final class KeyguardTransitionAnimationFlow {
    public final KeyguardTransitionAnimationLogger logger;
    public final KeyguardTransitionInteractor transitionInteractor;

    public final class FlowBuilder {
        public final Edge edge;
        public final long transitionDuration;

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

        /* JADX WARN: Removed duplicated region for block: B:6:0x0018  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static final Float access$sharedFlowWithState_74qcysc$stepToValue(float f, float f2, Function0 function0, Function1 function1, Interpolator interpolator, TransitionStep transitionStep) {
            Float fValueOf;
            float f3 = (transitionStep.value - f) * f2;
            int i = WhenMappings.$EnumSwitchMapping$0[transitionStep.transitionState.ordinal()];
            if (i == 1) {
                if (function0 != null) {
                    function0.invoke();
                }
                fValueOf = Float.valueOf(Math.max(0.0f, Math.min(1.0f, f3)));
            } else if (i == 2) {
                fValueOf = f3 >= 1.0f ? Float.valueOf(1.0f) : f3 >= 0.0f ? Float.valueOf(f3) : null;
            }
            if (fValueOf != null) {
                return (Float) function1.mo781invoke(Float.valueOf(interpolator.getInterpolation(fValueOf.floatValue())));
            }
            return null;
        }

        /* renamed from: sharedFlow-74qcysc$default, reason: not valid java name */
        public static KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2614sharedFlow74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long duration;
            if ((i & 1) != 0) {
                j = flowBuilder.transitionDuration;
            }
            long j3 = j;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                duration = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                duration = j2;
            }
            return new KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1(flowBuilder.m2616sharedFlowWithState74qcysc(j3, function1, duration, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str));
        }

        /* renamed from: sharedFlowWithState-74qcysc$default, reason: not valid java name */
        public static /* synthetic */ Flow m2615sharedFlowWithState74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long duration;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                duration = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                duration = j2;
            }
            return flowBuilder.m2616sharedFlowWithState74qcysc(j, function1, duration, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str);
        }

        public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo(final float f) {
            Duration.Companion companion = Duration.Companion;
            return m2614sharedFlow74qcysc$default(this, DurationKt.toDuration(1, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ((Float) obj).floatValue();
                    return Float.valueOf(f);
                }
            }, 0L, null, new KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda1(f), new KeyguardTransitionAnimationFlow$FlowBuilder$$ExternalSyntheticLambda1(f), null, null, 204);
        }

        public final FlowBuilder setupWithoutSceneContainer(Edge.StateToState stateToState) {
            return KeyguardTransitionAnimationFlow.this.m2613setupVtjQ1oo(this.transitionDuration, stateToState);
        }

        /* renamed from: sharedFlowWithState-74qcysc, reason: not valid java name */
        public final Flow m2616sharedFlowWithState74qcysc(long j, final Function1 function1, long j2, final Function0 function0, final Function0 function02, final Function0 function03, final Interpolator interpolator, final String str) {
            Duration.Companion companion = Duration.Companion;
            if (j <= 0) {
                throw new IllegalArgumentException(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("duration must be a positive number: ", Duration.m3465toStringimpl(j)));
            }
            long jM3460plusLRDsOJo = Duration.m3460plusLRDsOJo(j2, j);
            long j3 = this.transitionDuration;
            if (Duration.m3454compareToLRDsOJo(jM3460plusLRDsOJo, j3) > 0) {
                String strM3465toStringimpl = Duration.m3465toStringimpl(j2);
                String strM3465toStringimpl2 = Duration.m3465toStringimpl(j);
                throw new IllegalArgumentException(TransitionKt$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("startTime(", strM3465toStringimpl, ") + duration(", strM3465toStringimpl2, ") must be <= transitionDuration("), Duration.m3465toStringimpl(j3), ")"));
            }
            final float fM3455divLRDsOJo = (float) Duration.m3455divLRDsOJo(j2, j3);
            final float fM3455divLRDsOJo2 = (float) Duration.m3455divLRDsOJo(j3, j);
            KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = KeyguardTransitionAnimationFlow.this;
            KeyguardTransitionAnimationLogger keyguardTransitionAnimationLogger = keyguardTransitionAnimationFlow.logger;
            keyguardTransitionAnimationLogger.getClass();
            if (str != null) {
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 = new KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = keyguardTransitionAnimationLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardTransitionAnimationLog", logLevel, keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.str2 = String.valueOf(fM3455divLRDsOJo);
                logBuffer.commit(logMessageObtain);
            }
            final Flow flowTransition = keyguardTransitionAnimationFlow.transitionInteractor.transition(this.edge);
            final KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow2 = KeyguardTransitionAnimationFlow.this;
            return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$mapNotNull$1

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

                    /* JADX WARN: Removed duplicated region for block: B:25:0x005b  */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        Float fAccess$sharedFlowWithState_74qcysc$stepToValue;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            TransitionStep transitionStep = (TransitionStep) obj;
                            KeyguardState keyguardState = transitionStep.from;
                            int[] iArr = KeyguardTransitionAnimationFlow.FlowBuilder.WhenMappings.$EnumSwitchMapping$0;
                            TransitionState transitionState = transitionStep.transitionState;
                            int i3 = iArr[transitionState.ordinal()];
                            if (i3 == 1) {
                                fAccess$sharedFlowWithState_74qcysc$stepToValue = KeyguardTransitionAnimationFlow.FlowBuilder.access$sharedFlowWithState_74qcysc$stepToValue(this.$start$inlined, this.$chunks$inlined, this.$onStart$inlined, this.$onStep$inlined, this.$interpolator$inlined, transitionStep);
                            } else if (i3 == 2) {
                                fAccess$sharedFlowWithState_74qcysc$stepToValue = KeyguardTransitionAnimationFlow.FlowBuilder.access$sharedFlowWithState_74qcysc$stepToValue(this.$start$inlined, this.$chunks$inlined, this.$onStart$inlined, this.$onStep$inlined, this.$interpolator$inlined, transitionStep);
                            } else if (i3 == 3) {
                                Function0 function0 = this.$onCancel$inlined;
                                if (function0 != null) {
                                    fAccess$sharedFlowWithState_74qcysc$stepToValue = (Float) function0.invoke();
                                }
                            } else {
                                if (i3 != 4) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                Function0 function02 = this.$onFinish$inlined;
                                fAccess$sharedFlowWithState_74qcysc$stepToValue = function02 != null ? (Float) function02.invoke() : null;
                            }
                            StateToValue stateToValue = new StateToValue(keyguardState, transitionStep.to, transitionState, fAccess$sharedFlowWithState_74qcysc$stepToValue);
                            KeyguardTransitionAnimationLogger keyguardTransitionAnimationLogger = this.this$0.logger;
                            keyguardTransitionAnimationLogger.getClass();
                            String str = this.$name$inlined;
                            if (str != null) {
                                LogLevel logLevel = LogLevel.DEBUG;
                                KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 = new KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0(0);
                                LogBuffer logBuffer = keyguardTransitionAnimationLogger.buffer;
                                LogMessage logMessageObtain = logBuffer.obtain("KeyguardTransitionAnimationLog", logLevel, keyguardTransitionAnimationLogger$$ExternalSyntheticLambda0, null);
                                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                logMessageImpl.str1 = "[" + str + "][" + transitionState + "]";
                                logMessageImpl.str2 = String.valueOf(transitionStep.value);
                                logMessageImpl.str3 = String.valueOf(stateToValue.value);
                                logBuffer.commit(logMessageObtain);
                            }
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(stateToValue, anonymousClass1) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector, function02, function03, fM3455divLRDsOJo, fM3455divLRDsOJo2, function0, function1, interpolator, keyguardTransitionAnimationFlow2, str), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
    public final FlowBuilder m2613setupVtjQ1oo(long j, Edge edge) {
        return new FlowBuilder(this, j, edge, null);
    }
}
