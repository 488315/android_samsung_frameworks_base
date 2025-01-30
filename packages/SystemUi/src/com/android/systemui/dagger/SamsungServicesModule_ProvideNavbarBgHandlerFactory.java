package com.android.systemui.dagger;

import android.os.Handler;
import android.os.HandlerThread;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideNavbarBgHandlerFactory implements Provider {
    public static Handler provideNavbarBgHandler() {
        HandlerThread handlerThread = new HandlerThread("SysUiNavbarBg", 0);
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideNavbarBgHandler();
    }
}
