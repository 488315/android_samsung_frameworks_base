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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;

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

    /* renamed from: com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$startTransition$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return MutableSceneTransitionLayoutStateImpl.this.startTransition(null, false, this);
        }
    }

    /* renamed from: com.android.compose.animation.scene.MutableSceneTransitionLayoutStateImpl$startTransitionImmediately$1, reason: invalid class name and case insensitive filesystem */
    final class C07681 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $chain;
        final /* synthetic */ TransitionState.Transition $transition;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07681(TransitionState.Transition transition, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$transition = transition;
            this.$chain = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MutableSceneTransitionLayoutStateImpl.this.new C07681(this.$transition, this.$chain, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07681) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = MutableSceneTransitionLayoutStateImpl.this;
                TransitionState.Transition transition = this.$transition;
                boolean z = this.$chain;
                this.label = 1;
                if (mutableSceneTransitionLayoutStateImpl.startTransition(transition, z, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public MutableSceneTransitionLayoutStateImpl(SceneKey sceneKey, MotionScheme motionScheme, SceneTransitions sceneTransitions, Set set, Function1 function1, Function1 function12, Function1 function13, Function2 function2, Function1 function14, Function1 function15, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        SceneTransitions sceneTransitions2;
        if ((i & 4) != 0) {
            SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = new SceneTransitionsBuilderImpl();
            Unit unit = Unit.INSTANCE;
            sceneTransitions2 = new SceneTransitions(sceneTransitionsBuilderImpl.transitionSpecs, sceneTransitionsBuilderImpl.interruptionHandler);
        } else {
            sceneTransitions2 = sceneTransitions;
        }
        this(sceneKey, motionScheme, sceneTransitions2, (i & 8) != 0 ? EmptySet.INSTANCE : set, (i & 16) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(0) : function1, (i & 32) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(1) : function12, (i & 64) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(1) : function13, (i & 128) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda2() : function2, (i & 256) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(2) : function14, (i & 512) != 0 ? new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(3) : function15, (i & 1024) != 0 ? false : z);
    }

    public final void checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        Thread threadCurrentThread = Thread.currentThread();
        if (threadCurrentThread == this.creationThread) {
            return;
        }
        throw new IllegalStateException(StringsKt__IndentKt.trimIndent("\n                    Only the original thread that created a SceneTransitionLayoutState can mutate it\n                      Expected: " + this.creationThread.getName() + "\n                      Current: " + threadCurrentThread.getName() + "\n                ").toString());
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
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.lang.Object, kotlin.Unit] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object startTransition(TransitionState.Transition transition, boolean z, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        ?? r8;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Log.i("SceneTransitionLayoutState", "startTransition(transition=" + transition + ", chain=" + z + ")");
                checkThread$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                TransitionState transitionState = getTransitionState();
                transition.currentSceneWhenTransitionStarted = transitionState.getCurrentScene();
                transition.currentOverlaysWhenTransitionStarted = transitionState.getCurrentOverlays();
                SceneTransitions sceneTransitions = this.transitions;
                TransitionKey key = transition.getKey();
                LinkedHashMap linkedHashMap = (LinkedHashMap) sceneTransitions.transitionCache;
                ContentKey contentKey = transition.fromContent;
                Object linkedHashMap2 = linkedHashMap.get(contentKey);
                if (linkedHashMap2 == null) {
                    linkedHashMap2 = new LinkedHashMap();
                    linkedHashMap.put(contentKey, linkedHashMap2);
                }
                Map map = (Map) linkedHashMap2;
                ContentKey contentKey2 = transition.toContent;
                Object linkedHashMap3 = map.get(contentKey2);
                if (linkedHashMap3 == null) {
                    linkedHashMap3 = new LinkedHashMap();
                    map.put(contentKey2, linkedHashMap3);
                }
                Map map2 = (Map) linkedHashMap3;
                Object objFindSpec = map2.get(key);
                if (objFindSpec == null) {
                    objFindSpec = sceneTransitions.findSpec(contentKey, contentKey2, key);
                    map2.put(key, objFindSpec);
                }
                TransitionSpecImpl transitionSpecImpl = (TransitionSpecImpl) objFindSpec;
                transition._cuj = transitionSpecImpl.cuj;
                transition.transformationSpec = (TransformationSpecImpl) transitionSpecImpl.transformationSpec.mo781invoke(transition);
                Function1 function1 = transitionSpecImpl.previewTransformationSpec;
                transition.previewTransformationSpec = function1 != null ? (TransformationSpecImpl) function1.mo781invoke(transition) : null;
                startTransitionInternal(transition, z);
                this.onTransitionStart.mo781invoke(transition);
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = transition;
                anonymousClass1.label = 1;
                this = this;
                if (transition.runInternal$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(anonymousClass1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                transition = (TransitionState.Transition) anonymousClass1.L$1;
                MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl = (MutableSceneTransitionLayoutStateImpl) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                r8 = mutableSceneTransitionLayoutStateImpl;
            }
            r8.finishTransition(transition);
            r8.onTransitionEnd.mo781invoke(transition);
            this = Unit.INSTANCE;
            return this;
        } catch (Throwable th) {
            this.finishTransition(transition);
            this.onTransitionEnd.mo781invoke(transition);
            throw th;
        }
    }

    public final StandaloneCoroutine startTransitionImmediately(CoroutineScope coroutineScope, TransitionState.Transition transition, boolean z) {
        return BuildersKt.launch$default(coroutineScope, null, CoroutineStart.UNDISPATCHED, new C07681(transition, z, null), 1);
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
                List transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = this.f$0.getTransitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                int size = transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.size();
                for (int i = 0; i < size; i++) {
                    TransitionState transitionState = (TransitionState) transitionStates$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.get(i);
                    if (transitionState instanceof TransitionState.Transition) {
                        List list = ((TransitionState.Transition) transitionState).transformationSpec.transformationMatchers;
                        int size2 = list.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            Transformation.Factory factory = ((TransformationMatcher) list.get(i2)).factory;
                            if ((factory instanceof SharedElementTransformation.Factory) && ((SharedElementTransformation.Factory) factory).elevateInContent != null) {
                                listBuilderCreateListBuilder.add(factory);
                            }
                        }
                    }
                }
                return listBuilderCreateListBuilder.build();
            }
        });
        this.finishedTransitions = new LinkedHashSet();
    }

    public static /* synthetic */ void getFinishedTransitions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout$annotations() {
    }
}
