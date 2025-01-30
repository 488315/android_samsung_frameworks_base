package com.android.systemui.bixby2.controller.mediacontrol;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PlayLastSongController_Factory implements Provider {
    private final Provider modeProvider;

    public PlayLastSongController_Factory(Provider provider) {
        this.modeProvider = provider;
    }

    public static PlayLastSongController_Factory create(Provider provider) {
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
