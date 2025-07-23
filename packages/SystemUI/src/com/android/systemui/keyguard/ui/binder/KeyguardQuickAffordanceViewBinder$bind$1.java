package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.animation.view.LaunchableImageView;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceViewBinder$bind$1 {
    public final /* synthetic */ DisposableHandle $disposableHandle;
    public final /* synthetic */ LaunchableImageView $view;

    public KeyguardQuickAffordanceViewBinder$bind$1(MutableStateFlow mutableStateFlow, KeyguardQuickAffordanceViewBinder keyguardQuickAffordanceViewBinder, LaunchableImageView launchableImageView, DisposableHandle disposableHandle) {
        this.$view = launchableImageView;
        this.$disposableHandle = disposableHandle;
    }

    public final void destroy() {
        this.$view.setOnApplyWindowInsetsListener(null);
        this.$disposableHandle.dispose();
    }
}
