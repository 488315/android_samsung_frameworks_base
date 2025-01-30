package com.android.server.enterprise.filter;

import android.os.Debug;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class KnoxNetworkFilterProfileInfo {
    public static final boolean DBG = Debug.semIsProductDev();
    public static ConcurrentHashMap mProfileInfomap = new ConcurrentHashMap();
    public volatile String mPackageName;
    public volatile int mPackageUid;
    public volatile String mProfileName;
    public volatile String mRulesConfig;
    public volatile String mSocketConfig;
    public volatile boolean mV4DnsConfigured = false;
    public volatile boolean mV4TcpConfigured = false;
    public volatile boolean mV4UdpConfigured = false;
    public volatile boolean mV6DnsConfigured = false;
    public volatile boolean mV6TcpConfigured = false;
    public volatile boolean mV6UdpConfigured = false;
    public volatile int mState = 6;
    public HashSet mVpnClientUidList = new HashSet();
    public HashSet mBrowserAppList = new HashSet();
    public volatile int mDNSCacheStatus = 0;

    public void setProfileName(String str) {
        this.mProfileName = str;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public void setRulesConfig(String str) {
        this.mRulesConfig = str;
    }

    public String getRulesConfig() {
        return this.mRulesConfig;
    }

    public void setSocketConfig(String str) {
        this.mSocketConfig = str;
    }

    public String getSocketConfig() {
        return this.mSocketConfig;
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageUid(int i) {
        this.mPackageUid = i;
    }

    public int getPackageUid() {
        return this.mPackageUid;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public int getState() {
        return this.mState;
    }

    public void setV4DnsConfigured(boolean z) {
        this.mV4DnsConfigured = z;
    }

    public boolean isV4DnsConfigured() {
        return this.mV4DnsConfigured;
    }

    public void setV4TcpConfigured(boolean z) {
        this.mV4TcpConfigured = z;
    }

    public boolean isV4TcpConfigured() {
        return this.mV4TcpConfigured;
    }

    public void setV4UdpConfigured(boolean z) {
        this.mV4UdpConfigured = z;
    }

    public boolean isV4UdpConfigured() {
        return this.mV4UdpConfigured;
    }

    public void setV6DnsConfigured(boolean z) {
        this.mV6DnsConfigured = z;
    }

    public void setV6TcpConfigured(boolean z) {
        this.mV6TcpConfigured = z;
    }

    public boolean isV6TcpConfigured() {
        return this.mV6TcpConfigured;
    }

    public void setV6UdpConfigured(boolean z) {
        this.mV6UdpConfigured = z;
    }

    public boolean isV6UdpConfigured() {
        return this.mV6UdpConfigured;
    }

    public static synchronized KnoxNetworkFilterProfileInfo addProfileEntry(String str, KnoxNetworkFilterProfileInfo knoxNetworkFilterProfileInfo) {
        synchronized (KnoxNetworkFilterProfileInfo.class) {
            if (str == null || knoxNetworkFilterProfileInfo == null) {
                return null;
            }
            return (KnoxNetworkFilterProfileInfo) mProfileInfomap.put(str, knoxNetworkFilterProfileInfo);
        }
    }

    public static synchronized KnoxNetworkFilterProfileInfo removeProfileEntry(String str) {
        synchronized (KnoxNetworkFilterProfileInfo.class) {
            if (str == null) {
                return null;
            }
            if (!mProfileInfomap.containsKey(str)) {
                return null;
            }
            return (KnoxNetworkFilterProfileInfo) mProfileInfomap.remove(str);
        }
    }

    public static synchronized KnoxNetworkFilterProfileInfo getProfileEntry(String str) {
        synchronized (KnoxNetworkFilterProfileInfo.class) {
            if (str == null) {
                return null;
            }
            if (!mProfileInfomap.containsKey(str)) {
                return null;
            }
            return (KnoxNetworkFilterProfileInfo) mProfileInfomap.get(str);
        }
    }

    public static boolean containsProfileEntry(String str) {
        if (str == null) {
            return false;
        }
        return mProfileInfomap.containsKey(str);
    }

    public static Collection getProfileEntries() {
        return mProfileInfomap.values();
    }

    public void addUidToVpnClientList(int i) {
        this.mVpnClientUidList.add(Integer.valueOf(i));
    }

    public boolean removeUidFromVpnClientList(int i) {
        if (!this.mVpnClientUidList.contains(Integer.valueOf(i))) {
            return false;
        }
        this.mVpnClientUidList.remove(Integer.valueOf(i));
        return true;
    }

    public HashSet getVpnClientUidList() {
        return this.mVpnClientUidList;
    }

    public void addAppsToBrowserList(String[] strArr) {
        for (String str : strArr) {
            this.mBrowserAppList.add(str);
        }
    }

    public HashSet getBrowserAppList() {
        return this.mBrowserAppList;
    }

    public void clearBrowserAppList() {
        this.mBrowserAppList.clear();
    }

    public int getDnsCacheStatus() {
        return this.mDNSCacheStatus;
    }

    public void setDnsCacheStatus(int i) {
        this.mDNSCacheStatus = i;
    }
}
