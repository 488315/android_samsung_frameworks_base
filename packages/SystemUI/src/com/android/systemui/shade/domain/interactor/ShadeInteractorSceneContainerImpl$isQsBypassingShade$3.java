package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ShadeInteractorSceneContainerImpl$isQsBypassingShade$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneInteractor $sceneInteractor;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeInteractorSceneContainerImpl$isQsBypassingShade$3(SceneInteractor sceneInteractor, Continuation continuation) {
        super(2, continuation);
        this.$sceneInteractor = sceneInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeInteractorSceneContainerImpl$isQsBypassingShade$3 shadeInteractorSceneContainerImpl$isQsBypassingShade$3 = new ShadeInteractorSceneContainerImpl$isQsBypassingShade$3(this.$sceneInteractor, continuation);
        shadeInteractorSceneContainerImpl$isQsBypassingShade$3.L$0 = obj;
        return shadeInteractorSceneContainerImpl$isQsBypassingShade$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeInteractorSceneContainerImpl$isQsBypassingShade$3) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        final SceneKey sceneKey = (SceneKey) pair.component1();
        final SceneKey sceneKey2 = (SceneKey) pair.component2();
        final ReadonlyStateFlow readonlyStateFlow = this.$sceneInteractor.transitionState;
        return FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ SceneKey $notificationsScene$inlined;
                public final /* synthetic */ SceneKey $quickSettingsScene$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SceneKey sceneKey, SceneKey sceneKey2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$quickSettingsScene$inlined = sceneKey;
                    this.$notificationsScene$inlined = sceneKey2;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.compose.animation.scene.ObservableTransitionState r6 = (com.android.compose.animation.scene.ObservableTransitionState) r6
                        boolean r7 = r6 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
                        r2 = 0
                        if (r7 == 0) goto L3a
                        goto L55
                    L3a:
                        boolean r7 = r6 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
                        if (r7 == 0) goto L67
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r6 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r6
                        com.android.compose.animation.scene.SceneKey r7 = r6.toScene
                        com.android.compose.animation.scene.SceneKey r4 = r5.$quickSettingsScene$inlined
                        boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r4)
                        if (r7 == 0) goto L55
                        com.android.compose.animation.scene.SceneKey r6 = r6.fromScene
                        com.android.compose.animation.scene.SceneKey r7 = r5.$notificationsScene$inlined
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r7)
                        if (r6 != 0) goto L55
                        r2 = r3
                    L55:
                        java.lang.Boolean r6 = java.lang.Boolean.valueOf(r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    L67:
                        kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
                        r5.<init>()
                        throw r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$isQsBypassingShade$3$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, sceneKey, sceneKey2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }
}
