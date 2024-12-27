package com.android.systemui.keyguard.ui.composable.section;

import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import dagger.Lazy;

public final class StatusBarSection {
    public final KeyguardStatusBarViewComponent.Factory componentFactory;
    public final Lazy notificationPanelView;

    public StatusBarSection(KeyguardStatusBarViewComponent.Factory factory, Lazy lazy) {
    }
}
