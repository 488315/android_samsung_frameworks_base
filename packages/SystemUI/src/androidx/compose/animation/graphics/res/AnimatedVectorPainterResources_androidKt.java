package androidx.compose.animation.graphics.res;

import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import androidx.compose.animation.graphics.vector.AnimatedVectorTarget;
import androidx.compose.animation.graphics.vector.Animator;
import androidx.compose.animation.graphics.vector.StateVectorConfig;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainter;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimatedVectorPainterResources_androidKt {
    public static final VectorPainter rememberAnimatedVectorPainter(final AnimatedImageVector animatedImageVector, final boolean z, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter (AnimatedVectorPainterResources.android.kt:44)");
        }
        ComposableSingletons$AnimatedVectorPainterResources_androidKt.INSTANCE.getClass();
        final ComposableLambdaImpl composableLambdaImpl = ComposableSingletons$AnimatedVectorPainterResources_androidKt.f0lambda1;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter (AnimatedVectorPainterResources.android.kt:56)");
        }
        ImageVector imageVector = animatedImageVector.imageVector;
        float f = imageVector.defaultWidth;
        ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(10512245, new Function4() { // from class: androidx.compose.animation.graphics.res.AnimatedVectorPainterResources_androidKt$rememberAnimatedVectorPainter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                ((Number) obj).floatValue();
                ((Number) obj2).floatValue();
                Composer composer2 = (Composer) obj3;
                int intValue = ((Number) obj4).intValue();
                ComposerImpl composerImpl = (ComposerImpl) composer2;
                if (composerImpl.shouldExecute(intValue & 1, (intValue & 129) != 128)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter.<anonymous> (AnimatedVectorPainterResources.android.kt:67)");
                    }
                    Transition updateTransition = TransitionKt.updateTransition(Boolean.valueOf(z), animatedImageVector.imageVector.name, composerImpl, 0, 0);
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    composerImpl.startReplaceGroup(244958144);
                    AnimatedImageVector animatedImageVector2 = animatedImageVector;
                    List list = animatedImageVector2.targets;
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        AnimatedVectorTarget animatedVectorTarget = (AnimatedVectorTarget) list.get(i);
                        Animator animator = animatedVectorTarget.animator;
                        animator.getClass();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.Animator.createVectorConfig (Animator.kt:57)");
                        }
                        composerImpl.startReplaceGroup(-1031781866);
                        Object rememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        if (rememberedValue == Composer.Companion.Empty) {
                            rememberedValue = new StateVectorConfig();
                            composerImpl.updateRememberedValue(rememberedValue);
                        }
                        StateVectorConfig stateVectorConfig = (StateVectorConfig) rememberedValue;
                        animator.Configure(updateTransition, stateVectorConfig, animatedImageVector2.totalDuration, composerImpl, 0);
                        composerImpl.end(false);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        String str = animatedVectorTarget.name;
                        StateVectorConfig stateVectorConfig2 = (StateVectorConfig) linkedHashMap.get(str);
                        if (stateVectorConfig2 != null) {
                            State state = stateVectorConfig.rotationState;
                            if (state != null) {
                                stateVectorConfig2.rotationState = state;
                            }
                            State state2 = stateVectorConfig.pivotXState;
                            if (state2 != null) {
                                stateVectorConfig2.pivotXState = state2;
                            }
                            State state3 = stateVectorConfig.pivotYState;
                            if (state3 != null) {
                                stateVectorConfig2.pivotYState = state3;
                            }
                            State state4 = stateVectorConfig.scaleXState;
                            if (state4 != null) {
                                stateVectorConfig2.scaleXState = state4;
                            }
                            State state5 = stateVectorConfig.scaleYState;
                            if (state5 != null) {
                                stateVectorConfig2.scaleYState = state5;
                            }
                            State state6 = stateVectorConfig.translateXState;
                            if (state6 != null) {
                                stateVectorConfig2.translateXState = state6;
                            }
                            State state7 = stateVectorConfig.translateYState;
                            if (state7 != null) {
                                stateVectorConfig2.translateYState = state7;
                            }
                            State state8 = stateVectorConfig.pathDataState;
                            if (state8 != null) {
                                stateVectorConfig2.pathDataState = state8;
                            }
                            State state9 = stateVectorConfig.fillColorState;
                            if (state9 != null) {
                                stateVectorConfig2.fillColorState = state9;
                            }
                            State state10 = stateVectorConfig.strokeColorState;
                            if (state10 != null) {
                                stateVectorConfig2.strokeColorState = state10;
                            }
                            State state11 = stateVectorConfig.strokeWidthState;
                            if (state11 != null) {
                                stateVectorConfig2.strokeWidthState = state11;
                            }
                            State state12 = stateVectorConfig.strokeAlphaState;
                            if (state12 != null) {
                                stateVectorConfig2.strokeAlphaState = state12;
                            }
                            State state13 = stateVectorConfig.fillAlphaState;
                            if (state13 != null) {
                                stateVectorConfig2.fillAlphaState = state13;
                            }
                            State state14 = stateVectorConfig.trimPathStartState;
                            if (state14 != null) {
                                stateVectorConfig2.trimPathStartState = state14;
                            }
                            State state15 = stateVectorConfig.trimPathEndState;
                            if (state15 != null) {
                                stateVectorConfig2.trimPathEndState = state15;
                            }
                            State state16 = stateVectorConfig.trimPathOffsetState;
                            if (state16 != null) {
                                stateVectorConfig2.trimPathOffsetState = state16;
                            }
                        } else {
                            linkedHashMap.put(str, stateVectorConfig);
                        }
                    }
                    composerImpl.end(false);
                    composableLambdaImpl.invoke(animatedImageVector.imageVector.root, linkedHashMap, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                }
                return Unit.INSTANCE;
            }
        }, composer);
        VectorPainter m569rememberVectorPaintervIP8VLU = VectorPainterKt.m569rememberVectorPaintervIP8VLU(f, imageVector.defaultHeight, imageVector.viewportWidth, imageVector.viewportHeight, imageVector.name, imageVector.tintColor, imageVector.tintBlendMode, rememberComposableLambda, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m569rememberVectorPaintervIP8VLU;
    }
}
