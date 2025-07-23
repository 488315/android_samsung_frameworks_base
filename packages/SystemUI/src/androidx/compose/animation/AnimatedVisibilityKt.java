package androidx.compose.animation;

import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AnimatedVisibilityKt {
    /* JADX WARN: Code restructure failed: missing block: B:59:0x016f, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01fe, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0258, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L157;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedEnterExitImpl(final androidx.compose.animation.core.Transition r21, final kotlin.jvm.functions.Function1 r22, final androidx.compose.ui.Modifier r23, final androidx.compose.animation.EnterTransition r24, final androidx.compose.animation.ExitTransition r25, final kotlin.jvm.functions.Function2 r26, final kotlin.jvm.functions.Function3 r27, androidx.compose.runtime.Composer r28, final int r29, final int r30) {
        /*
            Method dump skipped, instructions count: 807
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedVisibilityKt.AnimatedEnterExitImpl(androidx.compose.animation.core.Transition, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedVisibility(boolean r20, androidx.compose.ui.Modifier r21, androidx.compose.animation.EnterTransition r22, androidx.compose.animation.ExitTransition r23, java.lang.String r24, final kotlin.jvm.functions.Function3 r25, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility(boolean, androidx.compose.ui.Modifier, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c2, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L69;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedVisibilityImpl(final androidx.compose.animation.core.Transition r16, final kotlin.jvm.functions.Function1 r17, final androidx.compose.ui.Modifier r18, final androidx.compose.animation.EnterTransition r19, final androidx.compose.animation.ExitTransition r20, final kotlin.jvm.functions.Function3 r21, androidx.compose.runtime.Composer r22, final int r23) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibilityImpl(androidx.compose.animation.core.Transition, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    public static final EnterExitState targetEnterExit(Transition transition, Function1 function1, Object obj, Composer composer) {
        EnterExitState enterExitState;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.targetEnterExit (AnimatedVisibility.kt:836)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startMovableGroup(-902032957, transition);
        boolean isSeeking = transition.isSeeking();
        TransitionState transitionState = transition.transitionState;
        if (isSeeking) {
            composerImpl.startReplaceGroup(2101770115);
            composerImpl.end(false);
            enterExitState = ((Boolean) function1.mo779invoke(obj)).booleanValue() ? EnterExitState.Visible : ((Boolean) function1.mo779invoke(transitionState.getCurrentState())).booleanValue() ? EnterExitState.PostExit : EnterExitState.PreEnter;
        } else {
            composerImpl.startReplaceGroup(2102044248);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MutableState mutableState = (MutableState) rememberedValue;
            if (((Boolean) function1.mo779invoke(transitionState.getCurrentState())).booleanValue()) {
                mutableState.setValue(Boolean.TRUE);
            }
            enterExitState = ((Boolean) function1.mo779invoke(obj)).booleanValue() ? EnterExitState.Visible : ((Boolean) mutableState.getValue()).booleanValue() ? EnterExitState.PostExit : EnterExitState.PreEnter;
            composerImpl.end(false);
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return enterExitState;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedVisibility(final androidx.compose.foundation.layout.RowScope r20, final boolean r21, androidx.compose.ui.Modifier r22, androidx.compose.animation.EnterTransition r23, androidx.compose.animation.ExitTransition r24, java.lang.String r25, final kotlin.jvm.functions.Function3 r26, androidx.compose.runtime.Composer r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility(androidx.compose.foundation.layout.RowScope, boolean, androidx.compose.ui.Modifier, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AnimatedVisibility(final androidx.compose.foundation.layout.ColumnScope r16, boolean r17, androidx.compose.ui.Modifier r18, androidx.compose.animation.EnterTransition r19, androidx.compose.animation.ExitTransition r20, java.lang.String r21, final kotlin.jvm.functions.Function3 r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.AnimatedVisibilityKt.AnimatedVisibility(androidx.compose.foundation.layout.ColumnScope, boolean, androidx.compose.ui.Modifier, androidx.compose.animation.EnterTransition, androidx.compose.animation.ExitTransition, java.lang.String, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }
}
