package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DeviceEntryRepositoryImpl$isLockscreenEnabled$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryRepositoryImpl$isLockscreenEnabled$2(DeviceEntryRepositoryImpl deviceEntryRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryRepositoryImpl$isLockscreenEnabled$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryRepositoryImpl$isLockscreenEnabled$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(!this.this$0.lockPatternUtils.isLockScreenDisabled(((UserRepositoryImpl) this.this$0.userRepository).getSelectedUserInfo().id));
    }
}
