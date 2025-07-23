package com.android.systemui.plank.dagger;

import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PlankComponent {
    public final boolean featureEnabled;
    public final Lazy lazyProtocolManager;

    public PlankComponent(boolean z, Lazy lazy) {
        this.featureEnabled = z;
        this.lazyProtocolManager = lazy;
    }
}
