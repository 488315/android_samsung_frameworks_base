package com.android.server.enterprise.nap;

import android.util.Log;
import com.android.server.enterprise.nap.NetworkAnalyticsService;
import com.samsung.android.knox.net.nap.serviceprovider.INetworkAnalyticsService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class NetworkAnalyticsConnectionManager {
    public static final boolean DBG = NetworkAnalyticsService.DBG;
    public static NetworkAnalyticsConnectionManager mInstance;
    public int activatedProfileCounter = 0;
    public ConcurrentHashMap binderMap;
    public ConcurrentHashMap profilesForPackage;

    public NetworkAnalyticsConnectionManager() {
        this.binderMap = null;
        this.profilesForPackage = null;
        this.binderMap = new ConcurrentHashMap();
        this.profilesForPackage = new ConcurrentHashMap();
    }

    public static NetworkAnalyticsConnectionManager getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkAnalyticsConnectionManager();
        }
        return mInstance;
    }

    public void storeBinderForPackage(String str, NetworkAnalyticsService.NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection) {
        if (this.binderMap.get(str) != null) {
            this.binderMap.remove(str);
        }
        this.binderMap.put(str, networkAnalyticsServiceConnection);
    }

    public void addProfileForPackage(String str, String str2) {
        if (this.profilesForPackage.get(str) == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(str2);
            this.profilesForPackage.put(str, arrayList);
        } else if (isProfilePresentForPackage(str, str2)) {
            return;
        } else {
            ((List) this.profilesForPackage.get(str)).add(str2);
        }
        this.activatedProfileCounter++;
    }

    public INetworkAnalyticsService getBinderForPackage(String str) {
        NetworkAnalyticsService.NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection = (NetworkAnalyticsService.NetworkAnalyticsServiceConnection) this.binderMap.get(str);
        if (networkAnalyticsServiceConnection == null) {
            return null;
        }
        if (DBG) {
            Log.d("NetworkAnalytics:ConnectionManager", "getBinderForPackage: binder is not null for " + str);
        }
        return networkAnalyticsServiceConnection.getBinderObject();
    }

    public NetworkAnalyticsService.NetworkAnalyticsServiceConnection getServiceConnectionForPackage(String str) {
        NetworkAnalyticsService.NetworkAnalyticsServiceConnection networkAnalyticsServiceConnection = (NetworkAnalyticsService.NetworkAnalyticsServiceConnection) this.binderMap.get(str);
        if (networkAnalyticsServiceConnection != null && DBG) {
            Log.d("NetworkAnalytics:ConnectionManager", "getServiceConnectionForPackage: service connection is not null for " + str);
        }
        return networkAnalyticsServiceConnection;
    }

    public boolean isProfilePresentForPackage(String str) {
        return getProfilesForPackage(str) != null;
    }

    public boolean isProfilePresentForPackage(String str, String str2) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:ConnectionManager", "isProfilePresentForPackage for packageName = " + str);
        }
        List profilesForPackage = getProfilesForPackage(str);
        boolean contains = profilesForPackage != null ? profilesForPackage.contains(str2) : false;
        if (z) {
            Log.d("NetworkAnalytics:ConnectionManager", "isProfilePresentForPackage for packageName is = " + contains);
        }
        return contains;
    }

    public List getProfilesForPackage(String str) {
        return (List) this.profilesForPackage.get(str);
    }

    public int getActiveProfilesNumber() {
        return this.activatedProfileCounter;
    }

    public void removeBinderForPackage(String str) {
        if (DBG) {
            Log.d("NetworkAnalytics:ConnectionManager", "removeBinderForPackage completely for packageName = " + str);
        }
        this.binderMap.remove(str);
    }

    public void removeProfileForPackage(String str, String str2) {
        if (DBG) {
            Log.d("NetworkAnalytics:ConnectionManager", "removeProfileForPackage for packageName = " + str);
        }
        List profilesForPackage = getProfilesForPackage(str);
        if (profilesForPackage != null) {
            for (int i = 0; i < profilesForPackage.size(); i++) {
                if (((String) profilesForPackage.get(i)).equals(str2)) {
                    profilesForPackage.remove(i);
                    this.activatedProfileCounter--;
                }
            }
            if (profilesForPackage.size() <= 0) {
                this.profilesForPackage.remove(str);
            }
            if (this.activatedProfileCounter < 0) {
                this.activatedProfileCounter = 0;
            }
        }
        if (DBG) {
            Log.d("NetworkAnalytics:ConnectionManager", "removeProfileForPackage for activatedProfileCounter = " + this.activatedProfileCounter);
        }
    }

    public void removeProfileForPackage(String str) {
        boolean z = DBG;
        if (z) {
            Log.d("NetworkAnalytics:ConnectionManager", "removeProfileForPackage completely for packageName = " + str);
        }
        List profilesForPackage = getProfilesForPackage(str);
        if (profilesForPackage == null) {
            return;
        }
        int size = this.activatedProfileCounter - profilesForPackage.size();
        this.activatedProfileCounter = size;
        if (size < 0) {
            this.activatedProfileCounter = 0;
        }
        this.profilesForPackage.remove(str);
        if (z) {
            Log.d("NetworkAnalytics:ConnectionManager", "removeProfileForPackage completely for activatedProfileCounter = " + this.activatedProfileCounter);
        }
    }
}
