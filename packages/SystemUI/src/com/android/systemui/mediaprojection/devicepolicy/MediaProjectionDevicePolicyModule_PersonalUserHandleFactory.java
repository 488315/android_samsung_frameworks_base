package com.android.systemui.mediaprojection.devicepolicy;

import android.app.ActivityManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.internal.Provider;

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
            UserHandle userHandleOf = UserHandle.of(currentUser != null ? currentUser.id : 0);
            userHandleOf.getClass();
            return userHandleOf;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return personalUserHandle(this.module, (ActivityManagerWrapper) this.activityManagerWrapperProvider.get());
    }
}
