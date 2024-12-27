package com.android.server.pm;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageMonitorCallbackHelper {
    public IActivityManager mActivityManager;
    public final Object mLock = new Object();
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RegisterUser {
        public int mUid;
        public int mUserId;
    }

    public static boolean isAllowedCallbackAction(String str) {
        return TextUtils.equals(str, "android.intent.action.PACKAGE_ADDED")
                || TextUtils.equals(str, "android.intent.action.PACKAGE_REMOVED")
                || TextUtils.equals(str, "android.intent.action.PACKAGE_CHANGED")
                || TextUtils.equals(str, "android.intent.action.UID_REMOVED")
                || TextUtils.equals(str, "android.intent.action.PACKAGES_SUSPENDED")
                || TextUtils.equals(str, "android.intent.action.PACKAGES_UNSUSPENDED")
                || TextUtils.equals(str, "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE")
                || TextUtils.equals(str, "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE")
                || TextUtils.equals(str, "android.intent.action.PACKAGE_DATA_CLEARED")
                || TextUtils.equals(str, "android.intent.action.PACKAGE_RESTARTED")
                || TextUtils.equals(str, "android.intent.action.PACKAGE_UNSTOPPED");
    }

    public final void doNotifyCallbacksByAction(
            String str,
            String str2,
            Bundle bundle,
            int[] iArr,
            SparseArray sparseArray,
            Handler handler,
            BroadcastHelper$$ExternalSyntheticLambda6 broadcastHelper$$ExternalSyntheticLambda6) {
        RemoteCallbackList remoteCallbackList;
        String str3;
        Uri uri;
        synchronized (this.mLock) {
            remoteCallbackList = this.mCallbacks;
        }
        for (int i : iArr) {
            int[] iArr2 = null;
            if (str2 != null) {
                uri = Uri.fromParts("package", str2, null);
                str3 = str;
            } else {
                str3 = str;
                uri = null;
            }
            Intent intent = new Intent(str3, uri);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra >= 0 && UserHandle.getUserId(intExtra) != i) {
                intent.putExtra(
                        "android.intent.extra.UID",
                        UserHandle.getUid(i, UserHandle.getAppId(intExtra)));
            }
            intent.putExtra("android.intent.extra.user_handle", i);
            if (sparseArray != null) {
                iArr2 = (int[]) sparseArray.get(i);
            }
            handler.post(
                    new PackageMonitorCallbackHelper$$ExternalSyntheticLambda0(
                            this,
                            remoteCallbackList,
                            i,
                            iArr2,
                            intent,
                            broadcastHelper$$ExternalSyntheticLambda6));
        }
    }

    public final void notifyPackageMonitor(
            String str,
            String str2,
            Bundle bundle,
            int[] iArr,
            int[] iArr2,
            SparseArray sparseArray,
            Handler handler,
            BroadcastHelper$$ExternalSyntheticLambda6 broadcastHelper$$ExternalSyntheticLambda6) {
        int[] runningUserIds;
        if (isAllowedCallbackAction(str)) {
            if (iArr == null) {
                try {
                    if (this.mActivityManager == null) {
                        this.mActivityManager = ActivityManager.getService();
                    }
                    IActivityManager iActivityManager = this.mActivityManager;
                    if (iActivityManager == null) {
                        return;
                    } else {
                        runningUserIds = iActivityManager.getRunningUserIds();
                    }
                } catch (RemoteException unused) {
                    return;
                }
            } else {
                runningUserIds = iArr;
            }
            if (ArrayUtils.isEmpty(iArr2)) {
                doNotifyCallbacksByAction(
                        str,
                        str2,
                        bundle,
                        runningUserIds,
                        sparseArray,
                        handler,
                        broadcastHelper$$ExternalSyntheticLambda6);
            } else {
                doNotifyCallbacksByAction(
                        str,
                        str2,
                        bundle,
                        iArr2,
                        sparseArray,
                        handler,
                        broadcastHelper$$ExternalSyntheticLambda6);
            }
        }
    }

    public final void onUserRemoved(int i) {
        int i2;
        ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                int registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
                arrayList = null;
                for (int i3 = 0; i3 < registeredCallbackCount; i3++) {
                    if (((RegisterUser) this.mCallbacks.getRegisteredCallbackCookie(i3)).mUserId
                            == i) {
                        IRemoteCallback registeredCallbackItem =
                                this.mCallbacks.getRegisteredCallbackItem(i3);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(registeredCallbackItem);
                    }
                }
            } finally {
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        int size = arrayList.size();
        for (i2 = 0; i2 < size; i2++) {
            IRemoteCallback iRemoteCallback = (IRemoteCallback) arrayList.get(i2);
            synchronized (this.mLock) {
                this.mCallbacks.unregister(iRemoteCallback);
            }
        }
    }
}
