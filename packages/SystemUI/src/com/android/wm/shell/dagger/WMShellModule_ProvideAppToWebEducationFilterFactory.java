package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.AppToWebEducationFilter;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
