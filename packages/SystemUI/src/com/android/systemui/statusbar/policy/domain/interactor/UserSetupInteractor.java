package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class UserSetupInteractor {
    public final StateFlow isUserSetUp;

    public UserSetupInteractor(UserSetupRepository userSetupRepository) {
        this.isUserSetUp = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
    }
}
