package com.android.systemui.user.data.repository;

import com.android.systemui.user.data.model.SelectedUserModel;
import com.android.systemui.user.data.model.SelectionStatus;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class UserRepositoryImpl$isEligibleForLogout$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SelectedUserModel $this_isEligibleForLogout;
    int label;
    final /* synthetic */ UserRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserRepositoryImpl$isEligibleForLogout$2(SelectedUserModel selectedUserModel, UserRepositoryImpl userRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$this_isEligibleForLogout = selectedUserModel;
        this.this$0 = userRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserRepositoryImpl$isEligibleForLogout$2(this.$this_isEligibleForLogout, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserRepositoryImpl$isEligibleForLogout$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.$this_isEligibleForLogout.selectionStatus == SelectionStatus.SELECTION_COMPLETE && this.this$0.devicePolicyManager.getLogoutUser() != null);
    }
}
