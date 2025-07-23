package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.education.AppToWebEducationController;
import com.android.wm.shell.desktopmode.education.AppToWebEducationFilter;
import com.android.wm.shell.desktopmode.education.data.AppToWebEducationDatastoreRepository;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.windowdecor.education.DesktopWindowingEducationPromoController;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.android.HandlerContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideAppToWebEducationControllerFactory implements Provider {
    public final Provider appToWebEducationDatastoreRepositoryProvider;
    public final Provider appToWebEducationFilterProvider;
    public final Provider applicationScopeProvider;
    public final Provider backgroundDispatcherProvider;
    public final Provider contextProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopWindowingEducationPromoControllerProvider;
    public final Provider windowDecorCaptionHandleRepositoryProvider;

    public WMShellModule_ProvideAppToWebEducationControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.appToWebEducationFilterProvider = provider2;
        this.appToWebEducationDatastoreRepositoryProvider = provider3;
        this.windowDecorCaptionHandleRepositoryProvider = provider4;
        this.desktopWindowingEducationPromoControllerProvider = provider5;
        this.applicationScopeProvider = provider6;
        this.backgroundDispatcherProvider = provider7;
        this.desktopStateProvider = provider8;
    }

    public static AppToWebEducationController provideAppToWebEducationController(Context context, AppToWebEducationFilter appToWebEducationFilter, AppToWebEducationDatastoreRepository appToWebEducationDatastoreRepository, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, DesktopWindowingEducationPromoController desktopWindowingEducationPromoController, CoroutineScope coroutineScope, HandlerContext handlerContext, DesktopState desktopState) {
        return new AppToWebEducationController(context, appToWebEducationFilter, appToWebEducationDatastoreRepository, windowDecorCaptionHandleRepository, desktopWindowingEducationPromoController, coroutineScope, handlerContext, desktopState);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AppToWebEducationController((Context) this.contextProvider.get(), (AppToWebEducationFilter) this.appToWebEducationFilterProvider.get(), (AppToWebEducationDatastoreRepository) this.appToWebEducationDatastoreRepositoryProvider.get(), (WindowDecorCaptionHandleRepository) this.windowDecorCaptionHandleRepositoryProvider.get(), (DesktopWindowingEducationPromoController) this.desktopWindowingEducationPromoControllerProvider.get(), (CoroutineScope) this.applicationScopeProvider.get(), (MainCoroutineDispatcher) this.backgroundDispatcherProvider.get(), (DesktopState) this.desktopStateProvider.get());
    }
}
