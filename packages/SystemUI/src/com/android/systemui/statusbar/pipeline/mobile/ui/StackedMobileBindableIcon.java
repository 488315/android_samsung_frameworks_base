package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.kairos.KairosNetwork;
import com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon;
import com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator;
import com.android.systemui.statusbar.pipeline.mobile.ui.binder.StackedMobileIconBinder;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModelKairos;
import com.android.systemui.statusbar.pipeline.shared.ui.view.ModernStatusBarView;
import com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarComposeIconView;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StackedMobileBindableIcon implements BindableIcon {
    public final StackedMobileBindableIcon$initializer$1 initializer;
    public final String slot;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.pipeline.mobile.ui.StackedMobileBindableIcon$initializer$1] */
    public StackedMobileBindableIcon(Context context, final MobileIconsViewModel mobileIconsViewModel, final StackedMobileIconViewModelImpl.Factory factory, final StackedMobileIconViewModelKairos.Factory factory2, final KairosNetwork kairosNetwork) {
        this.slot = context.getString(17043299);
        this.initializer = new ModernStatusBarViewCreator() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.StackedMobileBindableIcon$initializer$1
            @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.ModernStatusBarViewCreator
            public final ModernStatusBarView createAndBind(Context context2) {
                SingleBindableStatusBarComposeIconView.Companion.getClass();
                final SingleBindableStatusBarComposeIconView singleBindableStatusBarComposeIconView = (SingleBindableStatusBarComposeIconView) LayoutInflater.from(context2).inflate(R.layout.bindable_status_bar_compose_icon, (ViewGroup) null);
                String str = StackedMobileBindableIcon.this.slot;
                final StackedMobileIconViewModelKairos.Factory factory3 = factory2;
                final KairosNetwork kairosNetwork2 = kairosNetwork;
                final MobileIconsViewModel mobileIconsViewModel2 = mobileIconsViewModel;
                final StackedMobileIconViewModelImpl.Factory factory4 = factory;
                singleBindableStatusBarComposeIconView.initView(str, new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.StackedMobileBindableIcon$initializer$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        StackedMobileIconBinder.INSTANCE.getClass();
                        return StackedMobileIconBinder.bind(SingleBindableStatusBarComposeIconView.this, mobileIconsViewModel2, factory4, factory3, kairosNetwork2);
                    }
                });
                return singleBindableStatusBarComposeIconView;
            }
        };
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final ModernStatusBarViewCreator getInitializer() {
        return this.initializer;
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final boolean getShouldBindIcon() {
        return false;
    }

    @Override // com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon
    public final String getSlot() {
        return this.slot;
    }
}
