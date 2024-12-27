package com.android.systemui.searcle;

import android.content.Context;
import com.android.systemui.searcle.omni.SimpleBroadcastReceiver;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OmniAPI {
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;
    public static Context mContext = null;
    public static boolean mIsOmniPackageEnabled = true;
    public static final SimpleBroadcastReceiver mOmniPackageReceiver;

    static {
        int max = Math.max(Runtime.getRuntime().availableProcessors(), 2);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(max, max, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        mOmniPackageReceiver = new SimpleBroadcastReceiver(new OmniAPI$$ExternalSyntheticLambda0());
    }
}
