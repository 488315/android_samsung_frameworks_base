package com.android.systemui.statusbar.policy.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class UserSetupRepositoryImpl$fetchUserSetupState$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSetupRepositoryImpl this$0;

    public UserSetupRepositoryImpl$fetchUserSetupState$2(UserSetupRepositoryImpl userSetupRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSetupRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSetupRepositoryImpl$fetchUserSetupState$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSetupRepositoryImpl$fetchUserSetupState$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(((DeviceProvisionedControllerImpl) this.this$0.deviceProvisionedController).isCurrentUserSetup());
    }
}
