package com.android.systemui.util.settings;

import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SettingsUtilModule {
    GlobalSettings bindsGlobalSettings(GlobalSettingsImpl globalSettingsImpl);

    SecureSettings bindsSecureSettings(SecureSettingsImpl secureSettingsImpl);

    SystemSettings bindsSystemSettings(SystemSettingsImpl systemSettingsImpl);

    UserAwareSecureSettingsRepository bindsUserAwareSecureSettingsRepository(UserAwareSecureSettingsRepositoryImpl userAwareSecureSettingsRepositoryImpl);
}
