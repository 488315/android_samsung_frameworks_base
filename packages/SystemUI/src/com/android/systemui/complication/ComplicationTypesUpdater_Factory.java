package com.android.systemui.complication;

import com.android.settingslib.dream.DreamBackend;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.shared.condition.Monitor;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComplicationTypesUpdater_Factory implements Provider {
    public final Provider dreamBackendProvider;
    public final Provider dreamOverlayStateControllerProvider;
    public final Provider executorProvider;
    public final Provider monitorProvider;
    public final Provider secureSettingsProvider;

    public ComplicationTypesUpdater_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.dreamBackendProvider = provider;
        this.executorProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.dreamOverlayStateControllerProvider = provider4;
        this.monitorProvider = provider5;
    }

    public static ComplicationTypesUpdater newInstance(DreamBackend dreamBackend, Executor executor, SecureSettings secureSettings, DreamOverlayStateController dreamOverlayStateController, Monitor monitor) {
        return new ComplicationTypesUpdater(dreamBackend, executor, secureSettings, dreamOverlayStateController, monitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ComplicationTypesUpdater((DreamBackend) this.dreamBackendProvider.get(), (Executor) this.executorProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), (Monitor) this.monitorProvider.get());
    }
}
