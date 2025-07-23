package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
