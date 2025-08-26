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
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, readonlyStateFlow.$$delegate_0.getValue());
        this.transitionState = readonlyStateFlowStateIn;
        this.transitioningTo = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    ContentKey contentKey;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ObservableTransitionState observableTransitionState = (ObservableTransitionState) obj;
                        if (observableTransitionState instanceof ObservableTransitionState.Idle) {
                            contentKey = null;
                        } else {
                            if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            contentKey = ((ObservableTransitionState.Transition) observableTransitionState).toContent;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(contentKey, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.isTransitionUserInputOngoing = FlowKt.stateIn(FlowKt.transformLatest(readonlyStateFlowStateIn, new SceneInteractor$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
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
        boolean zContains = ((Set) sceneInteractor.currentOverlays.getValue()).contains(Overlays.Bouncer);
        boolean zAreEqual = Intrinsics.areEqual(sceneKey4, sceneKey3);
        SceneContainerRepository sceneContainerRepository = sceneInteractor.repository;
        if (zAreEqual && z) {
            SceneLogger sceneLogger = sceneInteractor.logger;
            sceneLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            SceneLogger$$ExternalSyntheticLambda0 sceneLogger$$ExternalSyntheticLambda0 = new SceneLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = sceneLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = sceneKey4.debugName;
            logMessageImpl.str2 = obj2 != null ? obj2.toString() : null;
            logBuffer.commit(logMessageObtain);
            Iterator it = sceneInteractor.onSceneAboutToChangeListener.iterator();
            while (it.hasNext()) {
                ((LockscreenSceneTransitionInteractor) ((OnSceneAboutToChangeListener) it.next())).onSceneAboutToChange(sceneKey4, obj2);
            }
            sceneContainerRepository.dataSource.freezeAndAnimateToCurrentState();
        }
        if (!sceneInteractor.validateSceneChange(sceneKey3, sceneKey4, str)) {
            if (zContains) {
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
        if (!zContains) {
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
        Object objPrevious;
        if (set.isEmpty()) {
            return sceneKey;
        }
        List list = this.allContentKeys;
        ListIterator listIterator = list.listIterator(list.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                objPrevious = null;
                break;
            }
            objPrevious = listIterator.previous();
            if (set.contains((ContentKey) objPrevious)) {
                break;
            }
        }
        if (objPrevious != null) {
            return (ContentKey) objPrevious;
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void snapToScene(SceneKey sceneKey, String str) {
        SceneKey sceneKey2;
        SceneKey sceneKey3 = (SceneKey) this.currentScene.getValue();
        SceneResolver sceneResolver = (SceneResolver) ((Map) this.sceneFamilyResolvers.get()).get(sceneKey);
        if (sceneResolver == null) {
            sceneKey2 = sceneKey;
        } else {
            if (HomeSceneFamilyResolver.homeScenes.contains(sceneKey3)) {
                return;
            }
            SceneKey sceneKey4 = (SceneKey) ((HomeSceneFamilyResolver) sceneResolver).resolvedScene.$$delegate_0.getValue();
            if (sceneKey4 != null) {
                sceneKey2 = sceneKey4;
            }
        }
        if (validateSceneChange(sceneKey3, sceneKey2, str)) {
            this.logger.logSceneChanged(sceneKey3, sceneKey2, null, str, true);
            this.repository.dataSource.snapToScene(sceneKey2);
            instantlyHideOverlay(Overlays.Bouncer, "Hiding on snapToScene for: (" + str + ")");
        }
    }

    public final boolean validateSceneChange(SceneKey sceneKey, SceneKey sceneKey2, String str) {
        ShadeModeInteractor shadeModeInteractor = this.shadeModeInteractor;
        if (shadeModeInteractor.isDualShade() && (Intrinsics.areEqual(sceneKey2, Scenes.Shade) || Intrinsics.areEqual(sceneKey2, Scenes.QuickSettings))) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Can't change scene to ", sceneKey2.debugName, " when dual shade is on!").toString());
        }
        if (shadeModeInteractor.isSplitShade() && Intrinsics.areEqual(sceneKey2, Scenes.QuickSettings)) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Can't change scene to ", sceneKey2.debugName, " in split shade mode!").toString());
        }
        boolean zAreEqual = Intrinsics.areEqual(sceneKey, sceneKey2);
        SceneLogger sceneLogger = this.logger;
        if (zAreEqual) {
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
        boolean zAreEqual2 = Intrinsics.areEqual(contentKey, sceneKey3);
        if (!Intrinsics.areEqual(sceneKey2, sceneKey3) || zAreEqual2 || ((DeviceUnlockStatus) ((DeviceUnlockedInteractor) this.deviceUnlockedInteractor.get()).deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked || !((Boolean) ((KeyguardEnabledInteractor) this.keyguardEnabledInteractor.get()).isKeyguardEnabled.$$delegate_0.getValue()).booleanValue()) {
            return true;
        }
        throw new IllegalStateException(("Cannot change to the Gone scene while the device is locked and not currently transitioning from Gone. Current transition state is " + readonlyStateFlow.$$delegate_0.getValue() + ". Logging reason for scene change was: " + str).toString());
    }
}
