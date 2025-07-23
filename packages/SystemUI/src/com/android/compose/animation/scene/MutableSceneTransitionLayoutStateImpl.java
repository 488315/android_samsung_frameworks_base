package com.android.compose.animation.scene;

import android.util.Log;
import androidx.compose.material3.MotionScheme;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.Transformation;
import com.android.compose.animation.scene.transformation.TransformationMatcher;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableSceneTransitionLayoutStateImpl implements MutableSceneTransitionLayoutState {
    public Function1 canChangeScene;
    public Function1 canHideOverlay;
    public Function2 canReplaceOverlay;
    public Function1 canShowOverlay;
    public final Thread creationThread;
    public boolean deferTransitionProgress;
    public final Set finishedTransitions;
    public MotionScheme motionScheme;
    public Function1 onTransitionEnd;
    public Function1 onTransitionStart;
    public final State transformationFactoriesWithElevation$delegate;
    public final MutableState transitionStates$delegate;
    public SceneTransitions transitions;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MutableSceneTransitionLayoutStateImpl(com.android.compose.animation.scene.SceneKey r19, androidx.compose.material3.MotionScheme r20, com.android.compose.animation.scene.SceneTransitions r21, java.util.Set r22, kotlin.jvm.functions.Function1 r23, kotlin.jvm.functions.Function1 r24, kotlin.jvm.functions.Function1 r25, kotlin.jvm.functions.Function2 r26, kotlin.jvm.functions.Function1 r27, kotlin.jvm.functions.Function1 r28, boolean r29, int r30, kotlin.jvm.internal.DefaultConstructorMarker r31) {
        /*
            r18 = this;
            r0 = r30
            r1 = 1
            r2 = 0
            r3 = r0 & 4
            if (r3 == 0) goto L1a
            com.android.compose.animation.scene.SceneTransitionsBuilderImpl r3 = new com.android.compose.animation.scene.SceneTransitionsBuilderImpl
            r3.<init>()
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            com.android.compose.animation.scene.SceneTransitions r4 = new com.android.compose.animation.scene.SceneTransitions
            java.util.List r5 = r3.transitionSpecs
            com.android.compose.animation.scene.DefaultInterruptionHandler r3 = r3.interruptionHandler
            r4.<init>(r5, r3)
            r9 = r4
            goto L1c
        L1a:
            r9 = r21
        L1c:
            r3 = r0 & 8
            if (r3 == 0) goto L24
            kotlin.collections.EmptySet r3 = kotlin.collections.EmptySet.INSTANCE
            r10 = r3
            goto L26
        L24:
            r10 = r22
        L26:
            r3 = r0 & 16
            if (r3 == 0) goto L31
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0 r3 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0
            r3.<init>(r2)
            r11 = r3
            goto L33
        L31:
            r11 = r23
        L33:
            r3 = r0 & 32
            if (r3 == 0) goto L3e
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0 r3 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0
            r3.<init>(r1)
            r12 = r3
            goto L40
        L3e:
            r12 = r24
        L40:
            r3 = r0 & 64
            if (r3 == 0) goto L4b
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0 r3 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0
            r3.<init>(r1)
            r13 = r3
            goto L4d
        L4b:
            r13 = r25
        L4d:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L58
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda2 r1 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda2
            r1.<init>()
            r14 = r1
            goto L5a
        L58:
            r14 = r26
        L5a:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L66
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0 r1 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0
            r3 = 2
            r1.<init>(r3)
            r15 = r1
            goto L68
        L66:
            r15 = r27
        L68:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L75
            com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0 r1 = new com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0
            r3 = 3
            r1.<init>(r3)
            r16 = r1
            goto L77
        L75:
            r16 = r28
        L77:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L84
            r17 = r2
        L7d:
            r6 = r18
            r7 = r19
            r8 = r20
            goto L87
        L84:
            r17 = r29
            goto L7d
        L87:
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl.<init>(com.android.compose.animation.scene.SceneKey, androidx.compose.material3.MotionScheme, com.android.compose.animation.scene.SceneTransitions, java.util.Set, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    public final void checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        Thread currentThread = Thread.currentThread();
        if (currentThread == this.creationThread) {
            return;
        }
        throw new IllegalStateException(StringsKt__IndentKt.trimIndent("\n                    Only the original thread that created a SceneTransitionLayoutState can mutate it\n                      Expected: " + this.creationThread.getName() + "\n                      Current: " + currentThread.getName() + "\n                ").toString());
    }

    public final void finishTransition(TransitionState.Transition transition) {
        checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        if (this.finishedTransitions.contains(transition)) {
            return;
        }
        CoroutineScopeKt.cancel(transition.getCoroutineScope$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(), null);
        List transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        if (transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.contains(transition)) {
            Log.i("SceneTransitionLayoutState", "finishTransition(transition=" + transition + ")");
            List list = transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (!(((TransitionState) transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.get(i)) instanceof TransitionState.Transition)) {
                    throw new IllegalStateException("Check failed.");
                }
            }
            this.finishedTransitions.add(transition);
            if (this.finishedTransitions.size() != transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.size()) {
                return;
            }
            TransitionState transitionState = (TransitionState) CollectionsKt___CollectionsKt.last(transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                if (!CollectionsKt___CollectionsKt.contains(this.finishedTransitions, (TransitionState) transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.get(i2))) {
                    return;
                }
            }
            TransitionState.Idle idle = new TransitionState.Idle(transitionState.getCurrentScene(), transitionState.getCurrentOverlays());
            Log.i("SceneTransitionLayoutState", "all transitions finished. idle=" + idle);
            this.finishedTransitions.clear();
            ((SnapshotMutableStateImpl) this.transitionStates$delegate).setValue(Collections.singletonList(idle));
        }
    }

    public final SceneKey getCurrentScene() {
        return getTransitionState().getCurrentScene();
    }

    public final List getCurrentTransitions() {
        if (!(CollectionsKt___CollectionsKt.last(getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout()) instanceof TransitionState.Idle)) {
            return getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        }
        if (getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() == 1) {
            return EmptyList.INSTANCE;
        }
        throw new IllegalStateException("Check failed.");
    }

    public final TransitionState getTransitionState() {
        return (TransitionState) getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().get(CollectionsKt__CollectionsKt.getLastIndex(getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout()));
    }

    public final List getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        return (List) ((SnapshotMutableStateImpl) this.transitionStates$delegate).getValue();
    }

    public final void hideOverlay(OverlayKey overlayKey, CoroutineScope coroutineScope, TransitionKey transitionKey) {
        checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        TransitionState transitionState = getTransitionState();
        if (transitionState.getCurrentOverlays().contains(overlayKey)) {
            SceneKey currentScene = transitionState.getCurrentScene();
            if (transitionState instanceof TransitionState.Transition.ShowOrHideOverlay) {
                TransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (TransitionState.Transition.ShowOrHideOverlay) transitionState;
                if (Intrinsics.areEqual(showOrHideOverlay.overlay, overlayKey) && Intrinsics.areEqual(showOrHideOverlay.fromOrToScene, currentScene)) {
                    AnimateOverlayKt.showOrHideOverlay(coroutineScope, this, overlayKey, currentScene, false, transitionKey, showOrHideOverlay, Intrinsics.areEqual(overlayKey, showOrHideOverlay.toContent));
                    return;
                }
            }
            AnimateOverlayKt.showOrHideOverlay(coroutineScope, this, overlayKey, currentScene, false, transitionKey, null, false);
        }
    }

    public final boolean isElevationPossible$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(ContentKey contentKey, ElementKey elementKey) {
        State state = this.transformationFactoriesWithElevation$delegate;
        if (!((List) state.getValue()).isEmpty()) {
            List list = (List) state.getValue();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                SharedElementTransformation.Factory factory = (SharedElementTransformation.Factory) list.get(i);
                if (Intrinsics.areEqual(factory.elevateInContent, contentKey) && (elementKey == null || factory.matcher.matches(contentKey, elementKey))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object, kotlin.Unit] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startTransition(com.android.compose.animation.scene.content.state.TransitionState.Transition r9, boolean r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl.startTransition(com.android.compose.animation.scene.content.state.TransitionState$Transition, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final StandaloneCoroutine startTransitionImmediately(CoroutineScope coroutineScope, TransitionState.Transition transition, boolean z) {
        return BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1(this, transition, z, null), 1);
    }

    public final void startTransitionInternal(TransitionState.Transition transition, boolean z) {
        TransitionState transitionState = (TransitionState) CollectionsKt___CollectionsKt.last(getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout());
        boolean z2 = transitionState instanceof TransitionState.Idle;
        MutableState mutableState = this.transitionStates$delegate;
        if (z2) {
            if (getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() != 1) {
                throw new IllegalStateException("Check failed.");
            }
            ((SnapshotMutableStateImpl) mutableState).setValue(Collections.singletonList(transition));
            return;
        }
        if (!(transitionState instanceof TransitionState.Transition)) {
            throw new NoWhenBranchMatchedException();
        }
        ((TransitionState.Transition) transitionState).freezeAndAnimateToCurrentState();
        boolean z3 = getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() >= 100;
        if (z && !z3) {
            ((SnapshotMutableStateImpl) mutableState).setValue(CollectionsKt___CollectionsKt.plus(getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(), transition));
            return;
        }
        if (z3) {
            StringBuilder sb = new StringBuilder("Potential leak detected in SceneTransitionLayoutState!\n  Some transition(s) never called STLState.finishTransition().\n");
            sb.append("  Transitions (size=" + getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() + "):");
            sb.append('\n');
            List transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
            int size = transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.size();
            for (int i = 0; i < size; i++) {
                TransitionState.Transition transition2 = (TransitionState.Transition) ((TransitionState) transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.get(i));
                ContentKey contentKey = transition2.fromContent;
                sb.append("  [" + (this.finishedTransitions.contains(transition2) ? "x" : " ") + "] " + contentKey + " => " + transition2.toContent + " (" + transition2 + ")");
                sb.append('\n');
            }
            Log.wtf("SceneTransitionLayoutState", sb.toString());
        }
        List currentTransitions = getCurrentTransitions();
        int size2 = currentTransitions.size();
        for (int i2 = 0; i2 < size2; i2++) {
            finishTransition((TransitionState.Transition) currentTransitions.get(i2));
        }
        if (getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().size() != 1) {
            throw new IllegalStateException("Check failed.");
        }
        if (!(getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().get(0) instanceof TransitionState.Idle)) {
            throw new IllegalStateException("Check failed.");
        }
        ((SnapshotMutableStateImpl) mutableState).setValue(Collections.singletonList(transition));
    }

    public MutableSceneTransitionLayoutStateImpl(SceneKey sceneKey, MotionScheme motionScheme, SceneTransitions sceneTransitions, Set<OverlayKey> set, Function1 function1, Function1 function12, Function1 function13, Function2 function2, Function1 function14, Function1 function15, boolean z) {
        this.motionScheme = motionScheme;
        this.transitions = sceneTransitions;
        this.canChangeScene = function1;
        this.canShowOverlay = function12;
        this.canHideOverlay = function13;
        this.canReplaceOverlay = function2;
        this.onTransitionStart = function14;
        this.onTransitionEnd = function15;
        this.deferTransitionProgress = z;
        this.creationThread = Thread.currentThread();
        this.transitionStates$delegate = SnapshotStateKt.mutableStateOf$default(Collections.singletonList(new TransitionState.Idle(sceneKey, set)));
        this.transformationFactoriesWithElevation$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                List transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = MutableSceneTransitionLayoutStateImpl.this.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                int size = transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.size();
                for (int i = 0; i < size; i++) {
                    TransitionState transitionState = (TransitionState) transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.get(i);
                    if (transitionState instanceof TransitionState.Transition) {
                        List list = ((TransitionState.Transition) transitionState).transformationSpec.transformationMatchers;
                        int size2 = list.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            Transformation.Factory factory = ((TransformationMatcher) list.get(i2)).factory;
                            if ((factory instanceof SharedElementTransformation.Factory) && ((SharedElementTransformation.Factory) factory).elevateInContent != null) {
                                createListBuilder.add(factory);
                            }
                        }
                    }
                }
                return createListBuilder.build();
            }
        });
        this.finishedTransitions = new LinkedHashSet();
    }

    public static /* synthetic */ void getFinishedTransitions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout$annotations() {
    }
}
