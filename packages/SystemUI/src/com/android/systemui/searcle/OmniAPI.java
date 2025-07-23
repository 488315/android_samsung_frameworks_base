package com.android.systemui.searcle;

import android.content.Context;
import com.android.systemui.searcle.omni.SimpleBroadcastReceiver;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class OmniAPI {
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
