package com.android.systemui.util.settings.repository;

import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface UserAwareSecureSettingsRepository {
    static /* synthetic */ Flow boolSettingForActiveUser$default(UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: boolSettingForActiveUser");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return userAwareSecureSettingsRepository.boolSettingForActiveUser(str, z);
    }

    Flow boolSettingForActiveUser(String str, boolean z);
}
