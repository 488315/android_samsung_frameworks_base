package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

final class UserSwitcherInteractor$users$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ UserSwitcherInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSwitcherInteractor$users$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = userSwitcherInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        UserSwitcherInteractor$users$1 userSwitcherInteractor$users$1 = new UserSwitcherInteractor$users$1(this.this$0, (Continuation) obj4);
        userSwitcherInteractor$users$1.L$0 = (List) obj;
        userSwitcherInteractor$users$1.L$1 = (UserInfo) obj2;
        userSwitcherInteractor$users$1.L$2 = (UserSwitcherSettingsModel) obj3;
        return userSwitcherInteractor$users$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            UserInfo userInfo = (UserInfo) this.L$1;
            UserSwitcherSettingsModel userSwitcherSettingsModel = (UserSwitcherSettingsModel) this.L$2;
            UserSwitcherInteractor userSwitcherInteractor = this.this$0;
            int i2 = userInfo.id;
            boolean z = userSwitcherSettingsModel.isUserSwitcherEnabled;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 1;
            obj = UserSwitcherInteractor.access$toUserModels(userSwitcherInteractor, list, i2, z, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
