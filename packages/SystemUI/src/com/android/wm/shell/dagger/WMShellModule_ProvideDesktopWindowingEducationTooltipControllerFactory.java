package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalSystemViewContainer;
import com.android.wm.shell.windowdecor.education.DesktopWindowingEducationTooltipController;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopWindowingEducationTooltipControllerFactory implements Provider {
    public final Provider additionalSystemViewContainerFactoryProvider;
    public final Provider contextProvider;
    public final Provider displayControllerProvider;

    public WMShellModule_ProvideDesktopWindowingEducationTooltipControllerFactory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.additionalSystemViewContainerFactoryProvider = provider2;
        this.displayControllerProvider = provider3;
    }

    public static DesktopWindowingEducationTooltipController provideDesktopWindowingEducationTooltipController(Context context, AdditionalSystemViewContainer.Factory factory, DisplayController displayController) {
        return new DesktopWindowingEducationTooltipController(context, factory, displayController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DesktopWindowingEducationTooltipController((Context) this.contextProvider.get(), (AdditionalSystemViewContainer.Factory) this.additionalSystemViewContainerFactoryProvider.get(), (DisplayController) this.displayControllerProvider.get());
    }
}
