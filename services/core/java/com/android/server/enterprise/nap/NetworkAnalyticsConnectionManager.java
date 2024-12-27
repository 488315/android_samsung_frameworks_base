package com.android.server.enterprise.nap;

import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class NetworkAnalyticsConnectionManager {
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static NetworkAnalyticsConnectionManager mInstance;
    public int activatedProfileCounter;
    public ConcurrentHashMap binderMap;
    public ConcurrentHashMap profilesForPackage;

    public final void addProfileForPackage(String str, String str2) {
        if (this.profilesForPackage.get(str) == null) {
            this.profilesForPackage.put(str, PortStatus_1_1$$ExternalSyntheticOutline0.m(str2));
        } else if (isProfilePresentForPackage(str, str2)) {
            return;
        } else {
            ((List) this.profilesForPackage.get(str)).add(str2);
        }
        this.activatedProfileCounter++;
    }

    public final INetworkAnalyticsService getBinderForPackage(String str) {
        NetworkAnalyticsService.NetworkAnalyticsServiceConnection
                networkAnalyticsServiceConnection =
                        (NetworkAnalyticsService.NetworkAnalyticsServiceConnection)
                                this.binderMap.get(str);
        if (networkAnalyticsServiceConnection == null) {
            return null;
        }
        if (DBG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "getBinderForPackage: binder is not null for ",
                    str,
                    "NetworkAnalytics:ConnectionManager");
        }
        return networkAnalyticsServiceConnection.napInterface;
    }

    public final NetworkAnalyticsService.NetworkAnalyticsServiceConnection
            getServiceConnectionForPackage(String str) {
        NetworkAnalyticsService.NetworkAnalyticsServiceConnection
                networkAnalyticsServiceConnection =
                        (NetworkAnalyticsService.NetworkAnalyticsServiceConnection)
                                this.binderMap.get(str);
        if (networkAnalyticsServiceConnection != null && DBG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "getServiceConnectionForPackage: service connection is not null for ",
                    str,
                    "NetworkAnalytics:ConnectionManager");
        }
        return networkAnalyticsServiceConnection;
    }

    public final boolean isProfilePresentForPackage(String str, String str2) {
        boolean z = DBG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "isProfilePresentForPackage for packageName = ",
                    str,
                    "NetworkAnalytics:ConnectionManager");
        }
        List list = (List) this.profilesForPackage.get(str);
        boolean contains = list != null ? list.contains(str2) : false;
        if (z) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "isProfilePresentForPackage for packageName is = ",
                    "NetworkAnalytics:ConnectionManager",
                    contains);
        }
        return contains;
    }

    public final void removeBinderForPackage(String str) {
        if (DBG) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "removeBinderForPackage completely for packageName = ",
                    str,
                    "NetworkAnalytics:ConnectionManager");
        }
        this.binderMap.remove(str);
    }

    public final void removeProfileForPackage(String str, String str2) {
        boolean z = DBG;
        if (z) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "removeProfileForPackage for packageName = ",
                    str,
                    "NetworkAnalytics:ConnectionManager");
        }
        List list = (List) this.profilesForPackage.get(str);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (((String) list.get(i)).equals(str2)) {
                    list.remove(i);
                    this.activatedProfileCounter--;
                }
            }
            if (list.size() <= 0) {
                this.profilesForPackage.remove(str);
            }
            if (this.activatedProfileCounter < 0) {
                this.activatedProfileCounter = 0;
            }
        }
        if (z) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("removeProfileForPackage for activatedProfileCounter = "),
                    this.activatedProfileCounter,
                    "NetworkAnalytics:ConnectionManager");
        }
    }
}
