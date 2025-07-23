package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.shared.model.UserModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherViewModel$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserSwitcherViewModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ UserSwitcherViewModel$$ExternalSyntheticLambda0(UserSwitcherViewModel userSwitcherViewModel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = userSwitcherViewModel;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$1;
        UserSwitcherViewModel userSwitcherViewModel = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                UserSwitcherInteractor userSwitcherInteractor = userSwitcherViewModel.userSwitcherInteractor;
                int i = UserSwitcherInteractor.$r8$clinit;
                UserActionModel userActionModel = (UserActionModel) obj;
                userSwitcherInteractor.executeAction(userActionModel, null);
                if (userActionModel != UserActionModel.ADD_USER) {
                    userSwitcherViewModel.isFinishRequiredDueToExecutedAction.updateState(null, Boolean.TRUE);
                }
                break;
            default:
                UserSwitcherInteractor userSwitcherInteractor2 = userSwitcherViewModel.userSwitcherInteractor;
                int i2 = ((UserModel) obj).id;
                int i3 = UserSwitcherInteractor.$r8$clinit;
                userSwitcherInteractor2.selectUser(i2, null);
                userSwitcherViewModel.userSwitched.updateState(null, Boolean.TRUE);
                break;
        }
        return Unit.INSTANCE;
    }
}
