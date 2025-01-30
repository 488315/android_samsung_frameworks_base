package com.android.systemui.statusbar.connectivity;

import android.os.Looper;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CallbackHandler_Factory implements Provider {
    public final Provider looperProvider;

    public CallbackHandler_Factory(Provider provider) {
        this.looperProvider = provider;
    }

    public static CallbackHandler newInstance(Looper looper) {
        return new CallbackHandler(looper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CallbackHandler((Looper) this.looperProvider.get());
    }
}
