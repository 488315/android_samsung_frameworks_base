package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
final class UserLockedInteractor$currentUserUnlocked$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserLockedInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserLockedInteractor$currentUserUnlocked$1(UserLockedInteractor userLockedInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userLockedInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserLockedInteractor$currentUserUnlocked$1 userLockedInteractor$currentUserUnlocked$1 = new UserLockedInteractor$currentUserUnlocked$1(this.this$0, continuation);
        userLockedInteractor$currentUserUnlocked$1.L$0 = obj;
        return userLockedInteractor$currentUserUnlocked$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserLockedInteractor$currentUserUnlocked$1) create((UserInfo) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        UserInfo userInfo = (UserInfo) this.L$0;
        UserLockedInteractor userLockedInteractor = this.this$0;
        return FlowKt.flowOn(((UserRepositoryImpl) userLockedInteractor.userRepository).isUserUnlocked(userInfo.getUserHandle()), userLockedInteractor.backgroundDispatcher);
    }
}
