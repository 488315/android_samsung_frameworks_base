package com.android.systemui.statusbar;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RemoteInputNotificationRebuilder_Factory implements Provider {
    public final Provider contextProvider;

    public RemoteInputNotificationRebuilder_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static RemoteInputNotificationRebuilder newInstance(Context context) {
        return new RemoteInputNotificationRebuilder(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new RemoteInputNotificationRebuilder((Context) this.contextProvider.get());
    }
}
