package com.android.p038wm.shell.dagger;

import android.content.Context;
import android.view.WindowManager;
import com.android.p038wm.shell.bubbles.BubblePositioner;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
