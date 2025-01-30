package com.android.systemui;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.ForegroundServiceController;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ForegroundServiceNotificationListener$$ExternalSyntheticLambda0 implements ForegroundServiceController.UserStateUpdateCallback {
    public final /* synthetic */ ForegroundServiceNotificationListener f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ForegroundServiceNotificationListener$$ExternalSyntheticLambda0(ForegroundServiceNotificationListener foregroundServiceNotificationListener, StatusBarNotification statusBarNotification, int i) {
        this.f$0 = foregroundServiceNotificationListener;
        this.f$1 = statusBarNotification;
        this.f$2 = i;
    }

    @Override // com.android.systemui.ForegroundServiceController.UserStateUpdateCallback
    public final boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState) {
        ForegroundServiceNotificationListener foregroundServiceNotificationListener = this.f$0;
        foregroundServiceNotificationListener.mForegroundServiceController.getClass();
        StatusBarNotification statusBarNotification = this.f$1;
        if (ForegroundServiceController.isDisclosureNotification(statusBarNotification)) {
            Bundle bundle = statusBarNotification.getNotification().extras;
            if (bundle != null) {
                String[] stringArray = bundle.getStringArray("android.foregroundApps");
                long j = statusBarNotification.getNotification().when;
                foregroundServicesUserState.mRunning = stringArray != null ? (String[]) Arrays.copyOf(stringArray, stringArray.length) : null;
                foregroundServicesUserState.mServiceStartTime = j;
            }
        } else {
            String packageName = statusBarNotification.getPackageName();
            String key = statusBarNotification.getKey();
            ArrayMap arrayMap = foregroundServicesUserState.mImportantNotifications;
            ArraySet arraySet = (ArraySet) arrayMap.get(packageName);
            if (arraySet != null) {
                arraySet.remove(key);
                if (arraySet.size() == 0) {
                    arrayMap.remove(packageName);
                }
            }
            ArrayMap arrayMap2 = foregroundServicesUserState.mStandardLayoutNotifications;
            ArraySet arraySet2 = (ArraySet) arrayMap2.get(packageName);
            if (arraySet2 != null) {
                arraySet2.remove(key);
                if (arraySet2.size() == 0) {
                    arrayMap2.remove(packageName);
                }
            }
            if ((statusBarNotification.getNotification().flags & 64) != 0 && this.f$2 > 1) {
                String packageName2 = statusBarNotification.getPackageName();
                String key2 = statusBarNotification.getKey();
                if (arrayMap.get(packageName2) == null) {
                    arrayMap.put(packageName2, new ArraySet());
                }
                ((ArraySet) arrayMap.get(packageName2)).add(key2);
            }
            if (Notification.Builder.recoverBuilder(foregroundServiceNotificationListener.mContext, statusBarNotification.getNotification()).usesStandardHeader()) {
                String packageName3 = statusBarNotification.getPackageName();
                String key3 = statusBarNotification.getKey();
                if (arrayMap2.get(packageName3) == null) {
                    arrayMap2.put(packageName3, new ArraySet());
                }
                ((ArraySet) arrayMap2.get(packageName3)).add(key3);
            }
        }
        return true;
    }
}
