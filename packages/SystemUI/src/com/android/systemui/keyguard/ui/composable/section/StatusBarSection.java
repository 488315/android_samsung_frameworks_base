package com.android.systemui.keyguard.ui.composable.section;

import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StatusBarSection {
    public final KeyguardStatusBarViewComponent.Factory componentFactory;
    public final Lazy notificationPanelView;

    public StatusBarSection(KeyguardStatusBarViewComponent.Factory factory, Lazy lazy) {
        this.componentFactory = factory;
        this.notificationPanelView = lazy;
    }
}
