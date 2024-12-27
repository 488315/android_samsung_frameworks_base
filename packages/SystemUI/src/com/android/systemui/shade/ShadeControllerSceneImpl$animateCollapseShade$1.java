package com.android.systemui.shade;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.TransitionKeys;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class ShadeControllerSceneImpl$animateCollapseShade$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShadeControllerSceneImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeControllerSceneImpl$animateCollapseShade$1(ShadeControllerSceneImpl shadeControllerSceneImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeControllerSceneImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShadeControllerSceneImpl$animateCollapseShade$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeControllerSceneImpl$animateCollapseShade$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(125L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ShadeControllerSceneImpl shadeControllerSceneImpl = this.this$0;
        shadeControllerSceneImpl.getClass();
        SceneKey sceneKey = SceneFamilies.Home;
        TransitionKeys.INSTANCE.getClass();
        SceneInteractor.changeScene$default(shadeControllerSceneImpl.sceneInteractor, sceneKey, "ShadeController.animateCollapseShade", TransitionKeys.SlightlyFasterShadeCollapse, 8);
        return Unit.INSTANCE;
    }
}
