package com.android.systemui.screenshot;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.screenshot.ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory */
/* loaded from: classes2.dex */
public final class C2334x95ac99d4 implements Provider {
    public static ScreenshotNotificationSmartActionsProvider providesScrnshtNotifSmartActionsProvider() {
        return new ScreenshotNotificationSmartActionsProvider();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new ScreenshotNotificationSmartActionsProvider();
    }
}
