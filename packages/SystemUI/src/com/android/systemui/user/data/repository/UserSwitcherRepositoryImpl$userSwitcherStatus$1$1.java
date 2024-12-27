package com.android.systemui.user.data.repository;

import android.graphics.drawable.Drawable;
import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class UserSwitcherRepositoryImpl$userSwitcherStatus$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public UserSwitcherRepositoryImpl$userSwitcherStatus$1$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        UserSwitcherRepositoryImpl$userSwitcherStatus$1$1 userSwitcherRepositoryImpl$userSwitcherStatus$1$1 = new UserSwitcherRepositoryImpl$userSwitcherStatus$1$1((Continuation) obj3);
        userSwitcherRepositoryImpl$userSwitcherStatus$1$1.L$0 = (String) obj;
        userSwitcherRepositoryImpl$userSwitcherStatus$1$1.L$1 = (Pair) obj2;
        return userSwitcherRepositoryImpl$userSwitcherStatus$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        Pair pair = (Pair) this.L$1;
        return new UserSwitcherStatusModel.Enabled(str, (Drawable) pair.component1(), ((Boolean) pair.component2()).booleanValue());
    }
}
