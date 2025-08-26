package com.android.systemui.bixby2.controller.mediacontrol;

import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class PlayLastSongController_Factory implements Provider {
    private final Provider modeProvider;

    public PlayLastSongController_Factory(Provider provider) {
        this.modeProvider = provider;
    }

    public static PlayLastSongController_Factory create(javax.inject.Provider provider) {
        return new PlayLastSongController_Factory(Providers.asDaggerProvider(provider));
    }

    public static PlayLastSongController newInstance(int i) {
        return new PlayLastSongController(i);
    }

    public static PlayLastSongController_Factory create(Provider provider) {
        return new PlayLastSongController_Factory(provider);
    }

    @Override // javax.inject.Provider
    public PlayLastSongController get() {
        return newInstance(((Integer) this.modeProvider.get()).intValue());
    }
}
