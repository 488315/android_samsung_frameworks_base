package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class ShadeSceneViewModel$destinationScenes$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ ShadeSceneViewModel this$0;

    public ShadeSceneViewModel$destinationScenes$1(ShadeSceneViewModel shadeSceneViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = shadeSceneViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ShadeSceneViewModel$destinationScenes$1 shadeSceneViewModel$destinationScenes$1 = new ShadeSceneViewModel$destinationScenes$1(this.this$0, (Continuation) obj3);
        shadeSceneViewModel$destinationScenes$1.L$0 = (ShadeMode) obj;
        shadeSceneViewModel$destinationScenes$1.Z$0 = booleanValue;
        return shadeSceneViewModel$destinationScenes$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        boolean z = this.Z$0;
        this.this$0.getClass();
        return ShadeSceneViewModel.destinationScenes(shadeMode, z);
    }
}
