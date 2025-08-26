package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
public abstract class PredictiveBackHandlerKt {
    /* JADX WARN: Removed duplicated region for block: B:35:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void PredictiveBackHandler(final SceneTransitionLayoutImpl sceneTransitionLayoutImpl, final UserActionResult userActionResult, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1608346907);
        int i2 = (composerImpl.changed(sceneTransitionLayoutImpl) ? 4 : 2) | i | (composerImpl.changed(userActionResult) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.compose.animation.scene.PredictiveBackHandler (PredictiveBackHandler.kt:45)");
            }
            boolean z = userActionResult != null;
            composerImpl.startReplaceGroup(1163450793);
            boolean z2 = ((i2 & 112) == 32) | ((i2 & 14) == 4);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z2) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new PredictiveBackHandlerKt$PredictiveBackHandler$1$1(userActionResult, sceneTransitionLayoutImpl, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                androidx.activity.compose.PredictiveBackHandlerKt.PredictiveBackHandler(z, (Function2) objRememberedValue, composerImpl, 0, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(userActionResult, i) { // from class: com.android.compose.animation.scene.PredictiveBackHandlerKt$$ExternalSyntheticLambda0
                public final /* synthetic */ UserActionResult f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PredictiveBackHandlerKt.PredictiveBackHandler(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Object access$animateProgress$animateOffset(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SwipeAnimation swipeAnimation, ContentKey contentKey, AnimationSpec animationSpec, Continuation continuation) {
        TransitionState transitionState = mutableSceneTransitionLayoutStateImpl.getTransitionState();
        TransitionState.Transition transition = swipeAnimation.contentTransition;
        if (transition == null) {
            transition = null;
        }
        if (!Intrinsics.areEqual(transitionState, transition) || swipeAnimation.isAnimatingOffset()) {
            return Unit.INSTANCE;
        }
        if (animationSpec == null) {
            TransitionState.Transition transition2 = swipeAnimation.contentTransition;
            animationSpec = (transition2 != null ? transition2 : null).transformationSpec.progressSpec;
        }
        Object objAnimateOffset$default = SwipeAnimation.animateOffset$default(swipeAnimation, 0.0f, contentKey, animationSpec, null, (SuspendLambda) continuation, 8);
        return objAnimateOffset$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objAnimateOffset$default : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$startTransition(MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, SwipeAnimation swipeAnimation, Job job, ContinuationImpl continuationImpl) {
        PredictiveBackHandlerKt$startTransition$1 predictiveBackHandlerKt$startTransition$1;
        if (continuationImpl instanceof PredictiveBackHandlerKt$startTransition$1) {
            predictiveBackHandlerKt$startTransition$1 = (PredictiveBackHandlerKt$startTransition$1) continuationImpl;
            int i = predictiveBackHandlerKt$startTransition$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                predictiveBackHandlerKt$startTransition$1.label = i - Integer.MIN_VALUE;
            } else {
                predictiveBackHandlerKt$startTransition$1 = new PredictiveBackHandlerKt$startTransition$1(continuationImpl);
            }
        }
        Object obj = predictiveBackHandlerKt$startTransition$1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = predictiveBackHandlerKt$startTransition$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            TransitionState.Transition transition = swipeAnimation.contentTransition;
            if (transition == null) {
                transition = null;
            }
            predictiveBackHandlerKt$startTransition$1.L$0 = job;
            predictiveBackHandlerKt$startTransition$1.label = 1;
            if (mutableSceneTransitionLayoutStateImpl.startTransition(transition, true, predictiveBackHandlerKt$startTransition$1) == obj2) {
                return obj2;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            job = (Job) predictiveBackHandlerKt$startTransition$1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (job.isActive()) {
            job.cancel(null);
        }
        return Unit.INSTANCE;
    }
}
