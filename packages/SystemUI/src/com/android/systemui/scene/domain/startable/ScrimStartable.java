package com.android.systemui.scene.domain.startable;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.BiometricUnlockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.BiometricUnlockMode;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.domain.startable.ScrimStartable;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractorPassThrough;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Collection;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class ScrimStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final DozeServiceHost dozeServiceHost;
    public final ScrimController scrimController;
    public final ScrimStartable$special$$inlined$map$1 scrimState;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;

    public final class Model {
        public final ScrimState scrimState;
        public final boolean unlocking;

        public Model(ScrimState scrimState, boolean z) {
            this.scrimState = scrimState;
            this.unlocking = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Model)) {
                return false;
            }
            Model model = (Model) obj;
            return this.scrimState == model.scrimState && this.unlocking == model.unlocking;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.unlocking) + (this.scrimState.hashCode() * 31);
        }

        public final String toString() {
            return "Model(scrimState=" + this.scrimState + ", unlocking=" + this.unlocking + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1] */
    public ScrimStartable(CoroutineScope coroutineScope, ScrimController scrimController, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, KeyguardInteractor keyguardInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, BiometricUnlockInteractor biometricUnlockInteractor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AlternateBouncerInteractor alternateBouncerInteractor, BrightnessMirrorShowingInteractorPassThrough brightnessMirrorShowingInteractorPassThrough, DozeServiceHost dozeServiceHost) {
        this.scrimController = scrimController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.dozeServiceHost = dozeServiceHost;
        ReadonlyStateFlow readonlyStateFlow = deviceEntryInteractor.isDeviceEntered;
        ReadonlyStateFlow readonlyStateFlow2 = sceneContainerOcclusionInteractor.invisibleDueToOcclusion;
        StateFlow stateFlow = sceneInteractor.currentScene;
        ReadonlyStateFlow readonlyStateFlow3 = keyguardInteractor.isDozing;
        ReadonlyStateFlow readonlyStateFlow4 = biometricUnlockInteractor.unlockState;
        ReadonlyStateFlow readonlyStateFlow5 = brightnessMirrorShowingInteractorPassThrough.isShowing;
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ScrimStartable$scrimState$1(this, null));
        final Flow[] flowArr = {readonlyStateFlow, readonlyStateFlow2, stateFlow, sceneInteractor.currentOverlays, sceneInteractor.transitionState, readonlyStateFlow3, keyguardInteractor.isDreaming, readonlyStateFlow4, readonlyStateFlow5, keyguardInteractor.isPulsing, flowConflatedCallbackFlow};
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1

            /* renamed from: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ ScrimStartable this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, ScrimStartable scrimStartable) {
                    super(3, continuation);
                    this.this$0 = scrimStartable;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:119:0x0226 A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:19:0x00ad  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x00db A[EDGE_INSN: B:31:0x00db->B:32:0x00dc BREAK  A[LOOP:0: B:25:0x00c1->B:124:?]] */
                /* JADX WARN: Removed duplicated region for block: B:38:0x00f0  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x00fb  */
                /* JADX WARN: Removed duplicated region for block: B:69:0x0159  */
                /* JADX WARN: Removed duplicated region for block: B:72:0x0167  */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    boolean z;
                    boolean z2;
                    ScrimStartable.Model model;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        boolean zBooleanValue = ((Boolean) objArr[0]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) objArr[1]).booleanValue();
                        SceneKey sceneKey = (SceneKey) objArr[2];
                        Set set = (Set) objArr[3];
                        ObservableTransitionState observableTransitionState = (ObservableTransitionState) objArr[4];
                        boolean zBooleanValue3 = ((Boolean) objArr[5]).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) objArr[6]).booleanValue();
                        BiometricUnlockModel biometricUnlockModel = (BiometricUnlockModel) objArr[7];
                        boolean zBooleanValue5 = ((Boolean) objArr[8]).booleanValue();
                        boolean zBooleanValue6 = ((Boolean) objArr[9]).booleanValue();
                        boolean zBooleanValue7 = ((Boolean) objArr[10]).booleanValue();
                        boolean zContains = set.contains(Overlays.Bouncer);
                        if (observableTransitionState instanceof ObservableTransitionState.Transition.ChangeScene) {
                            ObservableTransitionState.Transition.ChangeScene changeScene = (ObservableTransitionState.Transition.ChangeScene) observableTransitionState;
                            z = zContains;
                            if (Intrinsics.areEqual(changeScene.fromScene, Scenes.Lockscreen)) {
                                boolean z3 = Intrinsics.areEqual(changeScene.toScene, Scenes.Gone);
                                ScrimStartable scrimStartable = this.this$0;
                                int i2 = ScrimStartable.$r8$clinit;
                                scrimStartable.getClass();
                                if (ScrimStartable.isShade(sceneKey)) {
                                    Set<OverlayKey> set2 = set;
                                    if (!(set2 instanceof Collection) || !set2.isEmpty()) {
                                        for (OverlayKey overlayKey : set2) {
                                            this.this$0.getClass();
                                            if (ScrimStartable.isShade(overlayKey)) {
                                                z2 = true;
                                                break;
                                            }
                                        }
                                    }
                                    z2 = false;
                                    model = null;
                                    boolean zIsIdle$default = ObservableTransitionState.isIdle$default(observableTransitionState, Scenes.Communal, null, 2);
                                    boolean z4 = zBooleanValue && (biometricUnlockModel.mode == BiometricUnlockMode.WAKE_AND_UNLOCK || z3);
                                    if (!this.this$0.alternateBouncerInteractor.isVisibleState()) {
                                        ScrimStartable scrimStartable2 = this.this$0;
                                        if (z3) {
                                            scrimStartable2.statusBarKeyguardViewManager.onKeyguardFadedAway();
                                        } else {
                                            scrimStartable2.getClass();
                                        }
                                        this.this$0.getClass();
                                        if (!(observableTransitionState instanceof ObservableTransitionState.Idle)) {
                                            if (observableTransitionState instanceof ObservableTransitionState.Transition.ChangeScene) {
                                                ObservableTransitionState.Transition.ChangeScene changeScene2 = (ObservableTransitionState.Transition.ChangeScene) observableTransitionState;
                                                if (ScrimStartable.isShade(changeScene2.fromScene) || !ScrimStartable.isShade(changeScene2.toScene)) {
                                                    model = new ScrimStartable.Model(ScrimState.KEYGUARD, z4);
                                                }
                                            } else if (observableTransitionState instanceof ObservableTransitionState.Transition.ReplaceOverlay) {
                                                ObservableTransitionState.Transition.ReplaceOverlay replaceOverlay = (ObservableTransitionState.Transition.ReplaceOverlay) observableTransitionState;
                                                if (ScrimStartable.isShade(replaceOverlay.fromOverlay) || !ScrimStartable.isShade(replaceOverlay.toOverlay)) {
                                                }
                                            } else {
                                                if (!(observableTransitionState instanceof ObservableTransitionState.Transition.ShowOrHideOverlay)) {
                                                    throw new NoWhenBranchMatchedException();
                                                }
                                                ObservableTransitionState.Transition.ShowOrHideOverlay showOrHideOverlay = (ObservableTransitionState.Transition.ShowOrHideOverlay) observableTransitionState;
                                                if (ScrimStartable.isShade(showOrHideOverlay.fromContent) || !ScrimStartable.isShade(showOrHideOverlay.toContent)) {
                                                }
                                            }
                                        }
                                    } else if (z && !z4) {
                                        model = new ScrimStartable.Model(this.this$0.statusBarKeyguardViewManager.primaryBouncerNeedsScrimming() ? ScrimState.BOUNCER_SCRIMMED : ScrimState.BOUNCER, false);
                                    } else if (zBooleanValue5) {
                                        model = new ScrimStartable.Model(ScrimState.BRIGHTNESS_MIRROR, z4);
                                    } else if (z2 && !zBooleanValue) {
                                        model = new ScrimStartable.Model(ScrimState.SHADE_LOCKED, z4);
                                    } else if (zBooleanValue6) {
                                        model = new ScrimStartable.Model(ScrimState.PULSING, z4);
                                    } else if (zBooleanValue7) {
                                        model = new ScrimStartable.Model(ScrimState.OFF, z4);
                                    } else if (zBooleanValue3 && !z4) {
                                        ScrimStartable scrimStartable3 = this.this$0;
                                        if (z3) {
                                            scrimStartable3.statusBarKeyguardViewManager.onKeyguardFadedAway();
                                        } else {
                                            scrimStartable3.getClass();
                                        }
                                        model = new ScrimStartable.Model(ScrimState.AOD, false);
                                    } else if (zIsIdle$default) {
                                        model = (zBooleanValue || !zBooleanValue4 || z4) ? new ScrimStartable.Model(ScrimState.GLANCEABLE_HUB, z4) : new ScrimStartable.Model(ScrimState.GLANCEABLE_HUB_OVER_DREAM, false);
                                    } else if (!zBooleanValue && !z4 && !zBooleanValue2) {
                                        model = new ScrimStartable.Model(ScrimState.KEYGUARD, false);
                                    } else if (zBooleanValue || z4 || !zBooleanValue4) {
                                        ScrimStartable scrimStartable4 = this.this$0;
                                        if (ObservableTransitionState.isIdle$default(observableTransitionState, Scenes.Gone, null, 2)) {
                                            scrimStartable4.statusBarKeyguardViewManager.onKeyguardFadedAway();
                                        } else {
                                            scrimStartable4.getClass();
                                        }
                                        model = new ScrimStartable.Model(ScrimState.UNLOCKED, z4);
                                    } else {
                                        model = new ScrimStartable.Model(ScrimState.DREAMING, false);
                                    }
                                    this.label = 1;
                                    if (flowCollector.emit(model, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    z2 = true;
                                    model = null;
                                    boolean zIsIdle$default2 = ObservableTransitionState.isIdle$default(observableTransitionState, Scenes.Communal, null, 2);
                                    if (zBooleanValue) {
                                        if (!this.this$0.alternateBouncerInteractor.isVisibleState()) {
                                        }
                                        this.label = 1;
                                        if (flowCollector.emit(model, this) == coroutineSingletons) {
                                        }
                                    }
                                }
                            }
                        } else {
                            z = zContains;
                        }
                        ScrimStartable scrimStartable5 = this.this$0;
                        int i22 = ScrimStartable.$r8$clinit;
                        scrimStartable5.getClass();
                        if (ScrimStartable.isShade(sceneKey)) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        }, new ScrimStartable$scrimState$3(this, null));
        this.scrimState = new Flow() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1

            /* renamed from: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        ScrimStartable.Model model = (ScrimStartable.Model) obj;
                        ScrimState scrimState = model != null ? model.scrimState : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(scrimState, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public static boolean isShade(ContentKey contentKey) {
        return Intrinsics.areEqual(contentKey, Scenes.Shade) || Intrinsics.areEqual(contentKey, Scenes.QuickSettings) || Intrinsics.areEqual(contentKey, Overlays.NotificationsShade) || Intrinsics.areEqual(contentKey, Overlays.QuickSettingsShade);
    }

    public static /* synthetic */ void getScrimState$annotations() {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
