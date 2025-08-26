package com.android.systemui.util.settings;

/* loaded from: classes3.dex */
public interface SettingsUtilModule {
    GlobalSettings bindsGlobalSettings(GlobalSettingsImpl globalSettingsImpl);

    SecureSettings bindsSecureSettings(SecureSettingsImpl secureSettingsImpl);

    SystemSettings bindsSystemSettings(SystemSettingsImpl systemSettingsImpl);
}
