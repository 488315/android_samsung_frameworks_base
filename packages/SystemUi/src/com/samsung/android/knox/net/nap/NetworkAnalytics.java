package com.samsung.android.knox.net.nap;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.nap.INetworkAnalytics;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NetworkAnalytics {
    public static final String TAG = "NetworkAnalytics";
    public static INetworkAnalytics mNetworkAnalyticsService;
    public Context mContext;
    public ContextInfo mContextInfo;

    private NetworkAnalytics(Context context) {
        this.mContext = context;
    }

    public static NetworkAnalytics getInstance(ContextInfo contextInfo, Context context) {
        if (contextInfo == null || context == null) {
            return null;
        }
        return new NetworkAnalytics(contextInfo, context);
    }

    public static INetworkAnalytics getService() {
        if (mNetworkAnalyticsService == null) {
            mNetworkAnalyticsService = INetworkAnalytics.Stub.asInterface(ServiceManager.getService("knoxnap"));
        }
        return mNetworkAnalyticsService;
    }

    public final String getNPAVersion() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.i(TAG, "Called getNPAVersion");
            return getService().getNPAVersion();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getNPAVersion", e);
            return null;
        }
    }

    public final List<String> getNetworkMonitorProfiles() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.i(TAG, "Called getNetworkMonitorProfiles");
            return getService().getNetworkMonitorProfiles(this.mContextInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getNetworkMonitorProfiles", e);
            return null;
        }
    }

    public final List<Profile> getProfiles() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.i(TAG, "Called getProfiles");
            return getService().getProfiles(this.mContextInfo);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getProfiles", e);
            return null;
        }
    }

    public final int isProfileActivated(String str) {
        if (getService() == null) {
            return -1;
        }
        try {
            Log.i(TAG, "Called isProfileActivatedForUser");
            return getService().isProfileActivatedForUser(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getNetworkMonitorProfiles", e);
            return -1;
        }
    }

    public final int registerNetworkMonitorProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NetworkAnalytics.registerNetworkMonitorProfile");
        if (getService() == null) {
            Log.d(TAG, "The service is null");
            return -1;
        }
        try {
            Log.i(TAG, "Called registerNetworkMonitorProfile");
            return getService().registerNetworkMonitorProfile(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerNetworkMonitorClient", e);
            return -1;
        }
    }

    public final int start(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NetworkAnalytics.start(String)");
        if (getService() == null) {
            return -1;
        }
        try {
            Log.i(TAG, "Called start");
            Bundle bundle = new Bundle();
            bundle.putInt(NetworkAnalyticsConstants.ActivationState.PROFILE_RECORD_TYPE, 2);
            return getService().handleNAPClientCall(str, bundle, true);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in start", e);
            return -1;
        }
    }

    public final int stop(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NetworkAnalytics.stop");
        if (getService() == null) {
            return -1;
        }
        try {
            Log.i(TAG, "Called stop");
            return getService().handleNAPClientCall(str, null, false);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in stop", e);
            return -1;
        }
    }

    public final int unregisterNetworkMonitorProfile(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NetworkAnalytics.unregisterNetworkMonitorProfile");
        if (getService() == null) {
            return -1;
        }
        try {
            Log.i(TAG, "Called unregisterNetworkMonitorProfile");
            return getService().unregisterNetworkMonitorProfile(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in unregisterNetworkMonitorProfile", e);
            return -1;
        }
    }

    public static NetworkAnalytics getInstance(Context context) {
        if (context == null) {
            return null;
        }
        return new NetworkAnalytics(new ContextInfo(Process.myUid()), context);
    }

    private NetworkAnalytics(ContextInfo contextInfo, Context context) {
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public final int start(String str, Bundle bundle) {
        EnterpriseLicenseManager.log(this.mContextInfo, "NetworkAnalytics.start(String, Bundle)");
        if (getService() == null) {
            return -1;
        }
        try {
            Log.i(TAG, "Called start");
            return getService().handleNAPClientCall(str, bundle, true);
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in start", e);
            return -1;
        }
    }
}
