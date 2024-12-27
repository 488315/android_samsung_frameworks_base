package com.android.systemui.scene.domain.startable;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.BiometricUnlockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

public final class ScrimStartable implements CoreStartable {
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

    public ScrimStartable(CoroutineScope coroutineScope, ScrimController scrimController, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, KeyguardInteractor keyguardInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, BiometricUnlockInteractor biometricUnlockInteractor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AlternateBouncerInteractor alternateBouncerInteractor, BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, DozeServiceHost dozeServiceHost) {
        this.scrimController = scrimController;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.dozeServiceHost = dozeServiceHost;
        MutableStateFlow mutableStateFlow = keyguardInteractor.isDreaming;
        ReadonlyStateFlow readonlyStateFlow = brightnessMirrorShowingInteractor.isShowing;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ScrimStartable$scrimState$1(this, null));
        final Flow[] flowArr = {deviceEntryInteractor.isDeviceEntered, sceneContainerOcclusionInteractor.invisibleDueToOcclusion, sceneInteractor.currentScene, sceneInteractor.transitionState, keyguardInteractor.isDozing, mutableStateFlow, biometricUnlockInteractor.unlockState, readonlyStateFlow, keyguardInteractor.isPulsing, conflatedCallbackFlow};
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1

            /* renamed from: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ ScrimStartable this$0;

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

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invokeSuspend(java.lang.Object r21) {
                    /*
                        Method dump skipped, instructions count: 458
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$combine$1.2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        }, new ScrimStartable$scrimState$3(this, null));
        new Flow() { // from class: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.scene.domain.startable.ScrimStartable$Model r5 = (com.android.systemui.scene.domain.startable.ScrimStartable.Model) r5
                        if (r5 == 0) goto L39
                        com.android.systemui.statusbar.phone.ScrimState r5 = r5.scrimState
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.startable.ScrimStartable$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public static final boolean access$isShade(ScrimStartable scrimStartable, SceneKey sceneKey) {
        scrimStartable.getClass();
        return Intrinsics.areEqual(sceneKey, Scenes.Shade) || Intrinsics.areEqual(sceneKey, Scenes.QuickSettings) || Intrinsics.areEqual(sceneKey, Scenes.NotificationsShade) || Intrinsics.areEqual(sceneKey, Scenes.QuickSettingsShade);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
    }

    public static /* synthetic */ void getScrimState$annotations() {
    }
}
