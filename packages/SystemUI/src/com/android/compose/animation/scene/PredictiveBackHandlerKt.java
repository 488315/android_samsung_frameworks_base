package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PredictiveBackHandlerKt {
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0065, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void PredictiveBackHandler(final com.android.compose.animation.scene.SceneTransitionLayoutImpl r7, final com.android.compose.animation.scene.UserActionResult r8, androidx.compose.runtime.Composer r9, final int r10) {
        /*
            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
            r0 = 1608346907(0x5fdd6d1b, float:3.1910877E19)
            r9.startRestartGroup(r0)
            boolean r0 = r9.changed(r7)
            r1 = 4
            if (r0 == 0) goto L11
            r0 = r1
            goto L12
        L11:
            r0 = 2
        L12:
            r0 = r0 | r10
            boolean r2 = r9.changed(r8)
            r3 = 32
            if (r2 == 0) goto L1d
            r2 = r3
            goto L1f
        L1d:
            r2 = 16
        L1f:
            r0 = r0 | r2
            r2 = r0 & 19
            r4 = 18
            if (r2 != r4) goto L31
            boolean r2 = r9.getSkipping()
            if (r2 != 0) goto L2d
            goto L31
        L2d:
            r9.skipToGroupEnd()
            goto L81
        L31:
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L3c
            java.lang.String r2 = "com.android.compose.animation.scene.PredictiveBackHandler (PredictiveBackHandler.kt:45)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r2)
        L3c:
            r2 = 1
            r4 = 0
            if (r8 == 0) goto L42
            r5 = r2
            goto L43
        L42:
            r5 = r4
        L43:
            r6 = 1163450793(0x4558d9a9, float:3469.6038)
            r9.startReplaceGroup(r6)
            r6 = r0 & 112(0x70, float:1.57E-43)
            if (r6 != r3) goto L4f
            r3 = r2
            goto L50
        L4f:
            r3 = r4
        L50:
            r0 = r0 & 14
            if (r0 != r1) goto L55
            goto L56
        L55:
            r2 = r4
        L56:
            r0 = r3 | r2
            java.lang.Object r1 = r9.rememberedValue()
            if (r0 != 0) goto L67
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L70
        L67:
            com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1 r1 = new com.android.compose.animation.scene.PredictiveBackHandlerKt$PredictiveBackHandler$1$1
            r0 = 0
            r1.<init>(r8, r7, r0)
            r9.updateRememberedValue(r1)
        L70:
            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
            r9.end(r4)
            androidx.activity.compose.PredictiveBackHandlerKt.PredictiveBackHandler(r5, r1, r9, r4, r4)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L81
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L81:
            androidx.compose.runtime.RecomposeScopeImpl r9 = r9.endRestartGroup()
            if (r9 == 0) goto L8e
            com.android.compose.animation.scene.PredictiveBackHandlerKt$$ExternalSyntheticLambda0 r0 = new com.android.compose.animation.scene.PredictiveBackHandlerKt$$ExternalSyntheticLambda0
            r0.<init>(r8, r10)
            r9.block = r0
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.PredictiveBackHandlerKt.PredictiveBackHandler(com.android.compose.animation.scene.SceneTransitionLayoutImpl, com.android.compose.animation.scene.UserActionResult, androidx.compose.runtime.Composer, int):void");
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
        Object animateOffset$default = SwipeAnimation.animateOffset$default(swipeAnimation, 0.0f, contentKey, animationSpec, null, (SuspendLambda) continuation, 8);
        return animateOffset$default == CoroutineSingletons.COROUTINE_SUSPENDED ? animateOffset$default : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$startTransition(com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl r5, com.android.compose.animation.scene.SwipeAnimation r6, kotlinx.coroutines.Job r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            boolean r0 = r8 instanceof com.android.compose.animation.scene.PredictiveBackHandlerKt$startTransition$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.compose.animation.scene.PredictiveBackHandlerKt$startTransition$1 r0 = (com.android.compose.animation.scene.PredictiveBackHandlerKt$startTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.compose.animation.scene.PredictiveBackHandlerKt$startTransition$1 r0 = new com.android.compose.animation.scene.PredictiveBackHandlerKt$startTransition$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L35
            if (r2 != r4) goto L2d
            java.lang.Object r5 = r0.L$0
            r7 = r5
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L49
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.compose.animation.scene.content.state.TransitionState$Transition r6 = r6.contentTransition
            if (r6 == 0) goto L3d
            goto L3e
        L3d:
            r6 = r3
        L3e:
            r0.L$0 = r7
            r0.label = r4
            java.lang.Object r5 = r5.startTransition(r6, r4, r0)
            if (r5 != r1) goto L49
            return r1
        L49:
            boolean r5 = r7.isActive()
            if (r5 == 0) goto L52
            r7.cancel(r3)
        L52:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.PredictiveBackHandlerKt.access$startTransition(com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl, com.android.compose.animation.scene.SwipeAnimation, kotlinx.coroutines.Job, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
