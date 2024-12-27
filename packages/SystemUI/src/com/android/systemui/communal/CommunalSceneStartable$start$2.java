package com.android.systemui.communal;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalTransitionKeys;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class CommunalSceneStartable$start$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSceneStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneStartable$start$2(CommunalSceneStartable communalSceneStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSceneStartable$start$2 communalSceneStartable$start$2 = new CommunalSceneStartable$start$2(this.this$0, continuation);
        communalSceneStartable$start$2.L$0 = obj;
        return communalSceneStartable$start$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneStartable$start$2) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        CommunalSceneInteractor communalSceneInteractor = this.this$0.communalSceneInteractor;
        CommunalTransitionKeys.INSTANCE.getClass();
        ((CommunalSceneRepositoryImpl) communalSceneInteractor.communalSceneRepository).changeScene(sceneKey, CommunalTransitionKeys.SimpleFade);
        return Unit.INSTANCE;
    }
}
