package com.android.p038wm.shell.dagger;

import android.app.ActivityTaskManager;
import android.content.Context;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvidesSplitScreenControllerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider splitscreenControllerProvider;

    public WMShellBaseModule_ProvidesSplitScreenControllerFactory(Provider provider, Provider provider2) {
        this.splitscreenControllerProvider = provider;
        this.contextProvider = provider2;
    }

    public static Optional providesSplitScreenController(Context context, Optional optional) {
        if (!ActivityTaskManager.deviceSupportsMultiWindow(context) && !ActivityTaskManager.supportsSplitScreenMultiWindow(context)) {
            optional = Optional.empty();
        }
        Preconditions.checkNotNullFromProvides(optional);
        return optional;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesSplitScreenController((Context) this.contextProvider.get(), (Optional) this.splitscreenControllerProvider.get());
    }
}
