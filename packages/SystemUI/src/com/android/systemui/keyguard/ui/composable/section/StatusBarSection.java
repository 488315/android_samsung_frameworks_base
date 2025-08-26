package com.android.systemui.keyguard.ui.composable.section;

import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import dagger.Lazy;

/* loaded from: classes2.dex */
public final class StatusBarSection {
    public final KeyguardStatusBarViewComponent.Factory componentFactory;
    public final Lazy notificationPanelView;

    public StatusBarSection(KeyguardStatusBarViewComponent.Factory factory, Lazy lazy) {
        this.componentFactory = factory;
        this.notificationPanelView = lazy;
    }
}
