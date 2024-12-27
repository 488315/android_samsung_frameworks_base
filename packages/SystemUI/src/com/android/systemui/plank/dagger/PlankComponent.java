package com.android.systemui.plank.dagger;

import dagger.Lazy;

public final class PlankComponent {
    public final boolean featureEnabled;
    public final Lazy lazyProtocolManager;

    public PlankComponent(boolean z, Lazy lazy) {
        this.featureEnabled = z;
        this.lazyProtocolManager = lazy;
    }
}
