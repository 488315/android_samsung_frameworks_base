package com.android.server.wm;

import android.annotation.SystemApi;
import android.os.Binder;

import com.android.server.LocalServices;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public class ActivityInterceptorCallbackRegistry {
    public static final ActivityInterceptorCallbackRegistry sInstance =
            new ActivityInterceptorCallbackRegistry();

    public static ActivityInterceptorCallbackRegistry getInstance() {
        return sInstance;
    }

    public int getCallingUid() {
        return Binder.getCallingUid();
    }

    public void registerActivityInterceptorCallback(
            int i, ActivityInterceptorCallback activityInterceptorCallback) {
        if (getCallingUid() != 1000) {
            throw new SecurityException(
                    "Only system server can register ActivityInterceptorCallback");
        }
        if (!ActivityInterceptorCallback.isValidMainlineOrderId(i)) {
            throw new IllegalArgumentException(
                    "id is not in the mainline modules range, please"
                        + " useActivityTaskManagerInternal.registerActivityStartInterceptor(OrderedId,"
                        + " ActivityInterceptorCallback) instead.");
        }
        if (activityInterceptorCallback == null) {
            throw new IllegalArgumentException(
                    "The passed ActivityInterceptorCallback can not be null");
        }
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class))
                .registerActivityStartInterceptor(i, activityInterceptorCallback);
    }

    public void unregisterActivityInterceptorCallback(int i) {
        if (getCallingUid() != 1000) {
            throw new SecurityException(
                    "Only system server can register ActivityInterceptorCallback");
        }
        if (!ActivityInterceptorCallback.isValidMainlineOrderId(i)) {
            throw new IllegalArgumentException(
                    "id is not in the mainline modules range, please"
                        + " useActivityTaskManagerInternal.unregisterActivityStartInterceptor(OrderedId)"
                        + " instead.");
        }
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService)
                        ((ActivityTaskManagerInternal)
                                LocalServices.getService(ActivityTaskManagerInternal.class));
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!ActivityTaskManagerService.this.mActivityInterceptorCallbacks.contains(i)) {
                    throw new IllegalArgumentException(
                            "ActivityInterceptorCallback with id (" + i + ") is not registered");
                }
                ActivityTaskManagerService.this.mActivityInterceptorCallbacks.remove(i);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
