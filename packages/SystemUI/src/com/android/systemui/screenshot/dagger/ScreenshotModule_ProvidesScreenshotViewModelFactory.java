package com.android.systemui.screenshot.dagger;

import android.view.accessibility.AccessibilityManager;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class ScreenshotModule_ProvidesScreenshotViewModelFactory implements Provider {
    public final Provider accessibilityManagerProvider;

    public ScreenshotModule_ProvidesScreenshotViewModelFactory(Provider provider) {
        this.accessibilityManagerProvider = provider;
    }

    public static ScreenshotViewModel providesScreenshotViewModel(AccessibilityManager accessibilityManager) {
        return new ScreenshotViewModel(accessibilityManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ScreenshotViewModel((AccessibilityManager) this.accessibilityManagerProvider.get());
    }
}
