package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor$special$$inlined$map$3;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarUserChipViewModel {
    public final boolean chipEnabled;
    public final Flow isChipVisible;
    public final Function1 onClick;
    public final ChannelFlowTransformLatest userAvatar;
    public final ChannelFlowTransformLatest userCount;
    public final ChannelFlowTransformLatest userName;

    public StatusBarUserChipViewModel(final UserSwitcherInteractor userSwitcherInteractor) {
        boolean z = userSwitcherInteractor.isStatusBarUserChipEnabled;
        this.chipEnabled = z;
        this.isChipVisible = !z ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : FlowKt.mapLatest(userSwitcherInteractor.getUsers(), new StatusBarUserChipViewModel$isChipVisible$1(null));
        UserRepository userRepository = userSwitcherInteractor.repository;
        this.userName = FlowKt.mapLatest(new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userRepository).selectedUserInfo, userSwitcherInteractor), new StatusBarUserChipViewModel$userName$1(null));
        this.userAvatar = FlowKt.mapLatest(new UserSwitcherInteractor$special$$inlined$map$3(((UserRepositoryImpl) userRepository).selectedUserInfo, userSwitcherInteractor), new StatusBarUserChipViewModel$userAvatar$1(null));
        this.onClick = new Function1() { // from class: com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel$onClick$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                UserSwitcherInteractor.this.showUserSwitcher((Expandable) obj);
                return Unit.INSTANCE;
            }
        };
        this.userCount = FlowKt.mapLatest(userSwitcherInteractor.getUsers(), new StatusBarUserChipViewModel$userCount$1(null));
    }
}
