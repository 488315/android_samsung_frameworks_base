package com.android.server.enterprise.filter;

import android.os.Debug;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public final class KnoxNetworkFilterProfileInfo {
    public static final ConcurrentHashMap mProfileInfomap;
    public volatile String mPackageName;
    public volatile int mPackageUid;
    public volatile String mProfileName;
    public volatile String mRulesConfig;
    public volatile String mSocketConfig;
    public volatile boolean mV4DnsConfigured = false;
    public volatile boolean mV4TcpConfigured = false;
    public volatile boolean mV4UdpConfigured = false;
    public volatile boolean mV6TcpConfigured = false;
    public volatile boolean mV6UdpConfigured = false;
    public volatile int mState = 6;
    public final HashSet mVpnClientUidList = new HashSet();
    public final HashSet mBrowserAppList = new HashSet();
    public volatile int mDNSCacheStatus = 0;

    static {
        Debug.semIsProductDev();
        mProfileInfomap = new ConcurrentHashMap();
    }

    public static boolean containsProfileEntry(String str) {
        if (str == null) {
            return false;
        }
        return mProfileInfomap.containsKey(str);
    }

    public static synchronized KnoxNetworkFilterProfileInfo getProfileEntry(String str) {
        synchronized (KnoxNetworkFilterProfileInfo.class) {
            if (str == null) {
                return null;
            }
            ConcurrentHashMap concurrentHashMap = mProfileInfomap;
            if (!concurrentHashMap.containsKey(str)) {
                return null;
            }
            return (KnoxNetworkFilterProfileInfo) concurrentHashMap.get(str);
        }
    }
}
