package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class UserSetupInteractor {
    public final StateFlow isUserSetUp;

    public UserSetupInteractor(UserSetupRepository userSetupRepository) {
        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
    }
}
