package com.android.systemui.scene.domain.interactor;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor;
import com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.scene.data.repository.SceneContainerRepository;
import com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver;
import com.android.systemui.scene.domain.resolver.SceneResolver;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.shared.logger.SceneLogger$$ExternalSyntheticLambda0;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneInteractor {
    public final List allContentKeys;
    public final StateFlow currentOverlays;
    public final StateFlow currentScene;
    public final Lazy deviceUnlockedInteractor;
    public final DisabledContentInteractor disabledContentInteractor;
    public final StateFlowImpl isRemoteUserInteractionOngoing;
    public final StateFlowImpl isSceneContainerUserInputOngoing;
    public final ReadonlyStateFlow isTransitionUserInputOngoing;
    public final ReadonlyStateFlow isVisible;
    public final Lazy keyguardEnabledInteractor;
    public final SceneLogger logger;
    public final Set onSceneAboutToChangeListener = new LinkedHashSet();
    public final SceneContainerRepository repository;
    public final Lazy sceneFamilyResolvers;
    public final ShadeModeInteractor shadeModeInteractor;
    public final ReadonlyStateFlow topmostContent;
    public final ReadonlyStateFlow transitionState;
    public final ReadonlyStateFlow transitioningTo;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnSceneAboutToChangeListener {
    }

    public SceneInteractor(CoroutineScope coroutineScope, SceneContainerRepository sceneContainerRepository, SceneLogger sceneLogger, Lazy lazy, Lazy lazy2, Lazy lazy3, DisabledContentInteractor disabledContentInteractor, ShadeModeInteractor shadeModeInteractor) {
        this.repository = sceneContainerRepository;
        this.logger = sceneLogger;
        this.sceneFamilyResolvers = lazy;
        this.deviceUnlockedInteractor = lazy2;
        this.keyguardEnabledInteractor = lazy3;
        this.disabledContentInteractor = disabledContentInteractor;
        this.shadeModeInteractor = shadeModeInteractor;
        this.allContentKeys = sceneContainerRepository.allContentKeys;
        StateFlow stateFlow = sceneContainerRepository.currentScene;
        this.currentScene = stateFlow;
        StateFlow stateFlow2 = sceneContainerRepository.currentOverlays;
        this.currentOverlays = stateFlow2;
        SceneInteractor$transitionState$1 sceneInteractor$transitionState$1 = new SceneInteractor$transitionState$1(this, null);
        ReadonlyStateFlow readonlyStateFlow = sceneContainerRepository.transitionState;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlow, sceneInteractor$transitionState$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, readonlyStateFlow.$$delegate_0.getValue());
        this.transitionState = stateIn;
        this.transitioningTo = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.ObservableTransitionState r5 = (com.android.compose.animation.scene.ObservableTransitionState) r5
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
                        if (r6 == 0) goto L3a
                        r5 = 0
                        goto L42
                    L3a:
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
                        if (r6 == 0) goto L50
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r5 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r5
                        com.android.compose.animation.scene.ContentKey r5 = r5.toContent
                    L42:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    L50:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isTransitionUserInputOngoing = FlowKt.stateIn(FlowKt.transformLatest(stateIn, new SceneInteractor$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        SceneInteractor$isVisible$1 sceneInteractor$isVisible$1 = new SceneInteractor$isVisible$1(this, null);
        ReadonlyStateFlow readonlyStateFlow2 = sceneContainerRepository.isVisible;
        StateFlowImpl stateFlowImpl = sceneContainerRepository.isRemoteUserInputOngoing;
        StateFlowImpl stateFlowImpl2 = sceneContainerRepository.activeTransitionAnimationCount;
        this.isVisible = FlowKt.stateIn(FlowKt.combine(readonlyStateFlow2, stateFlowImpl, stateFlowImpl2, sceneInteractor$isVisible$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((Boolean) readonlyStateFlow2.$$delegate_0.getValue()).booleanValue() || ((Boolean) stateFlowImpl.getValue()).booleanValue() || ((Number) stateFlowImpl2.getValue()).intValue() > 0));
        this.isRemoteUserInteractionOngoing = stateFlowImpl;
        this.isSceneContainerUserInputOngoing = sceneContainerRepository.isSceneContainerUserInputOngoing;
        this.topmostContent = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlow, stateFlow2, new SceneInteractor$topmostContent$1(this)), coroutineScope, startedEagerly, determineTopmostContent((SceneKey) stateFlow.getValue(), (Set) stateFlow2.getValue()));
    }

    public static void changeScene$default(SceneInteractor sceneInteractor, SceneKey sceneKey, String str, TransitionKey transitionKey, Object obj, boolean z, int i) {
        ReadonlyStateFlow readonlyStateFlow;
        SceneKey sceneKey2;
        if ((i & 4) != 0) {
            transitionKey = null;
        }
        Object obj2 = (i & 8) != 0 ? null : obj;
        if ((i & 16) != 0) {
            z = false;
        }
        SceneKey sceneKey3 = (SceneKey) sceneInteractor.currentScene.getValue();
        SceneResolver sceneResolver = (SceneResolver) ((Map) sceneInteractor.sceneFamilyResolvers.get()).get(sceneKey);
        SceneKey sceneKey4 = (sceneResolver == null || (readonlyStateFlow = ((HomeSceneFamilyResolver) sceneResolver).resolvedScene) == null || (sceneKey2 = (SceneKey) readonlyStateFlow.$$delegate_0.getValue()) == null) ? sceneKey : sceneKey2;
        boolean contains = ((Set) sceneInteractor.currentOverlays.getValue()).contains(Overlays.Bouncer);
        boolean areEqual = Intrinsics.areEqual(sceneKey4, sceneKey3);
        SceneContainerRepository sceneContainerRepository = sceneInteractor.repository;
        if (areEqual && z) {
            SceneLogger sceneLogger = sceneInteractor.logger;
            sceneLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            SceneLogger$$ExternalSyntheticLambda0 sceneLogger$$ExternalSyntheticLambda0 = new SceneLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = sceneLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = sceneKey4.debugName;
            logMessageImpl.str2 = obj2 != null ? obj2.toString() : null;
            logBuffer.commit(obtain);
            Iterator it = sceneInteractor.onSceneAboutToChangeListener.iterator();
            while (it.hasNext()) {
                ((LockscreenSceneTransitionInteractor) ((OnSceneAboutToChangeListener) it.next())).onSceneAboutToChange(sceneKey4, obj2);
            }
            sceneContainerRepository.dataSource.freezeAndAnimateToCurrentState();
        }
        if (!sceneInteractor.validateSceneChange(sceneKey3, sceneKey4, str)) {
            if (contains) {
                sceneInteractor.hideOverlay(Overlays.Bouncer, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Scene change cancelled but hiding bouncer for: (", str, ")"), null);
                return;
            }
            return;
        }
        Iterator it2 = sceneInteractor.onSceneAboutToChangeListener.iterator();
        while (it2.hasNext()) {
            ((LockscreenSceneTransitionInteractor) ((OnSceneAboutToChangeListener) it2.next())).onSceneAboutToChange(sceneKey4, obj2);
        }
        sceneInteractor.logger.logSceneChanged(sceneKey3, sceneKey4, obj2, str, false);
        if (!contains) {
            sceneContainerRepository.dataSource.changeScene(sceneKey4, transitionKey);
        } else {
            sceneContainerRepository.dataSource.snapToScene(sceneKey4);
            sceneInteractor.hideOverlay(Overlays.Bouncer, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Hiding on changeScene for: (", str, ")"), null);
        }
    }

    public static boolean validateOverlayChange$default(SceneInteractor sceneInteractor, OverlayKey overlayKey, OverlayKey overlayKey2, String str, int i) {
        if ((i & 1) != 0) {
            overlayKey = null;
        }
        if ((i & 2) != 0) {
            overlayKey2 = null;
        }
        sceneInteractor.getClass();
        if (overlayKey == null && overlayKey2 == null) {
            throw new IllegalStateException(("No overlay key provided for requested change. Current transition state is " + sceneInteractor.transitionState.$$delegate_0.getValue() + ". Logging reason for overlay change was: " + str).toString());
        }
        if (!sceneInteractor.shadeModeInteractor.isDualShade() && (Intrinsics.areEqual(overlayKey2, Overlays.NotificationsShade) || Intrinsics.areEqual(overlayKey2, Overlays.QuickSettingsShade))) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Can't show overlay ", overlayKey2 != null ? overlayKey2.debugName : null, " when dual shade is off!").toString());
        }
        SceneLogger sceneLogger = sceneInteractor.logger;
        if (overlayKey2 != null && DisabledContentInteractor.isDisabled$default(sceneInteractor.disabledContentInteractor, overlayKey2)) {
            sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), overlayKey2.debugName, " is currently disabled"));
            return false;
        }
        if (overlayKey2 != null && overlayKey != null && overlayKey2.equals(overlayKey)) {
            sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, overlayKey.debugName + " is the same as " + overlayKey2.debugName);
            return false;
        }
        if (overlayKey2 != null && !((ArrayList) sceneInteractor.repository.allContentKeys).contains(overlayKey2)) {
            sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), overlayKey2.debugName, " is not in allContentKeys"));
            return false;
        }
        StateFlow stateFlow = sceneInteractor.currentOverlays;
        if (overlayKey != null && !((Set) stateFlow.getValue()).contains(overlayKey)) {
            sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), overlayKey.debugName, " is not a current overlay"));
            return false;
        }
        if (overlayKey2 != null && ((Set) stateFlow.getValue()).contains(overlayKey2)) {
            sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), overlayKey2.debugName, " is already a current overlay"));
            return false;
        }
        if (!Intrinsics.areEqual(overlayKey2, Overlays.Bouncer) || !Intrinsics.areEqual(sceneInteractor.currentScene.getValue(), Scenes.Gone)) {
            return true;
        }
        sceneLogger.logSceneChangeRejection(overlayKey, overlayKey2, str, "Cannot show Bouncer over Gone scene");
        return false;
    }

    public final ContentKey determineTopmostContent(SceneKey sceneKey, Set set) {
        Object obj;
        if (set.isEmpty()) {
            return sceneKey;
        }
        List list = this.allContentKeys;
        ListIterator listIterator = list.listIterator(list.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj = null;
                break;
            }
            obj = listIterator.previous();
            if (set.contains((ContentKey) obj)) {
                break;
            }
        }
        if (obj != null) {
            return (ContentKey) obj;
        }
        throw new IllegalStateException(("Could not find unknown content " + set + " in allContentKeys " + this.allContentKeys).toString());
    }

    public final void hideOverlay(OverlayKey overlayKey, String str, TransitionKey transitionKey) {
        if (validateOverlayChange$default(this, overlayKey, null, str, 2)) {
            SceneLogger.logOverlayChangeRequested$default(this.logger, overlayKey, null, str, 2);
            this.repository.dataSource.hideOverlay(overlayKey, transitionKey);
        }
    }

    public final void instantlyHideOverlay(OverlayKey overlayKey, String str) {
        if (validateOverlayChange$default(this, overlayKey, null, str, 2)) {
            SceneLogger.logOverlayChangeRequested$default(this.logger, overlayKey, null, str, 2);
            this.repository.dataSource.instantlyHideOverlay(overlayKey);
        }
    }

    public final void showOverlay(OverlayKey overlayKey, String str) {
        if (validateOverlayChange$default(this, null, overlayKey, str, 1)) {
            SceneLogger.logOverlayChangeRequested$default(this.logger, null, overlayKey, str, 1);
            this.repository.dataSource.showOverlay(overlayKey);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003a A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void snapToScene(com.android.compose.animation.scene.SceneKey r8, java.lang.String r9) {
        /*
            r7 = this;
            kotlinx.coroutines.flow.StateFlow r0 = r7.currentScene
            java.lang.Object r0 = r0.getValue()
            r2 = r0
            com.android.compose.animation.scene.SceneKey r2 = (com.android.compose.animation.scene.SceneKey) r2
            dagger.Lazy r0 = r7.sceneFamilyResolvers
            java.lang.Object r0 = r0.get()
            java.util.Map r0 = (java.util.Map) r0
            java.lang.Object r0 = r0.get(r8)
            com.android.systemui.scene.domain.resolver.SceneResolver r0 = (com.android.systemui.scene.domain.resolver.SceneResolver) r0
            if (r0 == 0) goto L33
            java.util.Set r1 = com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver.homeScenes
            boolean r1 = r1.contains(r2)
            if (r1 == 0) goto L22
            goto L3a
        L22:
            com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver r0 = (com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver) r0
            kotlinx.coroutines.flow.ReadonlyStateFlow r0 = r0.resolvedScene
            kotlinx.coroutines.flow.StateFlow r0 = r0.$$delegate_0
            java.lang.Object r0 = r0.getValue()
            com.android.compose.animation.scene.SceneKey r0 = (com.android.compose.animation.scene.SceneKey) r0
            if (r0 != 0) goto L31
            goto L33
        L31:
            r3 = r0
            goto L34
        L33:
            r3 = r8
        L34:
            boolean r8 = r7.validateSceneChange(r2, r3, r9)
            if (r8 != 0) goto L3b
        L3a:
            return
        L3b:
            r6 = 1
            com.android.systemui.scene.shared.logger.SceneLogger r1 = r7.logger
            r4 = 0
            r5 = r9
            r1.logSceneChanged(r2, r3, r4, r5, r6)
            com.android.systemui.scene.data.repository.SceneContainerRepository r8 = r7.repository
            com.android.systemui.scene.shared.model.SceneDataSource r8 = r8.dataSource
            r8.snapToScene(r3)
            com.android.compose.animation.scene.OverlayKey r8 = com.android.systemui.scene.shared.model.Overlays.Bouncer
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r0 = "Hiding on snapToScene for: ("
            r9.<init>(r0)
            r9.append(r5)
            java.lang.String r0 = ")"
            r9.append(r0)
            java.lang.String r9 = r9.toString()
            r7.instantlyHideOverlay(r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.interactor.SceneInteractor.snapToScene(com.android.compose.animation.scene.SceneKey, java.lang.String):void");
    }

    public final boolean validateSceneChange(SceneKey sceneKey, SceneKey sceneKey2, String str) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        if (shadeModeInteractor.isDualShade() && (Intrinsics.areEqual(sceneKey2, Scenes.Shade) || Intrinsics.areEqual(sceneKey2, Scenes.QuickSettings))) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Can't change scene to ", sceneKey2.debugName, " when dual shade is on!").toString());
        }
        if (shadeModeInteractor.isSplitShade() && Intrinsics.areEqual(sceneKey2, Scenes.QuickSettings)) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Can't change scene to ", sceneKey2.debugName, " in split shade mode!").toString());
        }
        boolean areEqual = Intrinsics.areEqual(sceneKey, sceneKey2);
        SceneLogger sceneLogger = this.logger;
        if (areEqual) {
            sceneLogger.logSceneChangeRejection(sceneKey, sceneKey2, str, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sceneKey.debugName, " is the same as ", sceneKey2.debugName));
            return false;
        }
        if (!((ArrayList) this.repository.allContentKeys).contains(sceneKey2)) {
            sceneLogger.logSceneChangeRejection(sceneKey, sceneKey2, str, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sceneKey2.debugName, " isn't present in allContentKeys"));
            return false;
        }
        if (DisabledContentInteractor.isDisabled$default(this.disabledContentInteractor, sceneKey2)) {
            sceneLogger.logSceneChangeRejection(sceneKey, sceneKey2, str, AbstractResolvableFuture$$ExternalSyntheticOutline0.m(sceneKey2.debugName, " is currently disabled"));
            return false;
        }
        ReadonlyStateFlow readonlyStateFlow = this.transitionState;
        Object value = readonlyStateFlow.$$delegate_0.getValue();
        ObservableTransitionState.Transition transition = value instanceof ObservableTransitionState.Transition ? (ObservableTransitionState.Transition) value : null;
        ContentKey contentKey = transition != null ? transition.fromContent : null;
        SceneKey sceneKey3 = Scenes.Gone;
        boolean areEqual2 = Intrinsics.areEqual(contentKey, sceneKey3);
        if (!Intrinsics.areEqual(sceneKey2, sceneKey3) || areEqual2 || ((DeviceUnlockStatus) ((DeviceUnlockedInteractor) this.deviceUnlockedInteractor.get()).deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked || !((Boolean) ((KeyguardEnabledInteractor) this.keyguardEnabledInteractor.get()).isKeyguardEnabled.$$delegate_0.getValue()).booleanValue()) {
            return true;
        }
        throw new IllegalStateException(("Cannot change to the Gone scene while the device is locked and not currently transitioning from Gone. Current transition state is " + readonlyStateFlow.$$delegate_0.getValue() + ". Logging reason for scene change was: " + str).toString());
    }
}
