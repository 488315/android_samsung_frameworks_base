package com.android.systemui.util;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SettingsHelper_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider dumpManagerProvider;

    public SettingsHelper_Factory(javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
    }

    public static SettingsHelper_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new SettingsHelper_Factory(provider, provider2);
    }

    public static SettingsHelper newInstance(Context context, DumpManager dumpManager) {
        return new SettingsHelper(context, dumpManager);
    }

    @Override // javax.inject.Provider
    public SettingsHelper get() {
        return newInstance((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}
