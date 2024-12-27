package com.android.systemui.communal.data.repository;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.TransitionKey;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalSceneRepositoryImpl$changeScene$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneKey $toScene;
    final /* synthetic */ TransitionKey $transitionKey;
    int label;
    final /* synthetic */ CommunalSceneRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneRepositoryImpl$changeScene$1(CommunalSceneRepositoryImpl communalSceneRepositoryImpl, SceneKey sceneKey, TransitionKey transitionKey, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneRepositoryImpl;
        this.$toScene = sceneKey;
        this.$transitionKey = transitionKey;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneRepositoryImpl$changeScene$1(this.this$0, this.$toScene, this.$transitionKey, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneRepositoryImpl$changeScene$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.sceneDataSource.changeScene(this.$toScene, this.$transitionKey);
        return Unit.INSTANCE;
    }
}
