package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.StatusIconContainerController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.shade.ShadeModule_Companion_ProvideStatusIconContainerControllerFactory */
/* loaded from: classes2.dex */
public final class C2463x492e7ba0 implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider indicatorCutoutUtilProvider;
    public final Provider indicatorGardenPresenterProvider;
    public final Provider indicatorScaleGardenerProvider;
    public final Provider statusIconContainerProvider;

    public C2463x492e7ba0(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.statusIconContainerProvider = provider;
        this.contextProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.indicatorScaleGardenerProvider = provider4;
        this.indicatorGardenPresenterProvider = provider5;
        this.indicatorCutoutUtilProvider = provider6;
    }

    public static StatusIconContainerController provideStatusIconContainerController(StatusIconContainer statusIconContainer, Context context, ConfigurationController configurationController, IndicatorScaleGardener indicatorScaleGardener, IndicatorGardenPresenter indicatorGardenPresenter, IndicatorCutoutUtil indicatorCutoutUtil) {
        ShadeModule.Companion.getClass();
        return new StatusIconContainerController(statusIconContainer, context, configurationController, indicatorScaleGardener, indicatorGardenPresenter, indicatorCutoutUtil);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatusIconContainerController((StatusIconContainer) this.statusIconContainerProvider.get(), (Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (IndicatorScaleGardener) this.indicatorScaleGardenerProvider.get(), (IndicatorGardenPresenter) this.indicatorGardenPresenterProvider.get(), (IndicatorCutoutUtil) this.indicatorCutoutUtilProvider.get());
    }
}
