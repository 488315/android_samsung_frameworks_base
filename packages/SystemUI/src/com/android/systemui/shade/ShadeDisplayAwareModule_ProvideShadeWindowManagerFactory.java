package com.android.systemui.shade;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadeWindowManagerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider defaultWindowManagerProvider;
    public final Provider windowManagerProvider;

    public ShadeDisplayAwareModule_ProvideShadeWindowManagerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.defaultWindowManagerProvider = provider;
        this.contextProvider = provider2;
        this.windowManagerProvider = provider3;
    }

    public static WindowManager provideShadeWindowManager(WindowManager windowManager, Context context, WindowManagerProvider windowManagerProvider) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        ShadeWindowGoesAround.INSTANCE.getClass();
        if (ShadeWindowGoesAround.FLAG.isTrue()) {
            ((WindowManagerProviderImpl) windowManagerProvider).getClass();
            windowManager = WindowManagerUtils.getWindowManager(context);
        }
        windowManager.getClass();
        return windowManager;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShadeWindowManager((WindowManager) this.defaultWindowManagerProvider.get(), (Context) this.contextProvider.get(), (WindowManagerProvider) this.windowManagerProvider.get());
    }
}
