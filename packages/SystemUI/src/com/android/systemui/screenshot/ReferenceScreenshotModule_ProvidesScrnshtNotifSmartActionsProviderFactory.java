package com.android.systemui.screenshot;

import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory implements Provider {
    public static ScreenshotNotificationSmartActionsProvider providesScrnshtNotifSmartActionsProvider() {
        return new ScreenshotNotificationSmartActionsProvider();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ScreenshotNotificationSmartActionsProvider();
    }
}
