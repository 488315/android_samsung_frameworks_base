package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class StatusBarUserChipViewModel {
    public final UserSwitcherInteractor interactor;
    public final Flow isChipVisible;
    public final StatusBarUserChipViewModel$$ExternalSyntheticLambda0 onClick;
    public final ChannelFlowTransformLatest userAvatar;
    public final ChannelFlowTransformLatest userCount;
    public final ChannelFlowTransformLatest userName;

    public StatusBarUserChipViewModel(UserSwitcherInteractor userSwitcherInteractor) {
        this.interactor = userSwitcherInteractor;
        this.isChipVisible = !((UserRepositoryImpl) userSwitcherInteractor.repository).isStatusBarUserChipEnabled ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.mapLatest(userSwitcherInteractor.getUsers(), new StatusBarUserChipViewModel$isChipVisible$1(null));
        this.userName = FlowKt.mapLatest(new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userSwitcherInteractor.repository).selectedUserInfo, userSwitcherInteractor), new StatusBarUserChipViewModel$userName$1(null));
        this.userAvatar = FlowKt.mapLatest(new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userSwitcherInteractor.repository).selectedUserInfo, userSwitcherInteractor), new StatusBarUserChipViewModel$userAvatar$1(null));
        this.onClick = new StatusBarUserChipViewModel$$ExternalSyntheticLambda0(this);
        this.userCount = FlowKt.mapLatest(userSwitcherInteractor.getUsers(), new StatusBarUserChipViewModel$userCount$1(null));
    }
}
