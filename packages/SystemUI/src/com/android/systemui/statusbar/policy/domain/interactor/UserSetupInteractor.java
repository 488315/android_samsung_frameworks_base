package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserSetupInteractor {
    public final StateFlow isUserSetUp;

    public UserSetupInteractor(UserSetupRepository userSetupRepository) {
        this.isUserSetUp = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
    }
}
