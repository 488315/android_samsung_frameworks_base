package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppHandleEducationDatastoreRepositoryFactory implements Provider {
    public final Provider contextProvider;

    public WMShellModule_ProvideAppHandleEducationDatastoreRepositoryFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static AppHandleEducationDatastoreRepository provideAppHandleEducationDatastoreRepository(Context context) {
        return new AppHandleEducationDatastoreRepository(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppHandleEducationDatastoreRepository((Context) this.contextProvider.get());
    }
}
