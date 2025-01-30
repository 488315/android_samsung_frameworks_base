package com.android.systemui.statusbar;

import android.content.Context;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
