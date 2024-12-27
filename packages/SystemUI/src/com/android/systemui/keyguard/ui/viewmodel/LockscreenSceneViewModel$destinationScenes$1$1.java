package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.shade.shared.model.ShadeMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class LockscreenSceneViewModel$destinationScenes$1$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ LockscreenSceneViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenSceneViewModel$destinationScenes$1$1(LockscreenSceneViewModel lockscreenSceneViewModel, Continuation continuation) {
        super(4, continuation);
        this.this$0 = lockscreenSceneViewModel;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        LockscreenSceneViewModel$destinationScenes$1$1 lockscreenSceneViewModel$destinationScenes$1$1 = new LockscreenSceneViewModel$destinationScenes$1$1(this.this$0, (Continuation) obj4);
        lockscreenSceneViewModel$destinationScenes$1$1.Z$0 = booleanValue;
        lockscreenSceneViewModel$destinationScenes$1$1.Z$1 = booleanValue2;
        lockscreenSceneViewModel$destinationScenes$1$1.L$0 = (ShadeMode) obj3;
        return lockscreenSceneViewModel$destinationScenes$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        ShadeMode shadeMode = (ShadeMode) this.L$0;
        this.this$0.getClass();
        return LockscreenSceneViewModel.destinationScenes(z, z2, shadeMode);
    }
}
