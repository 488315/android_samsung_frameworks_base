package com.android.systemui.user.data.repository;

import com.android.systemui.user.data.model.SelectedUserModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserRepositoryImpl$selectedUser$1$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserRepositoryImpl $this_run;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$selectedUser$1$2(UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$this_run = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserRepositoryImpl$selectedUser$1$2 userRepositoryImpl$selectedUser$1$2 = new UserRepositoryImpl$selectedUser$1$2(this.$this_run, continuation);
        userRepositoryImpl$selectedUser$1$2.L$0 = obj;
        return userRepositoryImpl$selectedUser$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$selectedUser$1$2) create((SelectedUserModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SelectedUserModel selectedUserModel = (SelectedUserModel) this.L$0;
        if (!selectedUserModel.userInfo.isGuest()) {
            this.$this_run.lastSelectedNonGuestUserId = selectedUserModel.userInfo.id;
        }
        return Unit.INSTANCE;
    }
}
