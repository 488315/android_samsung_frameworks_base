package androidx.compose.animation;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.DurationBasedAnimationSpec;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.graphics.TransformOriginKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class EnterExitTransitionKt {
    public static final SpringSpec DefaultOffsetAnimationSpec;
    public static final SpringSpec DefaultSizeAnimationSpec;
    public static final TwoWayConverter TransformOriginVectorConverter = VectorConvertersKt.TwoWayConverter(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$TransformOriginVectorConverter$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            long j = ((TransformOrigin) obj).packedValue;
            return new AnimationVector2D(TransformOrigin.m505getPivotFractionXimpl(j), TransformOrigin.m506getPivotFractionYimpl(j));
        }
    }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$TransformOriginVectorConverter$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AnimationVector2D animationVector2D = (AnimationVector2D) obj;
            return TransformOrigin.m503boximpl(TransformOriginKt.TransformOrigin(animationVector2D.v1, animationVector2D.v2));
        }
    });
    public static final SpringSpec DefaultAlphaAndScaleSpring = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);

    static {
        IntOffset.Companion companion = IntOffset.Companion;
        DefaultOffsetAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        IntSize.Companion companion2 = IntSize.Companion;
        DefaultSizeAnimationSpec = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:156:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Modifier createModifier(Transition transition, EnterTransition enterTransition, ExitTransition exitTransition, String str, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        Transition.DeferredAnimation deferredAnimation;
        Transition.DeferredAnimation deferredAnimation2;
        Transition.DeferredAnimation deferredAnimation3;
        ChangeSize changeSize;
        Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation;
        Transition.DeferredAnimation deferredAnimation4;
        Transition.DeferredAnimation deferredAnimation5;
        Transition transition2;
        Transition.DeferredAnimation deferredAnimation6;
        ComposerImpl composerImpl2;
        Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation2;
        Object enterExitTransitionKt$$ExternalSyntheticLambda0;
        ComposerImpl composerImpl3;
        ExitTransition exitTransition2;
        EnterTransition enterTransition2;
        final AnonymousClass1 anonymousClass1 = new Function0() { // from class: androidx.compose.animation.EnterExitTransitionKt.createModifier.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        };
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.createModifier (EnterExitTransition.kt:860)");
        }
        int i3 = i & 14;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.trackActiveEnter (EnterExitTransition.kt:908)");
        }
        boolean z = ((i3 ^ 6) > 4 && ((ComposerImpl) composer).changed(transition)) || (i & 6) == 4;
        ComposerImpl composerImpl4 = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl4.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!z) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(enterTransition);
                composerImpl4.updateRememberedValue(objRememberedValue);
            }
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        Object currentState = transition.transitionState.getCurrentState();
        SnapshotMutableStateImpl snapshotMutableStateImpl = (SnapshotMutableStateImpl) transition.targetState$delegate;
        Object value = snapshotMutableStateImpl.getValue();
        TransitionState transitionState = transition.transitionState;
        if (currentState == value && transitionState.getCurrentState() == EnterExitState.Visible) {
            if (transition.isSeeking()) {
                mutableState.setValue(enterTransition);
            } else {
                EnterTransition.Companion.getClass();
                mutableState.setValue(EnterTransition.None);
            }
        } else if (snapshotMutableStateImpl.getValue() == EnterExitState.Visible) {
            mutableState.setValue(((EnterTransition) mutableState.getValue()).plus(enterTransition));
        }
        EnterTransition enterTransition3 = (EnterTransition) mutableState.getValue();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        int i4 = i >> 3;
        int i5 = (i4 & 112) | i3;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.trackActiveExit (EnterExitTransition.kt:928)");
        }
        boolean z2 = (((i5 & 14) ^ 6) > 4 && composerImpl4.changed(transition)) || (i5 & 6) == 4;
        Object objRememberedValue2 = composerImpl4.rememberedValue();
        if (!z2) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = SnapshotStateKt.mutableStateOf$default(exitTransition);
                composerImpl4.updateRememberedValue(objRememberedValue2);
            }
        }
        MutableState mutableState2 = (MutableState) objRememberedValue2;
        if (transitionState.getCurrentState() == snapshotMutableStateImpl.getValue() && transitionState.getCurrentState() == EnterExitState.Visible) {
            if (transition.isSeeking()) {
                mutableState2.setValue(exitTransition);
            } else {
                ExitTransition.Companion.getClass();
                mutableState2.setValue(ExitTransition.None);
            }
        } else if (snapshotMutableStateImpl.getValue() != EnterExitState.Visible) {
            mutableState2.setValue(((ExitTransition) mutableState2.getValue()).plus(exitTransition));
        }
        ExitTransition exitTransition3 = (ExitTransition) mutableState2.getValue();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        boolean z3 = (enterTransition3.getData$animation().slide == null && exitTransition3.getData$animation().slide == null) ? false : true;
        boolean z4 = (enterTransition3.getData$animation().changeSize == null && exitTransition3.getData$animation().changeSize == null) ? false : true;
        if (z3) {
            composerImpl4.startReplaceGroup(-821159459);
            IntOffset.Companion companion2 = IntOffset.Companion;
            TwoWayConverter twoWayConverter = VectorConvertersKt.IntOffsetToVector;
            Object objRememberedValue3 = composerImpl4.rememberedValue();
            companion.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                objRememberedValue3 = str + " slide";
                composerImpl4.updateRememberedValue(objRememberedValue3);
            }
            String str2 = (String) objRememberedValue3;
            composerImpl = composerImpl4;
            i2 = i4;
            Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation3 = TransitionKt.createDeferredAnimation(transition, twoWayConverter, str2, composerImpl, i3 | 384, 0);
            composerImpl.end(false);
            deferredAnimation = deferredAnimationCreateDeferredAnimation3;
        } else {
            i2 = i4;
            composerImpl = composerImpl4;
            composerImpl.startReplaceGroup(-821053656);
            composerImpl.end(false);
            deferredAnimation = null;
        }
        if (z4) {
            composerImpl.startReplaceGroup(-820961865);
            IntSize.Companion companion3 = IntSize.Companion;
            TwoWayConverter twoWayConverter2 = VectorConvertersKt.IntSizeToVector;
            Object objRememberedValue4 = composerImpl.rememberedValue();
            companion.getClass();
            if (objRememberedValue4 == Composer.Companion.Empty) {
                objRememberedValue4 = str + " shrink/expand";
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation4 = TransitionKt.createDeferredAnimation(transition, twoWayConverter2, (String) objRememberedValue4, composerImpl, i3 | 384, 0);
            composerImpl.end(false);
            deferredAnimation2 = deferredAnimationCreateDeferredAnimation4;
        } else {
            composerImpl.startReplaceGroup(-820851041);
            composerImpl.end(false);
            deferredAnimation2 = null;
        }
        if (z4) {
            composerImpl.startReplaceGroup(-820777446);
            IntOffset.Companion companion4 = IntOffset.Companion;
            TwoWayConverter twoWayConverter3 = VectorConvertersKt.IntOffsetToVector;
            Object objRememberedValue5 = composerImpl.rememberedValue();
            companion.getClass();
            if (objRememberedValue5 == Composer.Companion.Empty) {
                objRememberedValue5 = str + " InterruptionHandlingOffset";
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation5 = TransitionKt.createDeferredAnimation(transition, twoWayConverter3, (String) objRememberedValue5, composerImpl, i3 | 384, 0);
            composerImpl.end(false);
            deferredAnimation3 = deferredAnimationCreateDeferredAnimation5;
        } else {
            composerImpl.startReplaceGroup(-820608001);
            composerImpl.end(false);
            deferredAnimation3 = null;
        }
        ChangeSize changeSize2 = enterTransition3.getData$animation().changeSize;
        final boolean z5 = ((changeSize2 == null || changeSize2.clip) && ((changeSize = exitTransition3.getData$animation().changeSize) == null || changeSize.clip) && z4) ? false : true;
        int i6 = i3 | (i2 & 7168);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.createGraphicsLayerBlock (EnterExitTransition.kt:956)");
        }
        boolean z6 = (enterTransition3.getData$animation().fade == null && exitTransition3.getData$animation().fade == null) ? false : true;
        boolean z7 = (enterTransition3.getData$animation().scale == null && exitTransition3.getData$animation().scale == null) ? false : true;
        if (z6) {
            composerImpl.startReplaceGroup(-675026101);
            FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
            TwoWayConverter twoWayConverter4 = VectorConvertersKt.FloatToVector;
            Object objRememberedValue6 = composerImpl.rememberedValue();
            companion.getClass();
            if (objRememberedValue6 == Composer.Companion.Empty) {
                objRememberedValue6 = str + " alpha";
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
            deferredAnimationCreateDeferredAnimation = TransitionKt.createDeferredAnimation(transition, twoWayConverter4, (String) objRememberedValue6, composerImpl, (i6 & 14) | 384, 0);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-674857617);
            composerImpl.end(false);
            deferredAnimationCreateDeferredAnimation = null;
        }
        if (z7) {
            composerImpl.startReplaceGroup(-674790005);
            FloatCompanionObject floatCompanionObject2 = FloatCompanionObject.INSTANCE;
            Transition.DeferredAnimation deferredAnimation7 = deferredAnimationCreateDeferredAnimation;
            TwoWayConverter twoWayConverter5 = VectorConvertersKt.FloatToVector;
            Object objRememberedValue7 = composerImpl.rememberedValue();
            companion.getClass();
            if (objRememberedValue7 == Composer.Companion.Empty) {
                objRememberedValue7 = str + " scale";
                composerImpl.updateRememberedValue(objRememberedValue7);
            }
            deferredAnimation4 = deferredAnimation7;
            Transition.DeferredAnimation deferredAnimationCreateDeferredAnimation6 = TransitionKt.createDeferredAnimation(transition, twoWayConverter5, (String) objRememberedValue7, composerImpl, (i6 & 14) | 384, 0);
            composerImpl.end(false);
            deferredAnimation5 = deferredAnimationCreateDeferredAnimation6;
        } else {
            deferredAnimation4 = deferredAnimationCreateDeferredAnimation;
            composerImpl.startReplaceGroup(-674621521);
            composerImpl.end(false);
            deferredAnimation5 = null;
        }
        if (z7) {
            composerImpl.startReplaceGroup(-674543896);
            deferredAnimation6 = deferredAnimation5;
            transition2 = transition;
            deferredAnimationCreateDeferredAnimation2 = TransitionKt.createDeferredAnimation(transition2, TransformOriginVectorConverter, "TransformOriginInterruptionHandling", composerImpl, (i6 & 14) | 384, 0);
            composerImpl2 = composerImpl;
            composerImpl2.end(false);
        } else {
            transition2 = transition;
            deferredAnimation6 = deferredAnimation5;
            composerImpl2 = composerImpl;
            composerImpl2.startReplaceGroup(-674372529);
            composerImpl2.end(false);
            deferredAnimationCreateDeferredAnimation2 = null;
        }
        boolean zChangedInstance = composerImpl2.changedInstance(deferredAnimation4) | composerImpl2.changed(enterTransition3) | composerImpl2.changed(exitTransition3) | composerImpl2.changedInstance(deferredAnimation6) | ((((i6 & 14) ^ 6) > 4 && composerImpl2.changed(transition2)) || (i6 & 6) == 4) | composerImpl2.changedInstance(deferredAnimationCreateDeferredAnimation2);
        Object objRememberedValue8 = composerImpl2.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue8 == Composer.Companion.Empty) {
                composerImpl3 = composerImpl2;
                exitTransition2 = exitTransition3;
                enterTransition2 = enterTransition3;
                enterExitTransitionKt$$ExternalSyntheticLambda0 = new EnterExitTransitionKt$$ExternalSyntheticLambda0(deferredAnimation4, deferredAnimation6, transition, enterTransition2, exitTransition2, deferredAnimationCreateDeferredAnimation2);
                composerImpl3.updateRememberedValue(enterExitTransitionKt$$ExternalSyntheticLambda0);
            } else {
                composerImpl3 = composerImpl2;
                enterExitTransitionKt$$ExternalSyntheticLambda0 = objRememberedValue8;
                exitTransition2 = exitTransition3;
                enterTransition2 = enterTransition3;
            }
        }
        GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit = (GraphicsLayerBlockForEnterExit) enterExitTransitionKt$$ExternalSyntheticLambda0;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        Modifier.Companion companion5 = Modifier.Companion;
        boolean zChanged = composerImpl3.changed(z5) | ((((i & 7168) ^ 3072) > 2048 && composerImpl3.changed(anonymousClass1)) || (i & 3072) == 2048);
        Object objRememberedValue9 = composerImpl3.rememberedValue();
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue9 == Composer.Companion.Empty) {
                objRememberedValue9 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$createModifier$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setClip(!z5 && ((Boolean) anonymousClass1.invoke()).booleanValue());
                        return Unit.INSTANCE;
                    }
                };
                composerImpl3.updateRememberedValue(objRememberedValue9);
            }
        }
        Modifier modifierThen = GraphicsLayerModifierKt.graphicsLayer(companion5, (Function1) objRememberedValue9).then(new EnterExitTransitionElement(transition, deferredAnimation2, deferredAnimation3, deferredAnimation, enterTransition2, exitTransition2, anonymousClass1, graphicsLayerBlockForEnterExit));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return modifierThen;
    }

    public static final EnterTransition expandIn(FiniteAnimationSpec finiteAnimationSpec, BiasAlignment biasAlignment, boolean z, Function1 function1) {
        return new EnterTransitionImpl(new TransitionData(null, null, new ChangeSize(biasAlignment, function1, finiteAnimationSpec, z), null, false, null, 59, null));
    }

    public static EnterTransition expandVertically$default(TweenSpec tweenSpec, BiasAlignment.Vertical vertical, final Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = tweenSpec;
        if ((i & 1) != 0) {
            IntSize.Companion companion = IntSize.Companion;
            finiteAnimationSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        }
        if ((i & 2) != 0) {
            Alignment.Companion.getClass();
            vertical = Alignment.Companion.Bottom;
        }
        boolean z = (i & 4) != 0;
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandVertically$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                    ((Number) obj).intValue();
                    return 0;
                }
            };
        }
        return expandIn(finiteAnimationSpecSpring$default, toAlignment(vertical), z, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$expandVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return IntSize.m861boximpl((((Number) function1.mo781invoke(Integer.valueOf((int) (r0 & 4294967295L)))).intValue() & 4294967295L) | (((int) (((IntSize) obj).packedValue >> 32)) << 32));
            }
        });
    }

    public static final EnterTransition fadeIn(FiniteAnimationSpec finiteAnimationSpec) {
        return new EnterTransitionImpl(new TransitionData(new Fade(0.0f, finiteAnimationSpec), null, null, null, false, null, 62, null));
    }

    public static /* synthetic */ EnterTransition fadeIn$default(DurationBasedAnimationSpec durationBasedAnimationSpec, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = durationBasedAnimationSpec;
        if ((i & 1) != 0) {
            finiteAnimationSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        }
        return fadeIn(finiteAnimationSpecSpring$default);
    }

    public static ExitTransition fadeOut$default(DurationBasedAnimationSpec durationBasedAnimationSpec, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = durationBasedAnimationSpec;
        if ((i & 1) != 0) {
            finiteAnimationSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5);
        }
        return new ExitTransitionImpl(new TransitionData(new Fade(0.0f, finiteAnimationSpecSpring$default), null, null, null, false, null, 62, null));
    }

    /* renamed from: scaleIn-L8ZKh-E$default, reason: not valid java name */
    public static EnterTransition m5scaleInL8ZKhE$default(TweenSpec tweenSpec, float f, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = (i & 1) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : tweenSpec;
        float f2 = (i & 2) != 0 ? 0.0f : f;
        TransformOrigin.Companion.getClass();
        return new EnterTransitionImpl(new TransitionData(null, null, null, new Scale(f2, TransformOrigin.Center, finiteAnimationSpecSpring$default, null), false, null, 55, null));
    }

    /* renamed from: scaleOut-L8ZKh-E$default, reason: not valid java name */
    public static ExitTransition m6scaleOutL8ZKhE$default(TweenSpec tweenSpec, float f, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = (i & 1) != 0 ? AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5) : tweenSpec;
        float f2 = (i & 2) != 0 ? 0.0f : f;
        TransformOrigin.Companion.getClass();
        return new ExitTransitionImpl(new TransitionData(null, null, null, new Scale(f2, TransformOrigin.Center, finiteAnimationSpecSpring$default, null), false, null, 55, null));
    }

    public static final ExitTransition shrinkOut(FiniteAnimationSpec finiteAnimationSpec, BiasAlignment biasAlignment, boolean z, Function1 function1) {
        return new ExitTransitionImpl(new TransitionData(null, null, new ChangeSize(biasAlignment, function1, finiteAnimationSpec, z), null, false, null, 59, null));
    }

    public static ExitTransition shrinkOut$default() {
        IntSize.Companion companion = IntSize.Companion;
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        Alignment.Companion.getClass();
        return shrinkOut(springSpecSpring$default, Alignment.Companion.BottomEnd, true, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt.shrinkOut.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                long j = ((IntSize) obj).packedValue;
                long j2 = 0;
                return IntSize.m861boximpl((j2 & 4294967295L) | (j2 << 32));
            }
        });
    }

    public static ExitTransition shrinkVertically$default(TweenSpec tweenSpec, BiasAlignment.Vertical vertical, final Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = tweenSpec;
        if ((i & 1) != 0) {
            IntSize.Companion companion = IntSize.Companion;
            finiteAnimationSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntSize.m861boximpl(VisibilityThresholdsKt.getVisibilityThreshold$3()), 1);
        }
        if ((i & 2) != 0) {
            Alignment.Companion.getClass();
            vertical = Alignment.Companion.Bottom;
        }
        boolean z = (i & 4) != 0;
        if ((i & 8) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkVertically$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                    ((Number) obj).intValue();
                    return 0;
                }
            };
        }
        return shrinkOut(finiteAnimationSpecSpring$default, toAlignment(vertical), z, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt$shrinkVertically$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return IntSize.m861boximpl((((Number) function1.mo781invoke(Integer.valueOf((int) (r0 & 4294967295L)))).intValue() & 4294967295L) | (((int) (((IntSize) obj).packedValue >> 32)) << 32));
            }
        });
    }

    public static final EnterTransition slideInVertically(final Function1 function1, FiniteAnimationSpec finiteAnimationSpec) {
        return new EnterTransitionImpl(new TransitionData(null, new Slide(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt.slideInVertically.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return IntOffset.m849boximpl((((Number) function1.mo781invoke(Integer.valueOf((int) (((IntSize) obj).packedValue & 4294967295L)))).intValue() & 4294967295L) | (0 << 32));
            }
        }, finiteAnimationSpec), null, null, false, null, 61, null));
    }

    public static /* synthetic */ EnterTransition slideInVertically$default(TweenSpec tweenSpec, Function1 function1, int i) {
        FiniteAnimationSpec finiteAnimationSpecSpring$default = tweenSpec;
        if ((i & 1) != 0) {
            IntOffset.Companion companion = IntOffset.Companion;
            finiteAnimationSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, IntOffset.m849boximpl(VisibilityThresholdsKt.getVisibilityThreshold()), 1);
        }
        if ((i & 2) != 0) {
            function1 = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt.slideInVertically.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return Integer.valueOf((-((Number) obj).intValue()) / 2);
                }
            };
        }
        return slideInVertically(function1, finiteAnimationSpecSpring$default);
    }

    public static final ExitTransition slideOutVertically(TweenSpec tweenSpec, final Function1 function1) {
        return new ExitTransitionImpl(new TransitionData(null, new Slide(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionKt.slideOutVertically.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return IntOffset.m849boximpl((((Number) function1.mo781invoke(Integer.valueOf((int) (((IntSize) obj).packedValue & 4294967295L)))).intValue() & 4294967295L) | (0 << 32));
            }
        }, tweenSpec), null, null, false, null, 61, null));
    }

    public static final BiasAlignment toAlignment(Alignment.Horizontal horizontal) {
        Alignment.Companion.getClass();
        return Intrinsics.areEqual(horizontal, Alignment.Companion.Start) ? Alignment.Companion.CenterStart : Intrinsics.areEqual(horizontal, Alignment.Companion.End) ? Alignment.Companion.CenterEnd : Alignment.Companion.Center;
    }

    public static final BiasAlignment toAlignment(Alignment.Vertical vertical) {
        Alignment.Companion.getClass();
        if (Intrinsics.areEqual(vertical, Alignment.Companion.Top)) {
            return Alignment.Companion.TopCenter;
        }
        if (Intrinsics.areEqual(vertical, Alignment.Companion.Bottom)) {
            return Alignment.Companion.BottomCenter;
        }
        return Alignment.Companion.Center;
    }
}
