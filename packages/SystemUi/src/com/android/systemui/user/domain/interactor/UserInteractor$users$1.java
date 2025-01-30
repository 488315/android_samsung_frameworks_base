package com.android.systemui.user.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor$users$1", m277f = "UserInteractor.kt", m278l = {143}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class UserInteractor$users$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$users$1(UserInteractor userInteractor, Continuation<? super UserInteractor$users$1> continuation) {
        super(4, continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        UserInteractor$users$1 userInteractor$users$1 = new UserInteractor$users$1(this.this$0, (Continuation) obj4);
        userInteractor$users$1.L$0 = (List) obj;
        userInteractor$users$1.L$1 = (UserInfo) obj2;
        userInteractor$users$1.L$2 = (UserSwitcherSettingsModel) obj3;
        return userInteractor$users$1.invokeSuspend(Unit.INSTANCE);
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
            UserInteractor userInteractor = this.this$0;
            int i2 = userInfo.id;
            boolean z = userSwitcherSettingsModel.isUserSwitcherEnabled;
            this.L$0 = null;
            this.L$1 = null;
            this.label = 1;
            obj = UserInteractor.access$toUserModels(userInteractor, list, i2, z, this);
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
