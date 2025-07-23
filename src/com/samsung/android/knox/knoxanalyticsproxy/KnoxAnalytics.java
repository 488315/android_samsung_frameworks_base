package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Binder;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy;

/* loaded from: classes6.dex */
public class KnoxAnalytics {
    private static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);
    public static final String KNOXANALYTICS_PROXY_SERVICE = "knox_analytics_proxy";
    private static String TAG = "[KnoxAnalyticsProxySDK] ";
    private static IKnoxAnalyticsProxy mService;

    public static void log(KnoxAnalyticsData knoxAnalyticsData) {
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, knoxAnalyticsData.toString());
        }
        if (!getService()) {
            if (z) {
                Log.d(TAG, "log(): service not running!");
                return;
            }
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                mService.log(knoxAnalyticsData);
            } catch (RemoteException e) {
                if (DEBUG) {
                    Log.e(TAG, "log(): Remote Exception in log - ", e);
                }
            } catch (SecurityException e2) {
                if (DEBUG) {
                    Log.e(TAG, "log(): Security Exception in log - ", e2);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean getService() {
        if (mService == null) {
            mService = IKnoxAnalyticsProxy.Stub.asInterface(ServiceManager.getService(KNOXANALYTICS_PROXY_SERVICE));
        }
        return mService != null;
    }
}
