package android.net.wifi;

import android.Manifest;
import android.content.Context;
import android.net.INetworkScoreCache;
import android.net.NetworkKey;
import android.net.ScoredNetwork;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.util.LruCache;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public class WifiNetworkScoreCache extends INetworkScoreCache.Stub {
    private static final int DEFAULT_MAX_CACHE_SIZE = 100;
    public static final int INVALID_NETWORK_SCORE = -128;
    private final LruCache<String, ScoredNetwork> mCache;
    private final Context mContext;
    private CacheListener mListener;
    private final Object mLock;
    private static final String TAG = "WifiNetworkScoreCache";
    private static final boolean DBG = Log.isLoggable(TAG, 3);

    public WifiNetworkScoreCache(Context context) {
        this(context, null);
    }

    public WifiNetworkScoreCache(Context context, CacheListener cacheListener) {
        this(context, cacheListener, 100);
    }

    public WifiNetworkScoreCache(Context context, CacheListener cacheListener, int i) {
        this.mLock = new Object();
        this.mContext = context.getApplicationContext();
        this.mListener = cacheListener;
        this.mCache = new LruCache<>(i);
    }

    @Override // android.net.INetworkScoreCache
    public final void updateScores(List<ScoredNetwork> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (DBG) {
            Log.d(TAG, "updateScores list size=" + list.size());
        }
        synchronized (this.mLock) {
            boolean z = false;
            for (ScoredNetwork scoredNetwork : list) {
                String buildNetworkKey = buildNetworkKey(scoredNetwork);
                if (buildNetworkKey == null) {
                    if (DBG) {
                        Log.d(TAG, "Failed to build network key for ScoredNetwork" + scoredNetwork);
                    }
                } else {
                    this.mCache.put(buildNetworkKey, scoredNetwork);
                    z = true;
                }
            }
            CacheListener cacheListener = this.mListener;
            if (cacheListener != null && z) {
                cacheListener.post(list);
            }
        }
    }

    @Override // android.net.INetworkScoreCache
    public final void clearScores() {
        synchronized (this.mLock) {
            this.mCache.evictAll();
        }
    }

    public boolean isScoredNetwork(ScanResult scanResult) {
        return getScoredNetwork(scanResult) != null;
    }

    public boolean hasScoreCurve(ScanResult scanResult) {
        ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        return (scoredNetwork == null || scoredNetwork.rssiCurve == null) ? false : true;
    }

    public int getNetworkScore(ScanResult scanResult) {
        ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        if (scoredNetwork == null || scoredNetwork.rssiCurve == null) {
            return -128;
        }
        byte lookupScore = scoredNetwork.rssiCurve.lookupScore(scanResult.level);
        if (DBG) {
            Log.d(TAG, "getNetworkScore found scored network " + scoredNetwork.networkKey + " score " + Integer.toString(lookupScore) + " RSSI " + scanResult.level);
        }
        return lookupScore;
    }

    public boolean getMeteredHint(ScanResult scanResult) {
        ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        return scoredNetwork != null && scoredNetwork.meteredHint;
    }

    public int getNetworkScore(ScanResult scanResult, boolean z) {
        ScoredNetwork scoredNetwork = getScoredNetwork(scanResult);
        if (scoredNetwork == null || scoredNetwork.rssiCurve == null) {
            return -128;
        }
        byte lookupScore = scoredNetwork.rssiCurve.lookupScore(scanResult.level, z);
        if (DBG) {
            Log.d(TAG, "getNetworkScore found scored network " + scoredNetwork.networkKey + " score " + Integer.toString(lookupScore) + " RSSI " + scanResult.level + " isActiveNetwork " + z);
        }
        return lookupScore;
    }

    public ScoredNetwork getScoredNetwork(ScanResult scanResult) {
        ScoredNetwork scoredNetwork;
        String buildNetworkKey = buildNetworkKey(scanResult);
        if (buildNetworkKey == null) {
            return null;
        }
        synchronized (this.mLock) {
            scoredNetwork = this.mCache.get(buildNetworkKey);
        }
        return scoredNetwork;
    }

    public ScoredNetwork getScoredNetwork(NetworkKey networkKey) {
        ScoredNetwork scoredNetwork;
        String buildNetworkKey = buildNetworkKey(networkKey);
        if (buildNetworkKey == null) {
            if (!DBG) {
                return null;
            }
            Log.d(TAG, "Could not build key string for Network Key: " + networkKey);
            return null;
        }
        synchronized (this.mLock) {
            scoredNetwork = this.mCache.get(buildNetworkKey);
        }
        return scoredNetwork;
    }

    private String buildNetworkKey(ScoredNetwork scoredNetwork) {
        if (scoredNetwork == null) {
            return null;
        }
        return buildNetworkKey(scoredNetwork.networkKey);
    }

    private String buildNetworkKey(NetworkKey networkKey) {
        String str;
        if (networkKey == null || networkKey.wifiKey == null || networkKey.type != 1 || (str = networkKey.wifiKey.ssid) == null) {
            return null;
        }
        if (networkKey.wifiKey.bssid == null) {
            return str;
        }
        return str + networkKey.wifiKey.bssid;
    }

    private String buildNetworkKey(ScanResult scanResult) {
        if (scanResult == null || scanResult.SSID == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("\"");
        sb.append(scanResult.SSID);
        sb.append("\"");
        if (scanResult.BSSID != null) {
            sb.append(scanResult.BSSID);
        }
        return sb.toString();
    }

    @Override // android.os.Binder
    protected final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.DUMP, TAG);
        printWriter.println(String.format("WifiNetworkScoreCache (%s/%d)", this.mContext.getPackageName(), Integer.valueOf(Process.myUid())));
        printWriter.println("  All score curves:");
        synchronized (this.mLock) {
            Iterator<ScoredNetwork> it = this.mCache.snapshot().values().iterator();
            while (it.hasNext()) {
                printWriter.println("    " + it.next());
            }
            printWriter.println("  Network scores for latest ScanResults:");
            for (ScanResult scanResult : ((WifiManager) this.mContext.getSystemService("wifi")).getScanResults()) {
                printWriter.println("    " + buildNetworkKey(scanResult) + ": " + getNetworkScore(scanResult));
            }
        }
    }

    public void registerListener(CacheListener cacheListener) {
        synchronized (this.mLock) {
            this.mListener = cacheListener;
        }
    }

    public void unregisterListener() {
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    public static abstract class CacheListener {
        private Handler mHandler;

        public abstract void networkCacheUpdated(List<ScoredNetwork> list);

        public CacheListener(Handler handler) {
            Objects.requireNonNull(handler);
            this.mHandler = handler;
        }

        void post(final List<ScoredNetwork> list) {
            this.mHandler.post(new Runnable() { // from class: android.net.wifi.WifiNetworkScoreCache.CacheListener.1
                @Override // java.lang.Runnable
                public void run() {
                    CacheListener.this.networkCacheUpdated(list);
                }
            });
        }
    }
}
