package com.android.systemui.shade.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2 shadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2 = new ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2((Continuation) obj3);
        shadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        shadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return shadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0058, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1.toContent, r4) == false) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r6)
            goto L72
        Ld:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L15:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            java.lang.Object r1 = r5.L$1
            com.android.compose.animation.scene.ObservableTransitionState r1 = (com.android.compose.animation.scene.ObservableTransitionState) r1
            boolean r3 = r1 instanceof com.android.compose.animation.scene.ObservableTransitionState.Idle
            if (r3 == 0) goto L2c
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            goto L69
        L2c:
            boolean r3 = r1 instanceof com.android.compose.animation.scene.ObservableTransitionState.Transition
            if (r3 == 0) goto L75
            com.android.compose.animation.scene.ObservableTransitionState$Transition r1 = (com.android.compose.animation.scene.ObservableTransitionState.Transition) r1
            boolean r3 = r1.isInitiatedByUserInput
            if (r3 == 0) goto L62
            com.android.compose.animation.scene.ContentKey r3 = r1.fromContent
            com.android.compose.animation.scene.SceneKey r4 = com.android.systemui.scene.shared.model.Scenes.Shade
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L5a
            com.android.compose.animation.scene.ContentKey r3 = r1.toContent
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L5a
            com.android.compose.animation.scene.ContentKey r3 = r1.fromContent
            com.android.compose.animation.scene.SceneKey r4 = com.android.systemui.scene.shared.model.Scenes.QuickSettings
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L5a
            com.android.compose.animation.scene.ContentKey r3 = r1.toContent
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 == 0) goto L62
        L5a:
            kotlinx.coroutines.flow.Flow r1 = r1.isUserInputOngoing
            com.android.systemui.shade.domain.interactor.ShadeAnimationInteractorSceneContainerImpl$isAnyFlingAnimationRunning$lambda$3$$inlined$map$1 r3 = new com.android.systemui.shade.domain.interactor.ShadeAnimationInteractorSceneContainerImpl$isAnyFlingAnimationRunning$lambda$3$$inlined$map$1
            r3.<init>()
            goto L69
        L62:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
        L69:
            r5.label = r2
            java.lang.Object r5 = kotlinx.coroutines.flow.FlowKt.emitAll(r6, r3, r5)
            if (r5 != r0) goto L72
            return r0
        L72:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L75:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.ShadeAnimationInteractorSceneContainerImpl$special$$inlined$flatMapLatest$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
