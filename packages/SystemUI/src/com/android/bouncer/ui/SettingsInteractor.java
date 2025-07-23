package com.android.bouncer.ui;

import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SettingsInteractor {
    public final ReadonlyStateFlow isShowLastPassword;

    public SettingsInteractor(SettingsRepository settingsRepository) {
        this.isShowLastPassword = ((SettingsRepositoryImpl) settingsRepository).isShowLastPassword;
    }
}
