package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WifiQoSScoredCache {
    public final Context mContext;
    public final SemCacheListener mListener;
    public final SemWifiManager mSemWifiManager;
    public boolean mUpdated;
    public final Object mLock = new Object();
    public final Map mCache = new HashMap();
    public final LogUtils mLog = new LogUtils();

    public abstract class SemCacheListener {
        public final Handler mHandler;

        public SemCacheListener(Handler handler) {
            Objects.requireNonNull(handler);
            this.mHandler = handler;
        }

        public abstract void networkCacheUpdated();
    }

    public WifiQoSScoredCache(Context context, SemCacheListener semCacheListener) {
        this.mContext = context;
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mListener = semCacheListener;
    }
}
