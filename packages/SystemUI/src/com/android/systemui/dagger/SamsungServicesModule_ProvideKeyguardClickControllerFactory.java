package com.android.systemui.dagger;

import com.android.systemui.keyguard.KeyguardClickControllerImpl;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideKeyguardClickControllerFactory implements Provider {
    public static KeyguardClickControllerImpl provideKeyguardClickController() {
        return new KeyguardClickControllerImpl();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new KeyguardClickControllerImpl();
    }
}
