package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppToWebEducationDatastoreRepositoryFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideAppToWebEducationDatastoreRepositoryFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static AppToWebEducationDatastoreRepository provideAppToWebEducationDatastoreRepository(Context context) {
        return new AppToWebEducationDatastoreRepository(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppToWebEducationDatastoreRepository((Context) this.contextProvider.get());
    }
}
