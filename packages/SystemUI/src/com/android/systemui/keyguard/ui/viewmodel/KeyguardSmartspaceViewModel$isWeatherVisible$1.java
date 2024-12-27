package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class KeyguardSmartspaceViewModel$isWeatherVisible$1 extends SuspendLambda implements Function3 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ KeyguardSmartspaceViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardSmartspaceViewModel$isWeatherVisible$1(KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardSmartspaceViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        KeyguardSmartspaceViewModel$isWeatherVisible$1 keyguardSmartspaceViewModel$isWeatherVisible$1 = new KeyguardSmartspaceViewModel$isWeatherVisible$1(this.this$0, (Continuation) obj3);
        keyguardSmartspaceViewModel$isWeatherVisible$1.Z$0 = booleanValue;
        keyguardSmartspaceViewModel$isWeatherVisible$1.Z$1 = booleanValue2;
        return keyguardSmartspaceViewModel$isWeatherVisible$1.invokeSuspend(Unit.INSTANCE);
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
        KeyguardSmartspaceViewModel keyguardSmartspaceViewModel = this.this$0;
        KeyguardSmartspaceViewModel.Companion companion = KeyguardSmartspaceViewModel.Companion;
        keyguardSmartspaceViewModel.getClass();
        return Boolean.valueOf(!z2 && z);
    }
}
