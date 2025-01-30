package com.android.systemui.dagger;

import android.app.INotificationManager;
import android.os.ServiceManager;
import com.android.systemui.plugins.subscreen.SubRoom;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideINotificationManagerFactory implements Provider {
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideINotificationManagerFactory(FrameworkServicesModule frameworkServicesModule) {
        this.module = frameworkServicesModule;
    }

    public static INotificationManager provideINotificationManager(FrameworkServicesModule frameworkServicesModule) {
        frameworkServicesModule.getClass();
        INotificationManager asInterface = INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION));
        Preconditions.checkNotNullFromProvides(asInterface);
        return asInterface;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideINotificationManager(this.module);
    }
}
