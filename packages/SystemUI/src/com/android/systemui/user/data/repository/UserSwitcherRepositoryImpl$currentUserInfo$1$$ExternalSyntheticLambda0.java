package com.android.systemui.user.data.repository;

import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserSwitcherRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ UserSwitcherRepositoryImpl$currentUserInfo$1$$ExternalSyntheticLambda0(UserSwitcherRepositoryImpl userSwitcherRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = userSwitcherRepositoryImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((UserInfoControllerImpl) this.f$0.userInfoController).removeCallback((UserSwitcherRepositoryImpl$currentUserInfo$1$listener$1) this.f$1);
                break;
            default:
                UserSwitcherController userSwitcherController = this.f$0.userSwitcherController;
                UserSwitcherInteractor.UserCallback userCallback = (UserSwitcherInteractor.UserCallback) userSwitcherController.callbackCompatMap.remove((UserSwitcherController.UserSwitchCallback) this.f$1);
                if (userCallback != null) {
                    userSwitcherController.getMUserSwitcherInteractor().removeCallback(userCallback);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
