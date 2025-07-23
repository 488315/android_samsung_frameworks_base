package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.education.AppHandleEducationController;
import com.android.wm.shell.desktopmode.education.AppHandleEducationFilter;
import com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.windowdecor.education.DesktopWindowingEducationTooltipController;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.android.HandlerContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppHandleEducationControllerFactory implements Provider {
    public final Provider appHandleEducationDatastoreRepositoryProvider;
    public final Provider appHandleEducationFilterProvider;
    public final Provider applicationScopeProvider;
    public final Provider backgroundDispatcherProvider;
    public final Provider contextProvider;
    public final Provider desktopModeUiEventLoggerProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopWindowingEducationTooltipControllerProvider;
    public final Provider windowDecorCaptionHandleRepositoryProvider;

    public WMShellModule_ProvideAppHandleEducationControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.appHandleEducationFilterProvider = provider2;
        this.appHandleEducationDatastoreRepositoryProvider = provider3;
        this.windowDecorCaptionHandleRepositoryProvider = provider4;
        this.desktopWindowingEducationTooltipControllerProvider = provider5;
        this.applicationScopeProvider = provider6;
        this.backgroundDispatcherProvider = provider7;
        this.desktopModeUiEventLoggerProvider = provider8;
        this.desktopStateProvider = provider9;
    }

    public static AppHandleEducationController provideAppHandleEducationController(Context context, AppHandleEducationFilter appHandleEducationFilter, AppHandleEducationDatastoreRepository appHandleEducationDatastoreRepository, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopWindowingEducationTooltipController desktopWindowingEducationTooltipController, CoroutineScope coroutineScope, HandlerContext handlerContext, DesktopModeUiEventLogger desktopModeUiEventLogger, DesktopState desktopState) {
        return new AppHandleEducationController(context, appHandleEducationFilter, appHandleEducationDatastoreRepository, windowDecorCaptionHandleRepository, desktopWindowingEducationTooltipController, coroutineScope, handlerContext, desktopModeUiEventLogger, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppHandleEducationController((Context) this.contextProvider.get(), (AppHandleEducationFilter) this.appHandleEducationFilterProvider.get(), (AppHandleEducationDatastoreRepository) this.appHandleEducationDatastoreRepositoryProvider.get(), (WindowDecorCaptionHandleRepository) this.windowDecorCaptionHandleRepositoryProvider.get(), (DesktopWindowingEducationTooltipController) this.desktopWindowingEducationTooltipControllerProvider.get(), (CoroutineScope) this.applicationScopeProvider.get(), (MainCoroutineDispatcher) this.backgroundDispatcherProvider.get(), (DesktopModeUiEventLogger) this.desktopModeUiEventLoggerProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
