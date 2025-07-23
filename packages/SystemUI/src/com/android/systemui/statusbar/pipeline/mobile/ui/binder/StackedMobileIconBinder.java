package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelKairos;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackedMobileIconBinder {
    public static final StackedMobileIconBinder INSTANCE = new StackedMobileIconBinder();

    private StackedMobileIconBinder() {
    }

    public static SingleBindableStatusBarComposeIconView$Companion$withDefaultBinding$2 bind(SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView, MobileIconsViewModel mobileIconsViewModel, StackedMobileIconViewModelImpl.Factory factory, StackedMobileIconViewModelKairos.Factory factory2, KairosNetwork kairosNetwork) {
        SingleBindableStatusBarComposeIconView.Companion companion = SingleBindableStatusBarComposeIconView.Companion;
        StackedMobileIconBinder$$ExternalSyntheticLambda0 stackedMobileIconBinder$$ExternalSyntheticLambda0 = new StackedMobileIconBinder$$ExternalSyntheticLambda0(mobileIconsViewModel, 0);
        StackedMobileIconBinder$bind$2 stackedMobileIconBinder$bind$2 = new StackedMobileIconBinder$bind$2(singleBindableStatusBarComposeIconView, kairosNetwork, factory2, factory, null);
        companion.getClass();
        return SingleBindableStatusBarComposeIconView.Companion.withDefaultBinding(singleBindableStatusBarComposeIconView, stackedMobileIconBinder$$ExternalSyntheticLambda0, stackedMobileIconBinder$bind$2);
    }
}
