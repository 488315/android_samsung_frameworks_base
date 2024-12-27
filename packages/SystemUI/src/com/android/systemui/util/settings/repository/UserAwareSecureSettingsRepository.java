package com.android.systemui.util.settings.repository;

import kotlinx.coroutines.flow.Flow;

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
