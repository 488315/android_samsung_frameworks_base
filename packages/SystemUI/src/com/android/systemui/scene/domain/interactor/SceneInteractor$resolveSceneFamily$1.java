package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.resolver.SceneResolver;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

final class SceneInteractor$resolveSceneFamily$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SceneKey $sceneKey;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SceneInteractor this$0;

    public SceneInteractor$resolveSceneFamily$1(SceneInteractor sceneInteractor, SceneKey sceneKey, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sceneInteractor;
        this.$sceneKey = sceneKey;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SceneInteractor$resolveSceneFamily$1 sceneInteractor$resolveSceneFamily$1 = new SceneInteractor$resolveSceneFamily$1(this.this$0, this.$sceneKey, continuation);
        sceneInteractor$resolveSceneFamily$1.L$0 = obj;
        return sceneInteractor$resolveSceneFamily$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SceneInteractor$resolveSceneFamily$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            SceneResolver sceneResolver = (SceneResolver) ((Map) this.this$0.sceneFamilyResolvers.get()).get(this.$sceneKey);
            Flow resolvedScene = sceneResolver != null ? sceneResolver.getResolvedScene() : null;
            if (resolvedScene == null) {
                resolvedScene = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(this.$sceneKey);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, resolvedScene, flowCollector) == coroutineSingletons) {
                return coroutineSingletons;
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
