package com.android.systemui.statusbar.window;

import android.content.Context;
import android.content.res.Resources;
import android.view.IWindowManager;
import android.view.WindowManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.util.DesktopManager;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarWindowController_Factory implements Provider {
    public final Provider contentInsetsProvider;
    public final Provider contextProvider;
    public final Provider desktopManagerProvider;
    public final Provider fragmentServiceProvider;
    public final Provider iWindowManagerProvider;
    public final Provider indicatorCutoutUtilProvider;
    public final Provider mAODAmbientWallpaperHelperProvider;
    public final Provider mainExecutorProvider;
    public final Provider resourcesProvider;
    public final Provider statusBarWindowViewProvider;
    public final Provider unfoldTransitionProgressProvider;
    public final Provider windowManagerProvider;

    public StatusBarWindowController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.contextProvider = provider;
        this.statusBarWindowViewProvider = provider2;
        this.windowManagerProvider = provider3;
        this.iWindowManagerProvider = provider4;
        this.contentInsetsProvider = provider5;
        this.fragmentServiceProvider = provider6;
        this.resourcesProvider = provider7;
        this.unfoldTransitionProgressProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.desktopManagerProvider = provider10;
        this.indicatorCutoutUtilProvider = provider11;
        this.mAODAmbientWallpaperHelperProvider = provider12;
    }

    public static StatusBarWindowController newInstance(Context context, StatusBarWindowView statusBarWindowView, WindowManager windowManager, IWindowManager iWindowManager, StatusBarContentInsetsProvider statusBarContentInsetsProvider, FragmentService fragmentService, Resources resources, Optional optional, Executor executor, DesktopManager desktopManager, IndicatorCutoutUtil indicatorCutoutUtil) {
        return new StatusBarWindowController(context, statusBarWindowView, windowManager, iWindowManager, statusBarContentInsetsProvider, fragmentService, resources, optional, executor, desktopManager, indicatorCutoutUtil);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        StatusBarWindowController newInstance = newInstance((Context) this.contextProvider.get(), (StatusBarWindowView) this.statusBarWindowViewProvider.get(), (WindowManager) this.windowManagerProvider.get(), (IWindowManager) this.iWindowManagerProvider.get(), (StatusBarContentInsetsProvider) this.contentInsetsProvider.get(), (FragmentService) this.fragmentServiceProvider.get(), (Resources) this.resourcesProvider.get(), (Optional) this.unfoldTransitionProgressProvider.get(), (Executor) this.mainExecutorProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (IndicatorCutoutUtil) this.indicatorCutoutUtilProvider.get());
        return newInstance;
    }
}
