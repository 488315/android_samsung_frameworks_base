package com.android.systemui.bixby2.controller.mediacontrol;

import dagger.internal.Provider;

public final class PlayLastSongController_Factory implements Provider {
    private final javax.inject.Provider modeProvider;

    public PlayLastSongController_Factory(javax.inject.Provider provider) {
        this.modeProvider = provider;
    }

    public static PlayLastSongController_Factory create(javax.inject.Provider provider) {
        return new PlayLastSongController_Factory(provider);
    }

    public static PlayLastSongController newInstance(int i) {
        return new PlayLastSongController(i);
    }

    @Override // javax.inject.Provider
    public PlayLastSongController get() {
        return newInstance(((Integer) this.modeProvider.get()).intValue());
    }
}
