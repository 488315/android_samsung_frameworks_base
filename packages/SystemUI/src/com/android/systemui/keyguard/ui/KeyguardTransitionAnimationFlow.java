package com.android.systemui.keyguard.ui;

import android.view.animation.Interpolator;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.keyguard.logging.KeyguardTransitionAnimationLogger;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardTransitionAnimationFlow {
    public final KeyguardTransitionAnimationLogger logger;
    public final KeyguardTransitionInteractor transitionInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FlowBuilder {
        public final Edge edge;
        public final long transitionDuration;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                return Float.valueOf(((Number) function1.invoke(Float.valueOf(interpolator.getInterpolation(valueOf.floatValue())))).floatValue());
            }
            return null;
        }

        /* renamed from: sharedFlow-74qcysc$default, reason: not valid java name */
        public static KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long j3;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                j3 = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                j3 = j2;
            }
            return new KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1(flowBuilder.m1964sharedFlowWithState74qcysc(j, function1, j3, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str));
        }

        /* renamed from: sharedFlowWithState-74qcysc$default, reason: not valid java name */
        public static /* synthetic */ Flow m1963sharedFlowWithState74qcysc$default(FlowBuilder flowBuilder, long j, Function1 function1, long j2, Function0 function0, Function0 function02, Function0 function03, Interpolator interpolator, String str, int i) {
            long j3;
            if ((i & 4) != 0) {
                Duration.Companion companion = Duration.Companion;
                j3 = DurationKt.toDuration(0, DurationUnit.MILLISECONDS);
            } else {
                j3 = j2;
            }
            return flowBuilder.m1964sharedFlowWithState74qcysc(j, function1, j3, (i & 8) != 0 ? null : function0, (i & 16) != 0 ? null : function02, (i & 32) != 0 ? null : function03, (i & 64) != 0 ? Interpolators.LINEAR : interpolator, (i & 128) != 0 ? null : str);
        }

        public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo(final float f) {
            Duration.Companion companion = Duration.Companion;
            return m1962sharedFlow74qcysc$default(this, DurationKt.toDuration(1, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$immediatelyTransitionTo$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((Number) obj).floatValue();
                    return Float.valueOf(f);
                }
            }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$immediatelyTransitionTo$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Float.valueOf(f);
                }
            }, null, null, 220);
        }

        public final FlowBuilder setupWithoutSceneContainer(Edge.StateToState stateToState) {
            Flags.sceneContainer();
            return KeyguardTransitionAnimationFlow.this.m1961setupVtjQ1oo(this.transitionDuration, stateToState);
        }

        /* renamed from: sharedFlowWithState-74qcysc, reason: not valid java name */
        public final Flow m1964sharedFlowWithState74qcysc(long j, final Function1 function1, long j2, final Function0 function0, final Function0 function02, final Function0 function03, final Interpolator interpolator, final String str) {
            Duration.Companion companion = Duration.Companion;
            if (j <= 0) {
                throw new IllegalArgumentException("duration must be a positive number: ".concat(Duration.m2545toStringimpl(j)));
            }
            long m2542plusLRDsOJo = Duration.m2542plusLRDsOJo(j2, j);
            long j3 = this.transitionDuration;
            if (Duration.m2537compareToLRDsOJo(m2542plusLRDsOJo, j3) > 0) {
                String m2545toStringimpl = Duration.m2545toStringimpl(j2);
                String m2545toStringimpl2 = Duration.m2545toStringimpl(j);
                throw new IllegalArgumentException(ComponentActivity$1$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("startTime(", m2545toStringimpl, ") + duration(", m2545toStringimpl2, ") must be <= transitionDuration("), Duration.m2545toStringimpl(j3), ")"));
            }
            DurationUnit m2540getStorageUnitimpl = Duration.m2540getStorageUnitimpl(j2);
            DurationUnit m2540getStorageUnitimpl2 = Duration.m2540getStorageUnitimpl(j3);
            if (m2540getStorageUnitimpl.compareTo(m2540getStorageUnitimpl2) < 0) {
                m2540getStorageUnitimpl = m2540getStorageUnitimpl2;
            }
            final float m2543toDoubleimpl = (float) (Duration.m2543toDoubleimpl(j2, m2540getStorageUnitimpl) / Duration.m2543toDoubleimpl(j3, m2540getStorageUnitimpl));
            DurationUnit m2540getStorageUnitimpl3 = Duration.m2540getStorageUnitimpl(j3);
            DurationUnit m2540getStorageUnitimpl4 = Duration.m2540getStorageUnitimpl(j);
            if (m2540getStorageUnitimpl3.compareTo(m2540getStorageUnitimpl4) < 0) {
                m2540getStorageUnitimpl3 = m2540getStorageUnitimpl4;
            }
            final float m2543toDoubleimpl2 = (float) (Duration.m2543toDoubleimpl(j3, m2540getStorageUnitimpl3) / Duration.m2543toDoubleimpl(j, m2540getStorageUnitimpl3));
            KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow = KeyguardTransitionAnimationFlow.this;
            keyguardTransitionAnimationFlow.logger.logCreate(str, m2543toDoubleimpl);
            final Flow transition = keyguardTransitionAnimationFlow.transitionInteractor.transition(this.edge);
            final KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow2 = KeyguardTransitionAnimationFlow.this;
            return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1$2, reason: invalid class name */
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

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1$2$1, reason: invalid class name */
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
                    public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                        /*
                            r11 = this;
                            boolean r0 = r13 instanceof com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState74qcysc$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r13
                            com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState74qcysc$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState-74qcysc$$inlined$map$1$2$1
                            r0.<init>(r13)
                        L18:
                            java.lang.Object r13 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L30
                            if (r2 != r3) goto L28
                            kotlin.ResultKt.throwOnFailure(r13)
                            goto La7
                        L28:
                            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                            r11.<init>(r12)
                            throw r11
                        L30:
                            kotlin.ResultKt.throwOnFailure(r13)
                            com.android.systemui.keyguard.shared.model.TransitionStep r12 = (com.android.systemui.keyguard.shared.model.TransitionStep) r12
                            com.android.systemui.keyguard.ui.StateToValue r13 = new com.android.systemui.keyguard.ui.StateToValue
                            com.android.systemui.keyguard.shared.model.KeyguardState r2 = r12.from
                            int[] r4 = com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow.FlowBuilder.WhenMappings.$EnumSwitchMapping$0
                            com.android.systemui.keyguard.shared.model.TransitionState r10 = r12.transitionState
                            int r5 = r10.ordinal()
                            r4 = r4[r5]
                            if (r4 == r3) goto L7d
                            r5 = 2
                            if (r4 == r5) goto L6d
                            r5 = 3
                            r6 = 0
                            if (r4 == r5) goto L61
                            r5 = 4
                            if (r4 != r5) goto L5b
                            kotlin.jvm.functions.Function0 r4 = r11.$onFinish$inlined
                            if (r4 == 0) goto L8c
                            java.lang.Object r4 = r4.invoke()
                            r6 = r4
                            java.lang.Float r6 = (java.lang.Float) r6
                            goto L8c
                        L5b:
                            kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                            r11.<init>()
                            throw r11
                        L61:
                            kotlin.jvm.functions.Function0 r4 = r11.$onCancel$inlined
                            if (r4 == 0) goto L8c
                            java.lang.Object r4 = r4.invoke()
                            r6 = r4
                            java.lang.Float r6 = (java.lang.Float) r6
                            goto L8c
                        L6d:
                            kotlin.jvm.functions.Function1 r7 = r11.$onStep$inlined
                            android.view.animation.Interpolator r8 = r11.$interpolator$inlined
                            float r4 = r11.$start$inlined
                            float r5 = r11.$chunks$inlined
                            kotlin.jvm.functions.Function0 r6 = r11.$onStart$inlined
                            r9 = r12
                            java.lang.Float r6 = com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow.FlowBuilder.access$sharedFlowWithState_74qcysc$stepToValue(r4, r5, r6, r7, r8, r9)
                            goto L8c
                        L7d:
                            kotlin.jvm.functions.Function1 r7 = r11.$onStep$inlined
                            android.view.animation.Interpolator r8 = r11.$interpolator$inlined
                            float r4 = r11.$start$inlined
                            float r5 = r11.$chunks$inlined
                            kotlin.jvm.functions.Function0 r6 = r11.$onStart$inlined
                            r9 = r12
                            java.lang.Float r6 = com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow.FlowBuilder.access$sharedFlowWithState_74qcysc$stepToValue(r4, r5, r6, r7, r8, r9)
                        L8c:
                            com.android.systemui.keyguard.shared.model.KeyguardState r4 = r12.to
                            r13.<init>(r2, r4, r10, r6)
                            com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow r2 = r11.this$0
                            com.android.keyguard.logging.KeyguardTransitionAnimationLogger r2 = r2.logger
                            java.lang.String r4 = r11.$name$inlined
                            java.lang.Float r5 = r13.value
                            r2.logTransitionStep(r4, r12, r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                            java.lang.Object r11 = r11.emit(r13, r0)
                            if (r11 != r1) goto La7
                            return r1
                        La7:
                            kotlin.Unit r11 = kotlin.Unit.INSTANCE
                            return r11
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlowWithState74qcysc$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, function02, function03, m2543toDoubleimpl, m2543toDoubleimpl2, function0, function1, interpolator, keyguardTransitionAnimationFlow2, str), continuation);
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
    public final FlowBuilder m1961setupVtjQ1oo(long j, Edge edge) {
        return new FlowBuilder(this, j, edge, null);
    }
}
