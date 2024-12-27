package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor;
import com.android.systemui.scene.data.repository.SceneContainerRepository;
import com.android.systemui.scene.domain.resolver.SceneResolver;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.FlowKt;
import dagger.Lazy;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneInteractor {
    public final ReadonlyStateFlow currentScene;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final ReadonlyStateFlow isTransitionUserInputOngoing;
    public final ReadonlyStateFlow isVisible;
    public final SceneLogger logger;
    public final Set onSceneAboutToChangeListener = new LinkedHashSet();
    public final SceneContainerRepository repository;
    public final Lazy sceneFamilyResolvers;
    public final ReadonlyStateFlow transitionState;
    public final ReadonlyStateFlow transitioningTo;

    public SceneInteractor(CoroutineScope coroutineScope, SceneContainerRepository sceneContainerRepository, SceneLogger sceneLogger, Lazy lazy, DeviceUnlockedInteractor deviceUnlockedInteractor) {
        this.repository = sceneContainerRepository;
        this.logger = sceneLogger;
        this.sceneFamilyResolvers = lazy;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        StateFlow stateFlow = sceneContainerRepository.currentScene;
        Flow pairwiseBy = FlowKt.pairwiseBy(stateFlow, stateFlow.getValue(), new SceneInteractor$currentScene$1(this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.currentScene = kotlinx.coroutines.flow.FlowKt.stateIn(pairwiseBy, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), stateFlow.getValue());
        final ReadonlyStateFlow readonlyStateFlow = sceneContainerRepository.transitionState;
        this.transitionState = readonlyStateFlow;
        kotlinx.coroutines.flow.FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.scene.domain.interactor.SceneInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.ObservableTransitionState r5 = (com.android.compose.animation.scene.ObservableTransitionState) r5
                        boolean r6 = r5 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
                        r2 = 0
                        if (r6 == 0) goto L3c
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r5 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r5
                        goto L3d
                    L3c:
                        r5 = r2
                    L3d:
                        if (r5 == 0) goto L41
                        com.android.compose.animation.scene.SceneKey r2 = r5.toScene
                    L41:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
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
        this.isTransitionUserInputOngoing = kotlinx.coroutines.flow.FlowKt.stateIn(kotlinx.coroutines.flow.FlowKt.transformLatest(readonlyStateFlow, new SceneInteractor$special$$inlined$flatMapLatest$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        SceneInteractor$isVisible$1 sceneInteractor$isVisible$1 = new SceneInteractor$isVisible$1(this, null);
        ReadonlyStateFlow readonlyStateFlow2 = sceneContainerRepository.isVisible;
        StateFlowImpl stateFlowImpl = sceneContainerRepository.isRemoteUserInteractionOngoing;
        this.isVisible = kotlinx.coroutines.flow.FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, stateFlowImpl, sceneInteractor$isVisible$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((Boolean) readonlyStateFlow2.$$delegate_0.getValue()).booleanValue() || ((Boolean) stateFlowImpl.getValue()).booleanValue()));
    }

    public static void changeScene$default(SceneInteractor sceneInteractor, SceneKey sceneKey, String str, TransitionKey transitionKey, int i) {
        StateFlow resolvedScene;
        SceneKey sceneKey2;
        if ((i & 4) != 0) {
            transitionKey = null;
        }
        SceneKey sceneKey3 = (SceneKey) sceneInteractor.currentScene.$$delegate_0.getValue();
        SceneResolver sceneResolver = (SceneResolver) ((Map) sceneInteractor.sceneFamilyResolvers.get()).get(sceneKey);
        if (sceneResolver != null && (resolvedScene = sceneResolver.getResolvedScene()) != null && (sceneKey2 = (SceneKey) resolvedScene.getValue()) != null) {
            sceneKey = sceneKey2;
        }
        if (sceneInteractor.validateSceneChange(sceneKey3, sceneKey, str)) {
            sceneInteractor.logger.logSceneChangeRequested(sceneKey3, sceneKey, str, false);
            Iterator it = sceneInteractor.onSceneAboutToChangeListener.iterator();
            while (it.hasNext()) {
                ((LockscreenSceneTransitionInteractor) it.next()).getClass();
                sceneKey.equals(Scenes.Lockscreen);
            }
            sceneInteractor.repository.dataSource.changeScene(sceneKey, transitionKey);
        }
    }

    public final boolean isSceneInFamily(SceneKey sceneKey, SceneKey sceneKey2) {
        SceneResolver sceneResolver = (SceneResolver) ((Map) this.sceneFamilyResolvers.get()).get(sceneKey2);
        return sceneResolver != null && sceneResolver.includesScene(sceneKey);
    }

    public final SafeFlow resolveSceneFamily(SceneKey sceneKey) {
        return new SafeFlow(new SceneInteractor$resolveSceneFamily$1(this, sceneKey, null));
    }

    public final boolean validateSceneChange(SceneKey sceneKey, SceneKey sceneKey2, String str) {
        if (!this.repository.config.sceneKeys.contains(sceneKey2)) {
            return false;
        }
        ReadonlyStateFlow readonlyStateFlow = this.transitionState;
        Object value = readonlyStateFlow.$$delegate_0.getValue();
        ObservableTransitionState.Transition transition = value instanceof ObservableTransitionState.Transition ? (ObservableTransitionState.Transition) value : null;
        SceneKey sceneKey3 = transition != null ? transition.fromScene : null;
        SceneKey sceneKey4 = Scenes.Gone;
        boolean areEqual = Intrinsics.areEqual(sceneKey3, sceneKey4);
        if (!Intrinsics.areEqual(sceneKey2, sceneKey4) || areEqual || ((DeviceUnlockStatus) this.deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked) {
            return !Intrinsics.areEqual(sceneKey, sceneKey2);
        }
        throw new IllegalStateException(("Cannot change to the Gone scene while the device is locked and not currently transitioning from Gone. Current transition state is " + readonlyStateFlow.$$delegate_0.getValue() + ". Logging reason for scene change was: " + str).toString());
    }
}
