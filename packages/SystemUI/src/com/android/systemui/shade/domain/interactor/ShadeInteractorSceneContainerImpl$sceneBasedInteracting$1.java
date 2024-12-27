package com.android.systemui.shade.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneInteractor $sceneInteractor;
    final /* synthetic */ SceneKey $sceneKey;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1(SceneInteractor sceneInteractor, SceneKey sceneKey, Continuation continuation) {
        super(2, continuation);
        this.$sceneInteractor = sceneInteractor;
        this.$sceneKey = sceneKey;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1 shadeInteractorSceneContainerImpl$sceneBasedInteracting$1 = new ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1(this.$sceneInteractor, this.$sceneKey, continuation);
        shadeInteractorSceneContainerImpl$sceneBasedInteracting$1.L$0 = obj;
        return shadeInteractorSceneContainerImpl$sceneBasedInteracting$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1) create((ObservableTransitionState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        if (observableTransitionState instanceof ObservableTransitionState.Idle) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        if (!(observableTransitionState instanceof ObservableTransitionState.Transition)) {
            throw new NoWhenBranchMatchedException();
        }
        final SafeFlow resolveSceneFamily = this.$sceneInteractor.resolveSceneFamily(this.$sceneKey);
        return new Flow() { // from class: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ ObservableTransitionState $state$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ObservableTransitionState observableTransitionState) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$state$inlined = observableTransitionState;
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
                        boolean r0 = r6 instanceof com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        com.android.compose.animation.scene.ObservableTransitionState r6 = r4.$state$inlined
                        r2 = r6
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r2 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r2
                        boolean r2 = r2.isInitiatedByUserInput
                        if (r2 == 0) goto L54
                        r2 = r6
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r2 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r2
                        com.android.compose.animation.scene.SceneKey r2 = r2.toScene
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r5)
                        if (r2 != 0) goto L52
                        com.android.compose.animation.scene.ObservableTransitionState$Transition r6 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r6
                        com.android.compose.animation.scene.SceneKey r6 = r6.fromScene
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r5)
                        if (r5 == 0) goto L54
                    L52:
                        r5 = r3
                        goto L55
                    L54:
                        r5 = 0
                    L55:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeInteractorSceneContainerImpl$sceneBasedInteracting$1$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, observableTransitionState), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
