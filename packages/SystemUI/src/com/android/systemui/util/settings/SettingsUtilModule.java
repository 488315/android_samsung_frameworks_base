package com.android.systemui.util.settings;

import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepositoryImpl;

public interface SettingsUtilModule {
    GlobalSettings bindsGlobalSettings(GlobalSettingsImpl globalSettingsImpl);

    SecureSettings bindsSecureSettings(SecureSettingsImpl secureSettingsImpl);

    SystemSettings bindsSystemSettings(SystemSettingsImpl systemSettingsImpl);

    UserAwareSecureSettingsRepository bindsUserAwareSecureSettingsRepository(UserAwareSecureSettingsRepositoryImpl userAwareSecureSettingsRepositoryImpl);
}
