package com.android.systemui.user.domain.interactor;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
