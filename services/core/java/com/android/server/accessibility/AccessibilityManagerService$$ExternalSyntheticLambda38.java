package com.android.server.accessibility;

import android.os.SystemClock;
import android.os.UserManager;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.util.function.pooled.PooledLambda;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityManagerService$$ExternalSyntheticLambda38
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AccessibilityManagerService$$ExternalSyntheticLambda38(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AccessibilityManagerService accessibilityManagerService = (AccessibilityManagerService) obj;
        switch (this.$r8$classId) {
            case 0:
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        if (accessibilityManagerService
                                .getUserStateLocked(accessibilityManagerService.mCurrentUserId)
                                .isHandlingAccessibilityEventsLocked()) {
                            String string =
                                    accessibilityManagerService.mContext.getString(
                                            17043434,
                                            ((UserManager)
                                                            accessibilityManagerService.mContext
                                                                    .getSystemService("user"))
                                                    .getUserInfo(
                                                            accessibilityManagerService
                                                                    .mCurrentUserId)
                                                    .name);
                            AccessibilityEvent obtain =
                                    AccessibilityEvent.obtain(
                                            EndpointMonitorConst
                                                    .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
                            obtain.getText().add(string);
                            int i = accessibilityManagerService.mCurrentUserId;
                            obtain.setEventTime(SystemClock.uptimeMillis());
                            accessibilityManagerService.mMainHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new AccessibilityManagerService$$ExternalSyntheticLambda12(
                                                    5),
                                            accessibilityManagerService,
                                            obtain,
                                            Integer.valueOf(i)));
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        accessibilityManagerService.mInputBound = true;
                        AccessibilityUserState userStateLocked =
                                accessibilityManagerService.getUserStateLocked(
                                        accessibilityManagerService.mCurrentUserId);
                        for (int size = userStateLocked.mBoundServices.size() - 1;
                                size >= 0;
                                size--) {
                            AccessibilityServiceConnection accessibilityServiceConnection =
                                    (AccessibilityServiceConnection)
                                            userStateLocked.mBoundServices.get(size);
                            if (accessibilityServiceConnection.mRequestImeApis) {
                                accessibilityServiceConnection
                                        .mInvocationHandler
                                        .obtainMessage(12)
                                        .sendToTarget();
                            }
                        }
                    } finally {
                    }
                }
                return;
            default:
                synchronized (accessibilityManagerService.mLock) {
                    try {
                        accessibilityManagerService.mInputBound = false;
                        AccessibilityUserState userStateLocked2 =
                                accessibilityManagerService.getUserStateLocked(
                                        accessibilityManagerService.mCurrentUserId);
                        for (int size2 = userStateLocked2.mBoundServices.size() - 1;
                                size2 >= 0;
                                size2--) {
                            AccessibilityServiceConnection accessibilityServiceConnection2 =
                                    (AccessibilityServiceConnection)
                                            userStateLocked2.mBoundServices.get(size2);
                            if (accessibilityServiceConnection2.mRequestImeApis) {
                                accessibilityServiceConnection2
                                        .mInvocationHandler
                                        .obtainMessage(13)
                                        .sendToTarget();
                            }
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
