package com.android.systemui;

import com.android.systemui.process.ProcessWrapper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SystemUISecondaryUserService_Factory implements Provider {
    public final Provider processWrapperProvider;

    public SystemUISecondaryUserService_Factory(Provider provider) {
        this.processWrapperProvider = provider;
    }

    public static SystemUISecondaryUserService newInstance(ProcessWrapper processWrapper) {
        return new SystemUISecondaryUserService(processWrapper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SystemUISecondaryUserService((ProcessWrapper) this.processWrapperProvider.get());
    }
}
