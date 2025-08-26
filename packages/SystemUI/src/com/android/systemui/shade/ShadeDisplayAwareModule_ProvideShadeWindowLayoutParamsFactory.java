package com.android.systemui.shade;

import android.content.Context;
import android.os.Binder;
import android.view.WindowManager;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ShadeDisplayAwareModule_ProvideShadeWindowLayoutParamsFactory implements Provider {
    public final Provider contextProvider;

    public ShadeDisplayAwareModule_ProvideShadeWindowLayoutParamsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static WindowManager.LayoutParams provideShadeWindowLayoutParams(Context context) {
        ShadeDisplayAwareModule.INSTANCE.getClass();
        ShadeWindowLayoutParams.INSTANCE.getClass();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2040, -2147221432, -3);
        layoutParams.token = new Binder();
        layoutParams.gravity = 48;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("NotificationShade");
        layoutParams.packageName = context.getPackageName();
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.privateFlags |= 512;
        return layoutParams;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShadeWindowLayoutParams((Context) this.contextProvider.get());
    }
}
