package com.android.systemui.shade.domain.interactor;

import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes3.dex */
final class ShadeInteractorImpl$isExpandToQsEnabled$1 extends SuspendLambda implements Function6 {
    final /* synthetic */ UserSwitcherInteractor $userSwitcherInteractor;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShadeInteractorImpl$isExpandToQsEnabled$1(UserSwitcherInteractor userSwitcherInteractor, Continuation continuation) {
        super(6, continuation);
        this.$userSwitcherInteractor = userSwitcherInteractor;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        boolean zBooleanValue4 = ((Boolean) obj5).booleanValue();
        ShadeInteractorImpl$isExpandToQsEnabled$1 shadeInteractorImpl$isExpandToQsEnabled$1 = new ShadeInteractorImpl$isExpandToQsEnabled$1(this.$userSwitcherInteractor, (Continuation) obj6);
        shadeInteractorImpl$isExpandToQsEnabled$1.L$0 = (DisableFlagsModel) obj;
        shadeInteractorImpl$isExpandToQsEnabled$1.Z$0 = zBooleanValue;
        shadeInteractorImpl$isExpandToQsEnabled$1.Z$1 = zBooleanValue2;
        shadeInteractorImpl$isExpandToQsEnabled$1.Z$2 = zBooleanValue3;
        shadeInteractorImpl$isExpandToQsEnabled$1.Z$3 = zBooleanValue4;
        return shadeInteractorImpl$isExpandToQsEnabled$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) this.L$0;
        return Boolean.valueOf(this.Z$3 && (this.Z$2 || !((UserSwitcherSettingsModel) ((UserRepositoryImpl) this.$userSwitcherInteractor.repository)._userSwitcherSettings.$$delegate_0.getValue()).isSimpleUserSwitcher) && this.Z$0 && disableFlagsModel.isQuickSettingsEnabled() && !this.Z$1);
    }
}
