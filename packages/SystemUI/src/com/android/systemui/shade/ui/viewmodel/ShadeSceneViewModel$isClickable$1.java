package com.android.systemui.shade.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

final class ShadeSceneViewModel$isClickable$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ShadeSceneViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeSceneViewModel$isClickable$1(ShadeSceneViewModel shadeSceneViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shadeSceneViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ShadeSceneViewModel$isClickable$1 shadeSceneViewModel$isClickable$1 = new ShadeSceneViewModel$isClickable$1(this.this$0, continuation);
        shadeSceneViewModel$isClickable$1.L$0 = obj;
        return shadeSceneViewModel$isClickable$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShadeSceneViewModel$isClickable$1) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        return sceneKey != null ? this.this$0.sceneInteractor.resolveSceneFamily(sceneKey) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
    }
}
