package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.ui.drawable.CircularDrawable;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3;
import com.android.systemui.user.shared.model.UserModel;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class UserSwitcherViewModel {
    public final StateFlowImpl _isMenuVisible;
    public final GuestUserInteractor guestUserInteractor;
    public final StateFlowImpl hasCancelButtonBeenClicked;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 isFinishRequested;
    public final StateFlowImpl isFinishRequiredDueToExecutedAction;
    public final StateFlowImpl isMenuVisible;
    public final UserSwitcherViewModel$special$$inlined$map$5 isOpenMenuButtonVisible;
    public final UserSwitcherViewModel$special$$inlined$map$3 maximumUserColumns;
    public final UserSwitcherViewModel$special$$inlined$map$4 menu;
    public final UserSwitcherViewModel$special$$inlined$map$1 selectedUser;
    public final StateFlowImpl userSwitched;
    public final UserSwitcherInteractor userSwitcherInteractor;
    public final UserSwitcherViewModel$special$$inlined$map$2 users;

    public UserSwitcherViewModel(UserSwitcherInteractor userSwitcherInteractor, GuestUserInteractor guestUserInteractor) {
        this.userSwitcherInteractor = userSwitcherInteractor;
        this.guestUserInteractor = guestUserInteractor;
        this.selectedUser = new UserSwitcherViewModel$special$$inlined$map$1(new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userSwitcherInteractor.repository).selectedUserInfo, userSwitcherInteractor), this);
        UserSwitcherViewModel$special$$inlined$map$2 userSwitcherViewModel$special$$inlined$map$2 = new UserSwitcherViewModel$special$$inlined$map$2(userSwitcherInteractor.getUsers(), this);
        this.users = userSwitcherViewModel$special$$inlined$map$2;
        this.maximumUserColumns = new UserSwitcherViewModel$special$$inlined$map$3(userSwitcherViewModel$special$$inlined$map$2, this);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isMenuVisible = stateFlowImplMutableStateFlow;
        this.isMenuVisible = stateFlowImplMutableStateFlow;
        UserSwitcherViewModel$special$$inlined$map$4 userSwitcherViewModel$special$$inlined$map$4 = new UserSwitcherViewModel$special$$inlined$map$4(userSwitcherInteractor.getActions(), this);
        this.menu = userSwitcherViewModel$special$$inlined$map$4;
        this.isOpenMenuButtonVisible = new UserSwitcherViewModel$special$$inlined$map$5(userSwitcherViewModel$special$$inlined$map$4);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this.hasCancelButtonBeenClicked = stateFlowImplMutableStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this.isFinishRequiredDueToExecutedAction = stateFlowImplMutableStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this.userSwitched = stateFlowImplMutableStateFlow4;
        this.isFinishRequested = FlowKt.combine(stateFlowImplMutableStateFlow2, stateFlowImplMutableStateFlow3, stateFlowImplMutableStateFlow4, new UserSwitcherViewModel$createFinishRequestedFlow$1(null));
    }

    public static final UserViewModel access$toViewModel(UserSwitcherViewModel userSwitcherViewModel, UserModel userModel) {
        userSwitcherViewModel.getClass();
        int i = userModel.id;
        Text resource = (userModel.isGuest && userModel.isSelected) ? new Text.Resource(R.string.guest_exit_quick_settings_button) : userModel.name;
        CircularDrawable circularDrawable = new CircularDrawable(userModel.image);
        boolean z = userModel.isSelectable;
        return new UserViewModel(i, resource, circularDrawable, userModel.isSelected, z ? 1.0f : 0.38f, !z ? null : new UserSwitcherViewModel$$ExternalSyntheticLambda0(userSwitcherViewModel, userModel, 1));
    }
}
