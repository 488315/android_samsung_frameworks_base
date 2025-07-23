package com.android.systemui.screenshot;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
