package com.android.bouncer.ui;

import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
public final class SettingsInteractor {
    public final ReadonlyStateFlow isShowLastPassword;

    public SettingsInteractor(SettingsRepository settingsRepository) {
        this.isShowLastPassword = ((SettingsRepositoryImpl) settingsRepository).isShowLastPassword;
    }
}
