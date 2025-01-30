package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.freeform.FreeformComponents;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideFreeformComponentsFactory implements Provider {
    public final Provider contextProvider;
    public final Provider freeformComponentsProvider;

    public WMShellBaseModule_ProvideFreeformComponentsFactory(Provider provider, Provider provider2) {
        this.freeformComponentsProvider = provider;
        this.contextProvider = provider2;
    }

    public static Optional provideFreeformComponents(Context context, Optional optional) {
        if (!FreeformComponents.isFreeformEnabled(context)) {
            optional = Optional.empty();
        }
        Preconditions.checkNotNullFromProvides(optional);
        return optional;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideFreeformComponents((Context) this.contextProvider.get(), (Optional) this.freeformComponentsProvider.get());
    }
}
