package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneContainerOcclusionInteractor {
    public final ReadonlyStateFlow invisibleDueToOcclusion;
    public final ReadonlyStateFlow isAodFullyOrPartiallyShown;
    public final ReadonlyStateFlow isOccludingActivityShown;

    public SceneContainerOcclusionInteractor(CoroutineScope coroutineScope, KeyguardOcclusionInteractor keyguardOcclusionInteractor, SceneInteractor sceneInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        KeyguardOcclusionInteractor$special$$inlined$map$1 keyguardOcclusionInteractor$special$$inlined$map$1 = keyguardOcclusionInteractor.isShowWhenLockedActivityOnTop;
        SharingStarted.Companion companion = SharingStarted.Companion;
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(keyguardOcclusionInteractor$special$$inlined$map$1, coroutineScope, WhileSubscribed$default, bool);
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SceneContainerOcclusionInteractor$isAodFullyOrPartiallyShown$1(null), keyguardTransitionInteractor.transitionValue(KeyguardState.AOD));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        float r5 = r5.floatValue()
                        r6 = 0
                        int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                        if (r5 <= 0) goto L3f
                        r5 = r3
                        goto L40
                    L3f:
                        r5 = 0
                    L40:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1 sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1 = new SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1(this, null);
        ReadonlyStateFlow readonlyStateFlow = sceneInteractor.transitionState;
        this.invisibleDueToOcclusion = FlowKt.stateIn(FlowKt.combine(stateIn, readonlyStateFlow, stateIn2, sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(invisibleDueToOcclusion(((Boolean) stateIn.$$delegate_0.getValue()).booleanValue(), (ObservableTransitionState) readonlyStateFlow.$$delegate_0.getValue(), ((Boolean) stateIn2.$$delegate_0.getValue()).booleanValue())));
    }

    public static boolean getCanBeOccluded(SceneKey sceneKey) {
        boolean z = true;
        if (!Intrinsics.areEqual(sceneKey, Scenes.Bouncer) && !Intrinsics.areEqual(sceneKey, Scenes.Communal) && !Intrinsics.areEqual(sceneKey, Scenes.Gone) && !Intrinsics.areEqual(sceneKey, Scenes.Lockscreen)) {
            z = false;
            if (!Intrinsics.areEqual(sceneKey, Scenes.NotificationsShade) && !Intrinsics.areEqual(sceneKey, Scenes.QuickSettings) && !Intrinsics.areEqual(sceneKey, Scenes.QuickSettingsShade) && !Intrinsics.areEqual(sceneKey, Scenes.Shade)) {
                throw new IllegalStateException(("SceneKey \"" + sceneKey + "\" doesn't have a mapping for canBeOccluded!").toString());
            }
        }
        return z;
    }

    public static boolean invisibleDueToOcclusion(boolean z, ObservableTransitionState observableTransitionState, boolean z2) {
        boolean z3;
        if (!z || z2) {
            return false;
        }
        if (observableTransitionState instanceof ObservableTransitionState.Idle) {
            z3 = getCanBeOccluded(((ObservableTransitionState.Idle) observableTransitionState).currentScene);
        } else {
            if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
                throw new NoWhenBranchMatchedException();
            }
            ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
            z3 = getCanBeOccluded(transition.fromScene) && getCanBeOccluded(transition.toScene);
        }
        return z3;
    }
}
