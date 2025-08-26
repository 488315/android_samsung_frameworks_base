package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class UserLockedInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Flow currentUserUnlocked;
    public final UserRepository userRepository;

    public UserLockedInteractor(CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, SelectedUserInteractor selectedUserInteractor) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.currentUserUnlocked = LatestConflatedKt.flatMapLatestConflated(selectedUserInteractor.selectedUserInfo, new UserLockedInteractor$currentUserUnlocked$1(this, null));
    }
}
