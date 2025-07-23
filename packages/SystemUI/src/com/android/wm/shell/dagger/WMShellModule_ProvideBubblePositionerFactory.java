package com.android.wm.shell.dagger;

import android.content.Context;
import android.view.WindowManager;
import com.android.wm.shell.bubbles.BubblePositioner;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideBubblePositionerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider windowManagerProvider;

    public WMShellModule_ProvideBubblePositionerFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
    }

    public static BubblePositioner provideBubblePositioner(Context context, WindowManager windowManager) {
        return new BubblePositioner(context, windowManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BubblePositioner((Context) this.contextProvider.get(), (WindowManager) this.windowManagerProvider.get());
    }
}
