package com.android.wm.shell.dagger;

import android.os.SystemProperties;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvidesOneHandedControllerFactory implements Provider {
    public final Provider oneHandedControllerProvider;

    public WMShellBaseModule_ProvidesOneHandedControllerFactory(Provider provider) {
        this.oneHandedControllerProvider = provider;
    }

    public static Optional providesOneHandedController(Optional optional) {
        if (!SystemProperties.getBoolean("ro.support_one_handed_mode", false)) {
            optional = Optional.empty();
        }
        Preconditions.checkNotNullFromProvides(optional);
        return optional;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesOneHandedController((Optional) this.oneHandedControllerProvider.get());
    }
}
