package com.android.server.location.injector;

public interface SettingsHelper$GlobalSettingChangedListener
        extends SettingsHelper$UserSettingChangedListener {
    void onSettingChanged();

    @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
    default void onSettingChanged(int i) {
        onSettingChanged();
    }
}
