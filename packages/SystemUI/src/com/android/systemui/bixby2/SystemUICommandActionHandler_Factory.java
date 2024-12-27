package com.android.systemui.bixby2;

import android.content.Context;
import com.android.systemui.bixby2.interactor.AppControlActionInteractor;
import com.android.systemui.bixby2.interactor.DeviceControlActionInteractor;
import com.android.systemui.bixby2.interactor.MusicControlActionInteractor;
import com.android.systemui.bixby2.interactor.NotificationControlActionInteractor;
import com.android.systemui.bixby2.interactor.ScreenControlActionInteractor;
import com.android.systemui.bixby2.interactor.ShareViaActionInteractor;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SystemUICommandActionHandler_Factory implements Provider {
    private final javax.inject.Provider appControlActionInteractorProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider deviceControlActionInteractorProvider;
    private final javax.inject.Provider musicControlActionInteractorProvider;
    private final javax.inject.Provider notificationControlActionInteractorProvider;
    private final javax.inject.Provider screenControlActionInteractorProvider;
    private final javax.inject.Provider shareViaActionInteractorProvider;
    private final javax.inject.Provider subscreenNotificationControllerProvider;

    public SystemUICommandActionHandler_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
        this.contextProvider = provider;
        this.appControlActionInteractorProvider = provider2;
        this.deviceControlActionInteractorProvider = provider3;
        this.musicControlActionInteractorProvider = provider4;
        this.notificationControlActionInteractorProvider = provider5;
        this.screenControlActionInteractorProvider = provider6;
        this.shareViaActionInteractorProvider = provider7;
        this.subscreenNotificationControllerProvider = provider8;
    }

    public static SystemUICommandActionHandler_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8) {
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
