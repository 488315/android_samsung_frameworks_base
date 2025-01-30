package com.android.systemui.bixby2;

import android.content.Context;
import com.android.systemui.bixby2.interactor.AppControlActionInteractor;
import com.android.systemui.bixby2.interactor.DeviceControlActionInteractor;
import com.android.systemui.bixby2.interactor.MusicControlActionInteractor;
import com.android.systemui.bixby2.interactor.NotificationControlActionInteractor;
import com.android.systemui.bixby2.interactor.ScreenControlActionInteractor;
import com.android.systemui.bixby2.interactor.ShareViaActionInteractor;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUICommandActionHandler_Factory implements Provider {
    private final Provider appControlActionInteractorProvider;
    private final Provider contextProvider;
    private final Provider deviceControlActionInteractorProvider;
    private final Provider musicControlActionInteractorProvider;
    private final Provider notificationControlActionInteractorProvider;
    private final Provider screenControlActionInteractorProvider;
    private final Provider shareViaActionInteractorProvider;
    private final Provider subscreenNotificationControllerProvider;

    public SystemUICommandActionHandler_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.appControlActionInteractorProvider = provider2;
        this.deviceControlActionInteractorProvider = provider3;
        this.musicControlActionInteractorProvider = provider4;
        this.notificationControlActionInteractorProvider = provider5;
        this.screenControlActionInteractorProvider = provider6;
        this.shareViaActionInteractorProvider = provider7;
        this.subscreenNotificationControllerProvider = provider8;
    }

    public static SystemUICommandActionHandler_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new SystemUICommandActionHandler_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static SystemUICommandActionHandler newInstance(Context context, AppControlActionInteractor appControlActionInteractor, DeviceControlActionInteractor deviceControlActionInteractor, MusicControlActionInteractor musicControlActionInteractor, NotificationControlActionInteractor notificationControlActionInteractor, ScreenControlActionInteractor screenControlActionInteractor, ShareViaActionInteractor shareViaActionInteractor, SubscreenNotificationController subscreenNotificationController) {
        return new SystemUICommandActionHandler(context, appControlActionInteractor, deviceControlActionInteractor, musicControlActionInteractor, notificationControlActionInteractor, screenControlActionInteractor, shareViaActionInteractor, subscreenNotificationController);
    }

    @Override // javax.inject.Provider
    public SystemUICommandActionHandler get() {
        return newInstance((Context) this.contextProvider.get(), (AppControlActionInteractor) this.appControlActionInteractorProvider.get(), (DeviceControlActionInteractor) this.deviceControlActionInteractorProvider.get(), (MusicControlActionInteractor) this.musicControlActionInteractorProvider.get(), (NotificationControlActionInteractor) this.notificationControlActionInteractorProvider.get(), (ScreenControlActionInteractor) this.screenControlActionInteractorProvider.get(), (ShareViaActionInteractor) this.shareViaActionInteractorProvider.get(), (SubscreenNotificationController) this.subscreenNotificationControllerProvider.get());
    }
}
