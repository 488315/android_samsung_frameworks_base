package androidx.compose.animation.core;

import androidx.compose.animation.EnterExitState;
import androidx.compose.animation.core.SeekableTransitionState;
import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public abstract class TransitionKt {
    public static final Function1 SeekableTransitionStateTotalDurationChanged = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$SeekableTransitionStateTotalDurationChanged$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            SeekableTransitionState seekableTransitionState = (SeekableTransitionState) obj;
            long j = seekableTransitionState.totalDurationNanos;
            ((SnapshotStateObserver) TransitionKt.SeekableStateObserver$delegate.getValue()).observeReads(seekableTransitionState, TransitionKt.SeekableTransitionStateTotalDurationChanged, seekableTransitionState.recalculateTotalDurationNanos);
            long j2 = seekableTransitionState.totalDurationNanos;
            if (j != j2) {
                SeekableTransitionState.SeekingAnimationState seekingAnimationState = seekableTransitionState.currentAnimation;
                if (seekingAnimationState != null) {
                    seekingAnimationState.durationNanos = j2;
                    if (seekingAnimationState.animationSpec == null) {
                        seekingAnimationState.animationSpecDuration = MathKt__MathJVMKt.roundToLong((1.0d - seekingAnimationState.start.get$animation_core(0)) * seekableTransitionState.totalDurationNanos);
                    }
                } else if (j2 != 0) {
                    seekableTransitionState.seekToFraction();
                }
            }
            return Unit.INSTANCE;
        }
    };
    public static final Lazy SeekableStateObserver$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.NONE, new Function0() { // from class: androidx.compose.animation.core.TransitionKt$SeekableStateObserver$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            SnapshotStateObserver snapshotStateObserver = new SnapshotStateObserver(new Function1() { // from class: androidx.compose.animation.core.TransitionKt$SeekableStateObserver$2.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    ((Function0) obj).invoke();
                    return Unit.INSTANCE;
                }
            });
            Snapshot.Companion companion = Snapshot.Companion;
            Function2 function2 = snapshotStateObserver.applyObserver;
            companion.getClass();
            snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
            return snapshotStateObserver;
        }
    });

    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Transition createChildTransitionInternal(final Transition transition, EnterExitState enterExitState, EnterExitState enterExitState2, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.createChildTransitionInternal (Transition.kt:1774)");
        }
        int i2 = (i & 14) ^ 6;
        boolean z = true;
        boolean z2 = (i2 > 4 && ((ComposerImpl) composer).changed(transition)) || (i & 6) == 4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z2) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Transition(new MutableTransitionState(enterExitState), transition, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), transition.label, " > EnterExitTransition"));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        final Transition transition2 = (Transition) objRememberedValue;
        if ((i2 <= 4 || !composerImpl.changed(transition)) && (i & 6) != 4) {
            z = false;
        }
        boolean zChanged = composerImpl.changed(transition2) | z;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$createChildTransitionInternal$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Transition<Object> transition3 = transition;
                        transition3._transitions.add(transition2);
                        final Transition<Object> transition4 = transition;
                        final Transition<Object> transition5 = transition2;
                        return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$createChildTransitionInternal$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                transition4._transitions.remove(transition5);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        EffectsKt.DisposableEffect(transition2, (Function1) objRememberedValue2, composerImpl);
        if (transition.isSeeking()) {
            transition2.seek(enterExitState, enterExitState2);
        } else {
            transition2.updateTarget$animation_core(enterExitState2);
            ((SnapshotMutableStateImpl) transition2.isSeeking$delegate).setValue(Boolean.FALSE);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transition2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Transition.DeferredAnimation createDeferredAnimation(final Transition transition, TwoWayConverter twoWayConverter, String str, Composer composer, int i, int i2) {
        Transition.DeferredAnimation.DeferredAnimationData deferredAnimationData;
        if ((i2 & 2) != 0) {
            str = "DeferredAnimation";
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.createDeferredAnimation (Transition.kt:1732)");
        }
        int i3 = (i & 14) ^ 6;
        boolean z = true;
        boolean z2 = (i3 > 4 && ((ComposerImpl) composer).changed(transition)) || (i & 6) == 4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z2) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Transition.DeferredAnimation(twoWayConverter, str);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        final Transition.DeferredAnimation deferredAnimation = (Transition.DeferredAnimation) objRememberedValue;
        if ((i3 <= 4 || !composerImpl.changed(transition)) && (i & 6) != 4) {
            z = false;
        }
        boolean zChangedInstance = composerImpl.changedInstance(deferredAnimation) | z;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$createDeferredAnimation$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final Transition<Object> transition2 = transition;
                        final Transition.DeferredAnimation<Object, AnimationVector> deferredAnimation2 = deferredAnimation;
                        return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$createDeferredAnimation$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                Transition.TransitionAnimationState transitionAnimationState;
                                Transition transition3 = transition2;
                                transition3.getClass();
                                Transition.DeferredAnimation.DeferredAnimationData deferredAnimationData2 = (Transition.DeferredAnimation.DeferredAnimationData) ((SnapshotMutableStateImpl) deferredAnimation2.data$delegate).getValue();
                                if (deferredAnimationData2 == null || (transitionAnimationState = deferredAnimationData2.animation) == null) {
                                    return;
                                }
                                transition3._animations.remove(transitionAnimationState);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        EffectsKt.DisposableEffect(deferredAnimation, (Function1) objRememberedValue2, composerImpl);
        if (transition.isSeeking() && (deferredAnimationData = (Transition.DeferredAnimation.DeferredAnimationData) ((SnapshotMutableStateImpl) deferredAnimation.data$delegate).getValue()) != null) {
            Function1 function1 = deferredAnimationData.targetValueByState;
            Transition transition2 = Transition.this;
            deferredAnimationData.animation.updateInitialAndTargetValue$animation_core(function1.mo781invoke(transition2.getSegment().getInitialState()), deferredAnimationData.targetValueByState.mo781invoke(transition2.getSegment().getTargetState()), (FiniteAnimationSpec) deferredAnimationData.transitionSpec.mo781invoke(transition2.getSegment()));
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return deferredAnimation;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Transition.TransitionAnimationState createTransitionAnimation(final Transition transition, Object obj, Object obj2, FiniteAnimationSpec finiteAnimationSpec, TwoWayConverter twoWayConverter, String str, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.createTransitionAnimation (Transition.kt:1849)");
        }
        int i2 = (i & 14) ^ 6;
        boolean z = true;
        boolean z2 = (i2 > 4 && ((ComposerImpl) composer).changed(transition)) || (i & 6) == 4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z2) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                AnimationVector animationVector = (AnimationVector) ((TwoWayConverterImpl) twoWayConverter).convertToVector.mo781invoke(obj2);
                animationVector.reset$animation_core();
                Transition.TransitionAnimationState transitionAnimationState = new Transition.TransitionAnimationState(obj, animationVector, twoWayConverter, str);
                composerImpl.updateRememberedValue(transitionAnimationState);
                objRememberedValue = transitionAnimationState;
            }
        }
        final Transition.TransitionAnimationState transitionAnimationState2 = (Transition.TransitionAnimationState) objRememberedValue;
        if (transition.isSeeking()) {
            transitionAnimationState2.updateInitialAndTargetValue$animation_core(obj, obj2, finiteAnimationSpec);
        } else {
            transitionAnimationState2.updateTargetValue$animation_core(obj2, finiteAnimationSpec);
        }
        if ((i2 <= 4 || !composerImpl.changed(transition)) && (i & 6) != 4) {
            z = false;
        }
        boolean zChanged = composerImpl.changed(transitionAnimationState2) | z;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$createTransitionAnimation$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        Transition<Object> transition2 = transition;
                        transition2._animations.add(transitionAnimationState2);
                        final Transition<Object> transition3 = transition;
                        final Transition.TransitionAnimationState<Object, AnimationVector> transitionAnimationState3 = transitionAnimationState2;
                        return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$createTransitionAnimation$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                transition3._animations.remove(transitionAnimationState3);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        EffectsKt.DisposableEffect(transitionAnimationState2, (Function1) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transitionAnimationState2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Transition rememberTransition(TransitionState transitionState, String str, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.rememberTransition (Transition.kt:799)");
        }
        int i2 = (i & 14) ^ 6;
        boolean z = true;
        boolean z2 = (i2 > 4 && ((ComposerImpl) composer).changed(transitionState)) || (i & 6) == 4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z2) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Transition(transitionState, str);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        final Transition transition = (Transition) objRememberedValue;
        if (transitionState instanceof SeekableTransitionState) {
            composerImpl.startReplaceGroup(1030660644);
            SeekableTransitionState seekableTransitionState = (SeekableTransitionState) transitionState;
            Object value = ((SnapshotMutableStateImpl) seekableTransitionState.currentState$delegate).getValue();
            Object value2 = ((SnapshotMutableStateImpl) seekableTransitionState.targetState$delegate).getValue();
            if ((i2 <= 4 || !composerImpl.changed(transitionState)) && (i & 6) != 4) {
                z = false;
            }
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!z) {
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new TransitionKt$rememberTransition$1$1(transitionState, null);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                EffectsKt.LaunchedEffect(value, value2, (Function2) objRememberedValue2, composerImpl);
                composerImpl.end(false);
            }
        } else {
            composerImpl.startReplaceGroup(1031122203);
            transition.animateTo$animation_core(transitionState.getTargetState(), composerImpl, 0);
            composerImpl.end(false);
        }
        boolean zChanged = composerImpl.changed(transition);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                objRememberedValue3 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$rememberTransition$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final Transition<Object> transition2 = transition;
                        return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$rememberTransition$2$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                Transition transition3 = transition2;
                                transition3.onTransitionEnd$animation_core();
                                transition3.transitionState.transitionRemoved$animation_core();
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
        }
        EffectsKt.DisposableEffect(transition, (Function1) objRememberedValue3, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transition;
    }

    public static final Transition updateTransition(Object obj, String str, Composer composer, int i, int i2) {
        if ((i2 & 2) != 0) {
            str = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.updateTransition (Transition.kt:86)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = new Transition(obj, str);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        final Transition transition = (Transition) objRememberedValue;
        transition.animateTo$animation_core(obj, composerImpl, (i & 8) | 48 | (i & 14));
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new Function1() { // from class: androidx.compose.animation.core.TransitionKt$updateTransition$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    final Transition<Object> transition2 = transition;
                    return new DisposableEffectResult() { // from class: androidx.compose.animation.core.TransitionKt$updateTransition$1$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            Transition transition3 = transition2;
                            transition3.onTransitionEnd$animation_core();
                            transition3.transitionState.transitionRemoved$animation_core();
                        }
                    };
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.DisposableEffect(transition, (Function1) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transition;
    }

    public static final Transition updateTransition(MutableTransitionState mutableTransitionState, String str, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.updateTransition (Transition.kt:852)");
        }
        Transition transitionRememberTransition = rememberTransition(mutableTransitionState, str, composer, i & 126);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return transitionRememberTransition;
    }
}
