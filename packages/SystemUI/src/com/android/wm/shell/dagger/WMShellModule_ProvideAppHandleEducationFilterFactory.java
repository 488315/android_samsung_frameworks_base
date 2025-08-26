package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.AppHandleEducationFilter;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppHandleEducationFilterFactory implements Provider {
    public final Provider appHandleEducationDatastoreRepositoryProvider;
    public final Provider contextProvider;

    public WMShellModule_ProvideAppHandleEducationFilterFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.appHandleEducationDatastoreRepositoryProvider = provider2;
    }

    public static AppHandleEducationFilter provideAppHandleEducationFilter(Context context, AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository) {
        return new AppHandleEducationFilter(context, appHandleEducationDatastoreRepository);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppHandleEducationFilter((Context) this.contextProvider.get(), (AppHandleEducationDatastoreRepository) this.appHandleEducationDatastoreRepositoryProvider.get());
    }
}
