package com.samsung.android.sdk.bixby2;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.sdk.bixby2.provider.CapsuleProvider;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Sbixby {
    public static Map appMetaInfoMap;
    public static Sbixby mInstance;

    private Sbixby(Context context) {
    }

    public static synchronized Sbixby getInstance() {
        Sbixby sbixby;
        synchronized (Sbixby.class) {
            sbixby = mInstance;
            if (sbixby == null) {
                throw new IllegalStateException("The Sbixby instance is NULL. do initialize Sbixby before accessing instance.");
            }
        }
        return sbixby;
    }

    public static void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("App Context is NULL. pass valid context.");
        }
        if (mInstance == null) {
            mInstance = new Sbixby(context);
        }
        Sbixby sbixby = mInstance;
        String packageName = context.getPackageName();
        sbixby.getClass();
        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("package name is null or empty.");
        }
        Object obj = CapsuleProvider.sWaitLock;
        synchronized (obj) {
            if (!CapsuleProvider.mIsAppInitialized) {
                CapsuleProvider.mIsAppInitialized = true;
                Log.i("CapsuleProvider_1.0.24", "releasing initialize wait lock.");
                obj.notify();
            }
        }
        new Timer().schedule(new TimerTask() { // from class: com.samsung.android.sdk.bixby2.provider.CapsuleProvider.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public final void run() {
                CapsuleProvider.mWaitForHandler = false;
            }
        }, 3000L);
    }
}
