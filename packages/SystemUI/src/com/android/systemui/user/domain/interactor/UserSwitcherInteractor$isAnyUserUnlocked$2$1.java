package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserSwitcherInteractor$isAnyUserUnlocked$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserInfo $user;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$isAnyUserUnlocked$2$1(UserSwitcherInteractor userSwitcherInteractor, UserInfo userInfo, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSwitcherInteractor;
        this.$user = userInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UserSwitcherInteractor$isAnyUserUnlocked$2$1(this.this$0, this.$user, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSwitcherInteractor$isAnyUserUnlocked$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.manager.isUserUnlocked(this.$user.getUserHandle()));
    }
}
