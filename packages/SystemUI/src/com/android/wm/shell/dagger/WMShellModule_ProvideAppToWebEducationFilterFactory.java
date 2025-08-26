package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.AppToWebEducationFilter;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppToWebEducationFilterFactory implements Provider {
    public final Provider appToWebEducationDatastoreRepositoryProvider;
    public final Provider contextProvider;

    public WMShellModule_ProvideAppToWebEducationFilterFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.appToWebEducationDatastoreRepositoryProvider = provider2;
    }

    public static AppToWebEducationFilter provideAppToWebEducationFilter(Context context, AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository) {
        return new AppToWebEducationFilter(context, appToWebEducationDatastoreRepository);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppToWebEducationFilter((Context) this.contextProvider.get(), (AppToWebEducationDatastoreRepository) this.appToWebEducationDatastoreRepositoryProvider.get());
    }
}
