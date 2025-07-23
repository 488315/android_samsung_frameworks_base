package com.android.systemui.mediaprojection.devicepolicy;

import android.app.ActivityManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaProjectionDevicePolicyModule_PersonalUserHandleFactory implements Provider {
    public final Provider activityManagerWrapperProvider;
    public final MediaProjectionDevicePolicyModule module;

    public MediaProjectionDevicePolicyModule_PersonalUserHandleFactory(MediaProjectionDevicePolicyModule mediaProjectionDevicePolicyModule, Provider provider) {
        this.module = mediaProjectionDevicePolicyModule;
        this.activityManagerWrapperProvider = provider;
    }

    public static UserHandle personalUserHandle(MediaProjectionDevicePolicyModule mediaProjectionDevicePolicyModule, ActivityManagerWrapper activityManagerWrapper) {
        mediaProjectionDevicePolicyModule.getClass();
        activityManagerWrapper.getClass();
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            UserHandle of = UserHandle.of(currentUser != null ? currentUser.id : 0);
            of.getClass();
            return of;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return personalUserHandle(this.module, (ActivityManagerWrapper) this.activityManagerWrapperProvider.get());
    }
}
