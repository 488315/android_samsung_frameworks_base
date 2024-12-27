package com.android.systemui.mediaprojection.devicepolicy;

import android.app.ActivityManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaProjectionDevicePolicyModule_PersonalUserHandleFactory implements Provider {
    public final javax.inject.Provider activityManagerWrapperProvider;
    public final MediaProjectionDevicePolicyModule module;

    public MediaProjectionDevicePolicyModule_PersonalUserHandleFactory(MediaProjectionDevicePolicyModule mediaProjectionDevicePolicyModule, javax.inject.Provider provider) {
        this.module = mediaProjectionDevicePolicyModule;
        this.activityManagerWrapperProvider = provider;
    }

    public static UserHandle personalUserHandle(MediaProjectionDevicePolicyModule mediaProjectionDevicePolicyModule) {
        mediaProjectionDevicePolicyModule.getClass();
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            return UserHandle.of(currentUser != null ? currentUser.id : 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return personalUserHandle(this.module);
    }
}
