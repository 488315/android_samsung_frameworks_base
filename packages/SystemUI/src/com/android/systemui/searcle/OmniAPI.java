package com.android.systemui.searcle;

import android.content.Context;
import com.android.systemui.searcle.omni.SimpleBroadcastReceiver;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class OmniAPI {
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    public static Context mContext = null;
    public static boolean mIsOmniPackageEnabled = true;
    public static final SimpleBroadcastReceiver mOmniPackageReceiver;

    static {
        int iMax = Math.max(Runtime.getRuntime().availableProcessors(), 2);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(iMax, iMax, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        mOmniPackageReceiver = new SimpleBroadcastReceiver(new OmniAPI$$ExternalSyntheticLambda0());
    }
}
