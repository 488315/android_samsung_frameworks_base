package com.samsung.android.server.continuity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;

import com.samsung.android.continuity.ISemContinuityManager;
import com.samsung.android.mcfds.lib.AbstractDeviceSyncManager$1;
import com.samsung.android.mcfds.lib.DeviceSyncManager;
import com.samsung.android.server.continuity.common.ExecutorUtil;
import com.samsung.android.server.continuity.common.ExecutorUtil$$ExternalSyntheticLambda0;
import com.samsung.android.server.continuity.sem.SemWrapper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class AbstractSemContinuityServiceImpl extends ISemContinuityManager.Stub {
    public int mCurrentUserId = -10000;
    public final McfDeviceSyncManager mMcfDsManager;
    public final UserManager mUserManager;

    public AbstractSemContinuityServiceImpl(
            Context context, McfDeviceSyncManager mcfDeviceSyncManager) {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(
                        64,
                        64,
                        60L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue(),
                        new ExecutorUtil$$ExternalSyntheticLambda0());
        ExecutorUtil.sExecutorIO = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        ExecutorUtil.sHandler = new Handler(Looper.getMainLooper());
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mMcfDsManager = mcfDeviceSyncManager;
    }

    public final int getNearbyDeviceCount(int i, int i2) {
        int semGetIdentifier;
        UserHandle userHandle = this.mMcfDsManager.mCurrentUserHandle;
        if (userHandle == null) {
            semGetIdentifier = -10000;
        } else {
            UserHandle userHandle2 = SemWrapper.SEM_ALL;
            semGetIdentifier = userHandle.semGetIdentifier();
        }
        if (semGetIdentifier != i2) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i2,
                    "getNearbyDeviceCount - invalid user ",
                    "[MCF_DS_SYS]_SemContinuityServiceImpl");
            return 0;
        }
        McfDeviceSyncManager mcfDeviceSyncManager = this.mMcfDsManager;
        mcfDeviceSyncManager.getClass();
        Log.d("[MCF_DS_SYS]_McfDsManager", "getNearbyDeviceCount");
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                return 0;
            }
        }
        DeviceSyncManager deviceSyncManager = mcfDeviceSyncManager.mDsManager;
        deviceSyncManager.getClass();
        Log.i("[MCF_DS_LIB]_DeviceSyncManager", "getNearbyDeviceCount");
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_INPUT", i3);
        AbstractDeviceSyncManager$1 abstractDeviceSyncManager$1 = deviceSyncManager.mCoreInterface;
        abstractDeviceSyncManager$1.getClass();
        Message obtain = Message.obtain();
        obtain.what = 1000;
        obtain.obj = bundle;
        return Math.max(abstractDeviceSyncManager$1.sendMessage(obtain), 0);
    }

    public abstract void setCurrentUserHandle(UserHandle userHandle);
}
