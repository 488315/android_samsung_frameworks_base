package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ SceneContainerOcclusionInteractor this$0;

    public SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1(SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = sceneContainerOcclusionInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1 sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1 = new SceneContainerOcclusionInteractor$invisibleDueToOcclusion$1(this.this$0, (Continuation) obj4);
        sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1.Z$0 = booleanValue;
        sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1.L$0 = (ObservableTransitionState) obj2;
        sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1.Z$1 = booleanValue2;
        return sceneContainerOcclusionInteractor$invisibleDueToOcclusion$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$0;
        boolean z2 = this.Z$1;
        this.this$0.getClass();
        return Boolean.valueOf(SceneContainerOcclusionInteractor.invisibleDueToOcclusion(z, observableTransitionState, z2));
    }
}
