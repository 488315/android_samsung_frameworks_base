package com.android.systemui;

import com.android.systemui.process.ProcessWrapper;
import dagger.internal.Provider;

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
