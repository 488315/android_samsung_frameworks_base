package com.android.systemui.plank.dagger;

import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PlankComponent {
    public final boolean featureEnabled;
    public final Lazy lazyProtocolManager;

    public PlankComponent(boolean z, Lazy lazy) {
        this.featureEnabled = z;
        this.lazyProtocolManager = lazy;
    }
}
