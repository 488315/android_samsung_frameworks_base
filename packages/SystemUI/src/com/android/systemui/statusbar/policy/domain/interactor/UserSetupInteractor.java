package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.statusbar.policy.data.repository.UserSetupRepository;
import com.android.systemui.statusbar.policy.data.repository.UserSetupRepositoryImpl;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UserSetupInteractor {
    public final StateFlow isUserSetUp;

    public UserSetupInteractor(UserSetupRepository userSetupRepository) {
        ReadonlyStateFlow readonlyStateFlow = ((UserSetupRepositoryImpl) userSetupRepository).isUserSetUp;
    }
}
