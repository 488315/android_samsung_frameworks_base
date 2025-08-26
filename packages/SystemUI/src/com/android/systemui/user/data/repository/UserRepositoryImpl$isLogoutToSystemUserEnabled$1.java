package com.android.systemui.user.data.repository;

import com.android.systemui.R;
import com.android.systemui.user.data.model.SelectedUserModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
final class UserRepositoryImpl$isLogoutToSystemUserEnabled$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$isLogoutToSystemUserEnabled$1(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$isLogoutToSystemUserEnabled$1 userRepositoryImpl$isLogoutToSystemUserEnabled$1 = new UserRepositoryImpl$isLogoutToSystemUserEnabled$1(this.this$0, continuation);
        userRepositoryImpl$isLogoutToSystemUserEnabled$1.L$0 = obj;
        return userRepositoryImpl$isLogoutToSystemUserEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$isLogoutToSystemUserEnabled$1) create((SelectedUserModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SelectedUserModel selectedUserModel = (SelectedUserModel) this.L$0;
            UserRepositoryImpl userRepositoryImpl = this.this$0;
            this.label = 1;
            int i2 = UserRepositoryImpl.$r8$clinit;
            userRepositoryImpl.getClass();
            obj = BuildersKt.withContext(userRepositoryImpl.backgroundDispatcher, new UserRepositoryImpl$isEligibleForLogout$2(selectedUserModel, userRepositoryImpl, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return ((Boolean) obj).booleanValue() ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(this.this$0.resources.getBoolean(R.bool.config_userSwitchingMustGoThroughLoginScreen))) : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }
}
