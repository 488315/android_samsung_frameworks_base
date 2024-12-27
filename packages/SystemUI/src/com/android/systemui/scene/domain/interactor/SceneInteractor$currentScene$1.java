package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class SceneInteractor$currentScene$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneInteractor$currentScene$1(SceneInteractor sceneInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = sceneInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SceneInteractor$currentScene$1 sceneInteractor$currentScene$1 = new SceneInteractor$currentScene$1(this.this$0, (Continuation) obj3);
        sceneInteractor$currentScene$1.L$0 = (SceneKey) obj;
        sceneInteractor$currentScene$1.L$1 = (SceneKey) obj2;
        return sceneInteractor$currentScene$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        SceneKey sceneKey2 = (SceneKey) this.L$1;
        this.this$0.logger.logSceneChangeCommitted(sceneKey, sceneKey2);
        return sceneKey2;
    }
}
