package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$fetchUserSetupState$2", m277f = "UserSetupRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserSetupRepositoryImpl$fetchUserSetupState$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UserSetupRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSetupRepositoryImpl$fetchUserSetupState$2(UserSetupRepositoryImpl userSetupRepositoryImpl, Continuation<? super UserSetupRepositoryImpl$fetchUserSetupState$2> continuation) {
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
