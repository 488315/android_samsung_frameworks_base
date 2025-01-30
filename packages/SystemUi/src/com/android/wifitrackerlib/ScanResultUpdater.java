package com.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Debug;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScanResultUpdater {
    public final Clock mClock;
    public final Object mLock;
    public final LogUtils mLog;
    public final long mMaxScanAgeMillis;
    public final Map mScanResultsBySsidAndBssid;
    public final SemWifiEntryFilter mSemFilter;

    public ScanResultUpdater(Clock clock, Context context) {
        this(clock, Long.MAX_VALUE, context);
    }

    public final List getScanResults(long j) {
        ArrayList arrayList;
        if (j > this.mMaxScanAgeMillis) {
            throw new IllegalArgumentException("maxScanAgeMillis argument cannot be greater than mMaxScanAgeMillis!");
        }
        synchronized (this.mLock) {
            arrayList = new ArrayList();
            for (ScanResult scanResult : ((ArrayMap) this.mScanResultsBySsidAndBssid).values()) {
                if (this.mClock.millis() - (scanResult.timestamp / 1000) <= j) {
                    arrayList.add(scanResult);
                }
            }
        }
        return arrayList;
    }

    public final void update(List list) {
        synchronized (this.mLock) {
            synchronized (this.mLock) {
                ((ArrayMap) this.mScanResultsBySsidAndBssid).entrySet().removeIf(new ScanResultUpdater$$ExternalSyntheticLambda0(this));
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                ScanResult scanResult2 = (ScanResult) ((ArrayMap) this.mScanResultsBySsidAndBssid).get(pair);
                if (scanResult2 == null || scanResult2.timestamp < scanResult.timestamp) {
                    ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                }
            }
        }
    }

    public ScanResultUpdater(Clock clock, long j, Context context) {
        this.mScanResultsBySsidAndBssid = new ArrayMap();
        this.mLock = new Object();
        this.mMaxScanAgeMillis = j;
        this.mClock = clock;
        this.mSemFilter = new SemWifiEntryFilter(context);
        this.mLog = new LogUtils();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(List list, WifiInfo wifiInfo) {
        int i;
        boolean z;
        synchronized (this.mLock) {
            synchronized (this.mLock) {
                ((ArrayMap) this.mScanResultsBySsidAndBssid).entrySet().removeIf(new ScanResultUpdater$$ExternalSyntheticLambda0(this));
            }
            this.mSemFilter.updateRssiFilter();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ScanResult scanResult = (ScanResult) it.next();
                if (!TextUtils.isEmpty(scanResult.SSID)) {
                    Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                    ScanResult scanResult2 = (ScanResult) ((ArrayMap) this.mScanResultsBySsidAndBssid).get(pair);
                    if (scanResult2 == null || scanResult2.timestamp < scanResult.timestamp) {
                        SemWifiEntryFilter semWifiEntryFilter = this.mSemFilter;
                        semWifiEntryFilter.getClass();
                        int i2 = scanResult.level;
                        if (i2 >= semWifiEntryFilter.mWeakSignalRssi && ((i = scanResult.frequency) <= 5000 || i >= 6000 || i2 >= semWifiEntryFilter.mWeakSignalRssi5Ghz)) {
                            z = true;
                            if (!z) {
                                if (wifiInfo != null && TextUtils.equals(wifiInfo.getBSSID(), scanResult.BSSID)) {
                                    Log.d("WifiTracker.ScanResultUpdater", "it's weak signal network " + wifiInfo.getSSID());
                                } else if (Debug.semIsProductDev()) {
                                    LogUtils logUtils = this.mLog;
                                    String str = "filtered scan item: " + scanResult.toString();
                                    if (logUtils.isProductDev) {
                                        Log.d("WifiTracker.ScanResultUpdater", logUtils.getPrintableLog(str));
                                    }
                                }
                            }
                            ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                        }
                        z = false;
                        if (!z) {
                        }
                        ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                    }
                }
            }
        }
    }
}
