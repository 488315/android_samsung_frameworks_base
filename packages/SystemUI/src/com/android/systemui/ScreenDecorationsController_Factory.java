package com.android.systemui;

import android.content.Context;
import com.android.systemui.ScreenDecorationsComponent;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.decor.CoverPrivacyDotDecorProviderFactory;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.events.CoverPrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import dagger.internal.Provider;

/* loaded from: classes.dex */
public final class ScreenDecorationsController_Factory implements Provider {
    public final Provider contextProvider;
    public final Provider coverDotFactoryProvider;
    public final Provider coverPrivacyDotViewControllerProvider;
    public final Provider displayTrackerProvider;
    public final Provider dotFactoryProvider;
    public final Provider privacyDotViewControllerProvider;
    public final Provider screenDecorationsComponentFactoryProvider;

    public ScreenDecorationsController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.displayTrackerProvider = provider2;
        this.screenDecorationsComponentFactoryProvider = provider3;
        this.privacyDotViewControllerProvider = provider4;
        this.coverPrivacyDotViewControllerProvider = provider5;
        this.dotFactoryProvider = provider6;
        this.coverDotFactoryProvider = provider7;
    }

    public static ScreenDecorationsController newInstance(Context context, DisplayTracker displayTracker, DaggerReferenceGlobalRootComponent.ScreenDecorationsComponentFactory screenDecorationsComponentFactory, PrivacyDotViewController privacyDotViewController, CoverPrivacyDotViewController coverPrivacyDotViewController, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, CoverPrivacyDotDecorProviderFactory coverPrivacyDotDecorProviderFactory) {
        return new ScreenDecorationsController(context, displayTracker, screenDecorationsComponentFactory, privacyDotViewController, coverPrivacyDotViewController, privacyDotDecorProviderFactory, coverPrivacyDotDecorProviderFactory);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ScreenDecorationsController((Context) this.contextProvider.get(), (DisplayTracker) this.displayTrackerProvider.get(), (ScreenDecorationsComponent.Factory) this.screenDecorationsComponentFactoryProvider.get(), (PrivacyDotViewController) this.privacyDotViewControllerProvider.get(), (CoverPrivacyDotViewController) this.coverPrivacyDotViewControllerProvider.get(), (PrivacyDotDecorProviderFactory) this.dotFactoryProvider.get(), (CoverPrivacyDotDecorProviderFactory) this.coverDotFactoryProvider.get());
    }
}
