package com.android.bouncer.ui;

import com.android.systemui.shared.settings.data.repository.SystemSettingsRepository;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SettingsRepositoryImpl implements SettingsRepository {
    public final ReadonlyStateFlow isShowLastPassword;

    public SettingsRepositoryImpl(CoroutineScope coroutineScope, SystemSettingsRepository systemSettingsRepository) {
        Flow boolSetting = systemSettingsRepository.boolSetting("show_password", false);
        SharingStarted.Companion.getClass();
        this.isShowLastPassword = FlowKt.stateIn(boolSetting, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }
}
