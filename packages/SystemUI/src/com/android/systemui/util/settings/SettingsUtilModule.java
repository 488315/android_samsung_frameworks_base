package com.android.systemui.util.settings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SettingsUtilModule {
    GlobalSettings bindsGlobalSettings(GlobalSettingsImpl globalSettingsImpl);

    SecureSettings bindsSecureSettings(SecureSettingsImpl secureSettingsImpl);

    SystemSettings bindsSystemSettings(SystemSettingsImpl systemSettingsImpl);
}
