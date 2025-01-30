package com.android.systemui.screenshot;

import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartActionsReceiver_Factory implements Provider {
    public final Provider screenshotSmartActionsProvider;

    public SmartActionsReceiver_Factory(Provider provider) {
        this.screenshotSmartActionsProvider = provider;
    }

    public static SmartActionsReceiver newInstance(ScreenshotSmartActions screenshotSmartActions) {
        return new SmartActionsReceiver(screenshotSmartActions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SmartActionsReceiver((ScreenshotSmartActions) this.screenshotSmartActionsProvider.get());
    }
}
